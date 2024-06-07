package com.example.redis;

import com.example.redis.entity.Item;
import com.example.redis.entity.ItemOrder;
import com.example.redis.repo.ItemRepository;
import com.example.redis.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
// 데이터의 조회가 오래 걸리는 데이터베이스
public class SlowDataQuery {
  private final ItemRepository itemRepository;
  private final OrderRepository orderRepository;

  public List<Item> findAll(){
    try {
      // 1초 멈췄다가 실행
      Thread.sleep(1000);
    }catch (InterruptedException ignored){}
    return itemRepository.findAll();
  }

  public Optional<Item> findById(Long id){
    try {
      // 1초 멈췄다가 실행
      Thread.sleep(1000);
    }catch (InterruptedException ignored){}
    return itemRepository.findById(id);
  }

  public Item purchase(Long id) {
    Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    orderRepository.save(ItemOrder.builder()
            .item(item)
            .build());

    return item;
  }
}
