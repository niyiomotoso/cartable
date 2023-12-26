package com.example.cartable.services

import com.example.cartable.dtos.OrderReceipt

interface CheckoutService {
    fun processCheckout(customerId: Long): OrderReceipt
}
