package com.example.cartable.services

import com.example.cartable.dtos.AddCustomerDto
import com.example.cartable.models.Customer

interface CustomerService {
    fun addNewCustomer(addCustomerDto: AddCustomerDto): Customer
}

