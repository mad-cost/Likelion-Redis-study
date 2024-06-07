package com.example.redis;

import com.example.redis.entity.Item;
import com.example.redis.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
// 데이터의 조회가 오래 걸리는 데이터베이스
public class SlowDataQuery {
  private final ItemRepository itemRepository;

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
}
