package com.example.redis.service;

import com.example.redis.SlowDataQuery;
import com.example.redis.dto.ItemDto;
import com.example.redis.entity.Item;
import com.example.redis.repo.ItemRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
// SlowDataQuery을 사용하는 ItemService
public class ItemService {
  private final SlowDataQuery repository;
  private final ItemRepository itemRepository;

  //@Resource(name = "cacheRedisTemplate") = cacheRedisTemplate이라는 이름을 가진 빈을 주입받는다
  @Resource(name = "cacheRedisTemplate")
  //Redis에서 키-값 쌍에 대한 연산을 수행하기 위한 인터페이스입니다
  private ValueOperations<Long, ItemDto> cacheOps;

  public ItemDto readOne(Long id){
    // Cache Aside를 RedisTemplate을 활용해 직접 구현해 보자.
    // 1. cacheOps에서 ItemDto를 찾아본다.
    // GET id
    ItemDto found = cacheOps.get(id);
    // 2. null일 경우 데이터베이스에서 조회한다.
    if (found == null) {
      // 2-1. 없으면 404
      found = repository.findById(id)
              .map(ItemDto::fromEntity)
              .orElseThrow(() ->
                      new ResponseStatusException(HttpStatus.NOT_FOUND));
      // 2-2. 있으면 캐시에 저장
      // 3번째 인자로 (60초 후)만료 시간 설정 가능
      cacheOps.set(id, found, Duration.ofSeconds(60));
    }
    // 3. 최종적으로 데이터를 반환한다.
    return found;
//        return repository.findById(id)
//                .map(ItemDto::fromEntity)
//                .orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  // cacheName: 캐시 규칙을 지정하기 위한 이름
  // key: 캐시를 저장할때 개별 데이터를 구분하기 위한 값
  @CachePut(cacheNames = "itemCache", key = "#result.id")
  public ItemDto create(ItemDto dto) {
    // CachePut은 항상 메서드를 실행하고 해당 결과를 캐시에 적용한다.
    log.info("cacheput create");
    return ItemDto.fromEntity(itemRepository.save(Item.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .stock(dto.getStock())
            .build()));
  }

  public ItemDto createManual(ItemDto dto) {
    Item item = itemRepository.save(Item.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .stock(dto.getStock())
            .build());
    ItemDto newDto = ItemDto.fromEntity(item);
    // 결과를 반환하기 전 캐시에 한번 저장한다.
    cacheOps.set(newDto.getId(), newDto, Duration.ofSeconds(60));
    return newDto;
  }

  @Cacheable(cacheNames = "itemAllCache", key = "#root.methodName")
  public List<ItemDto> readAll(){
    return repository.findAll()
            .stream()
            .map(ItemDto::fromEntity)
            .toList();
  }
}
