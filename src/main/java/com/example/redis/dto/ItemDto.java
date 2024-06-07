package com.example.redis.dto;

import com.example.redis.entity.Item;
import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//implements Serializable: Java 클래스에서 객체 직렬화를 가능하게 하기 위해 사용
//객체를 Redis와 같은 캐시 시스템에 저장하고 불러올 때 직렬화가 필요하다
public class ItemDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;

    public static ItemDto fromEntity(Item entity) {
        return ItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .build();
    }
}