package com.example.redis.controller;

import com.example.redis.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SimpleController {
  // 문자열 Key와 문자열로 구성된 Value를 다루기 위한 RedisTemplate
  private final StringRedisTemplate redisTemplate;

  @PutMapping("string")
  //HttpStatus.NO_CONTENT: 상태 코드 204를 의미. 요청이 성공적으로 처리되었지만 반환할 콘텐츠가 없음을 나타낸다.
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void setString(
          // http://localhost:8080/string?key=name&value=jun
          @RequestParam("key")
          String key,
          @RequestParam("value")
          String value
  ){
      // Redis의 String을 저장하고 싶다.
    // ValueOperations: Redis 기준, 문자열 작업을 위한 클래스
    ValueOperations<String, String> operations
            = redisTemplate.opsForValue();
    // Redis cosole에서 사용하던 SET key value
    operations.set(key, value);

//    // List를 위한 클래스
//    ListOperations<String, String> listOperations
//            = redisTemplate.opsForList();
//    listOperations.leftPush(key, value);
//    listOperations.leftPop(key);
//
//    // Set을 위한 클래스
//    SetOperations<String, String> setOperations
//            =redisTemplate.opsForSet();
//    setOperations.add(key, value);
  }

  @GetMapping("string")
  public String getString(@RequestParam("key") String key) {
    ValueOperations<String, String> operations = redisTemplate.opsForValue();

    // GET key
    String value = operations.get(key);

    // Null일 경우 에러 처리
    if (value == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return value;
  }

  private final RedisTemplate<String, PersonDto> personRedisTemplate;

  @PutMapping("person")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void setPerson(
          @RequestParam("name")
          String name,
          @RequestBody
          PersonDto dto
  ){
      ValueOperations<String, PersonDto> operations
              = personRedisTemplate.opsForValue();
      operations.set(name, dto);
  }

  @GetMapping("person")
  public PersonDto getPerson(
          @RequestParam("name")
          String name
  ){
      ValueOperations<String, PersonDto> operations
              = personRedisTemplate.opsForValue();
      PersonDto value = operations.get(name);
      if (value == null)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      return value;
  }



}
