package com.example.redis.controller;

import com.example.redis.dto.ItemDto;
import com.example.redis.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @GetMapping
  public List<ItemDto> readAll(){
    return itemService.readAll();
  }

  @GetMapping("{id}")
  public ItemDto readOne(
          @PathVariable("id")
          Long id
  ){
    return itemService.readOne(id);
  }



}
