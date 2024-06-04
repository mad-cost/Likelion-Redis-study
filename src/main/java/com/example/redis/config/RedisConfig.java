package com.example.redis.config;

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
  ){
      RedisTemplate<String, PersonDto> template = new RedisTemplate<>();
      template.setConnectionFactory(connectionFactory);
      // 주어진 데이터의 직렬화 방식을 결정
      template.setDefaultSerializer(RedisSerializer.json());
      return template;
  }
}
