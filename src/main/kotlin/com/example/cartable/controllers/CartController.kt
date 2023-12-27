package com.example.cartable.controllers

import com.example.cartable.dtos.AddToCartDto
import com.example.cartable.dtos.RemoveFromCartDto
import com.example.cartable.models.Cart
import com.example.cartable.services.CartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cart")
class CartController(private val cartService: CartService) {
    @PostMapping("/add")
    fun addToCart(@RequestBody addToCartDto: AddToCartDto): ResponseEntity<Cart> {
        val cart = cartService.addToCart(addToCartDto)

        return ResponseEntity(cart, HttpStatus.OK)
    }

    @PostMapping("/remove")
    fun removeFromCart(@RequestBody removeFromCartDto: RemoveFromCartDto): ResponseEntity<Boolean> {
        val updated = cartService.removeFromCart(removeFromCartDto)

        return ResponseEntity(updated, HttpStatus.OK)
    }
}