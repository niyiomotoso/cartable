package com.example.cartable.controllers

import com.example.cartable.dtos.AddItemDto
import com.example.cartable.models.Item
import com.example.cartable.services.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController(private val itemService: ItemService) {
    @PostMapping("")
fun addNewItem(@RequestBody addItemDto: AddItemDto): ResponseEntity<Item> {
    val item = itemService.addNewItem(addItemDto)

    return ResponseEntity(item, HttpStatus.CREATED);
}

}
