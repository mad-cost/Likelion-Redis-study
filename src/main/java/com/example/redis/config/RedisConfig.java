package com.example.redis.config;

import com.example.redis.dto.ItemDto;
import com.example.redis.dto.PersonDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {
  @Bean
  public RedisTemplate<String, PersonDto> personRedisTemplate(
          RedisConnectionFactory connectionFactory
  ) {
    RedisTemplate<String, PersonDto> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    // 주어진 데이터의 직렬화 방식을 결정
    // Redis의 Value는 결국 문자열 형식이니까,
    // 주어진 데이터(DTO)를 어떻게 문자열로 바꿀 것인지
//        template.setDefaultSerializer(RedisSerializer.json());
    // 미리 만들어진 String 변환기를 설정
    template.setKeySerializer(RedisSerializer.string());
    // 미리 만들어진 JSON 변환기를 설정
    template.setValueSerializer(RedisSerializer.json());
    return template;
  }

  // 원하는 자료형의 데이터를 Redis에 넣기 위해, 해당 자료형을 사용하는 RedisTemplate을 만들고 @Bean으로 등록
  // RedisTemplate: Redis에서 제공하는 클래스로, Redis에 데이터를 저장하고 조회할 때 사용.
  @Bean
  public RedisTemplate<Long, ItemDto> cacheRedisTemplate(
          // RedisConnectionFactory: Redis 연결을 생성하는 팩토리입니다. 이 팩토리를 통해 RedisTemplate은 Redis 서버와 연결
          RedisConnectionFactory connectionFactory
  ) {
    RedisTemplate<Long, ItemDto> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setDefaultSerializer(RedisSerializer.json());
    return template;
  }

  @Bean
  public RedisTemplate<String, ItemDto> rankTemplate(
          RedisConnectionFactory connectionFactory
  ) {
    RedisTemplate<String, ItemDto> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(RedisSerializer.string());
    template.setValueSerializer(RedisSerializer.json());
    return template;
  }
}