package com.example.cartable.services

import com.example.cartable.dtos.AddToCartDto
import com.example.cartable.dtos.RemoveFromCartDto
import com.example.cartable.models.Cart

interface CartService {
    fun addToCart(addToCartDto: AddToCartDto): Cart
    fun findByCustomerId(customerId: Long): List<Cart>

    fun emptyCustomerCart(customerId: Long): Boolean
    fun removeFromCart(removeFromCartDto: RemoveFromCartDto): Boolean
}