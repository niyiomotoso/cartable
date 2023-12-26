package com.example.cartable.dtos

import com.example.cartable.models.SalesOrderItem

class OrderReceipt (
        var orderId: Long,
        var customerId: Long,
        var netTotalPrice: Double,
        var grossTotalPrice: Double,
        var discountedPrice: Double,
        var salesOrderItems: List<SalesOrderItem>

)

