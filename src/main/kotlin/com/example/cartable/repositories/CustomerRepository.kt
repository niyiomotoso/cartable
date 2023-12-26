package com.example.cartable.repositories

import com.example.cartable.models.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
    fun existsByName(name: String): Boolean
}