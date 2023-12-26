package com.example.cartable.repositories

import com.example.cartable.models.SalesOrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface SalesOrderItemRepository: JpaRepository<SalesOrderItem, Long>