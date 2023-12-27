package com.example.cartable.dtos

class RemoveFromCartDto (
        var cartId: Long,
        var quantity: Int,
        var removeAllQuantities: Boolean
)