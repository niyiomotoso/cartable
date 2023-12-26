package com.example.cartable.services.impl

import com.example.cartable.dtos.AddCustomerDto
import com.example.cartable.models.Customer
import com.example.cartable.repositories.CustomerRepository
import com.example.cartable.services.CustomerService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(private val customerRepository: CustomerRepository): CustomerService {
    override fun addNewCustomer(addCustomerDto: AddCustomerDto): Customer {
        if(customerRepository.existsByName(addCustomerDto.name))
            throw EntityNotFoundException("Customer with name '${addCustomerDto.name}' already exists")

        val newCustomer = Customer(0, addCustomerDto.name, null, null)
        return customerRepository.save(newCustomer)
    }
}