package com.example.cartable.services.impl

import com.example.cartable.dtos.AddItemDto
import com.example.cartable.exceptions.BadRequestException
import com.example.cartable.models.Item
import com.example.cartable.repositories.ItemRepository
import com.example.cartable.services.ItemService
import jakarta.persistence.EntityNotFoundException
import org.jetbrains.annotations.NotNull
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl(
        val itemRepository: ItemRepository
) : ItemService {
    override fun addNewItem(@NotNull addItemDto: AddItemDto): Item {
        if (itemRepository.existsByName(addItemDto.name)) {
            throw EntityNotFoundException("Item with name '${addItemDto.name}' already exists")
        }

        val newItem = Item(0, addItemDto.name, addItemDto.price, addItemDto.quantity, null, null)
        return itemRepository.save(newItem)
    }
}
