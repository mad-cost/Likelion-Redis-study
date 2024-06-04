package com.example.redis;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("session")
// 임시 정보 간단하게 사용해보기
public class SessionController {
  @PutMapping
  //HttpStatus.NO_CONTENT: 상태 코드 204를 의미. 요청이 성공적으로 처리되었지만 반환할 콘텐츠가 없음을 나타낸다.
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void setSession(
          @RequestParam("key")
          String key,
          @RequestParam("value")
          String value,
          // HTTP 세션을 관리하는 데 사용
          HttpSession session
  ){  
    // 세션에 데이터를 저장
      session.setAttribute(key, value);
  }

  @GetMapping
  public String getSession(
          @RequestParam("key")
          String key,
          HttpSession session
  ){
    // 반환형이 Object이다, 결과로 null 반환이 가능
    Object value = session.getAttribute(key);
    if (value == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    // value가 String인지
    if (!(value instanceof String))
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
     return value.toString();

  }







}
