package com.example.cartable.controllers

import com.example.cartable.dtos.AddCustomerDto
import com.example.cartable.models.Customer
import com.example.cartable.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(private val customerService: CustomerService) {
    @PostMapping("/add")
    fun addNewCustomer(@RequestBody addCustomerDto: AddCustomerDto): ResponseEntity<Customer> {
        val customer = customerService.addNewCustomer(addCustomerDto)

        return ResponseEntity(customer, HttpStatus.CREATED)
    }
}