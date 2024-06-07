package com.example.redis.controller;

import com.example.redis.dto.ItemDto;
import com.example.redis.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @PostMapping
  public ItemDto create(
          @RequestBody
          ItemDto itemDto
  ) {
    return itemService.create(itemDto);
  }

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
