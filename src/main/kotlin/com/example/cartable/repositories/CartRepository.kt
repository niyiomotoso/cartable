package com.example.cartable.repositories

import com.example.cartable.models.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository: JpaRepository<Cart, Long> {
    fun findByCustomerIdAndItemId(customerId: Long, itemId: Long): Cart?
    fun findByCustomerId(customerId: Long): List<Cart>
}