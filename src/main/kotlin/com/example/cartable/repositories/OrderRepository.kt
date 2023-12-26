package com.example.cartable.repositories

import com.example.cartable.models.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long>