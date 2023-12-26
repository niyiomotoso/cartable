package com.example.cartable.services

import com.example.cartable.dtos.AddItemDto
import com.example.cartable.models.Item

interface ItemService {
    fun addNewItem(addItemDto: AddItemDto): Item
}