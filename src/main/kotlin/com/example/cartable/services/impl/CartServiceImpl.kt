package com.example.cartable.services.impl

import com.example.cartable.constants.MessageConstants
import com.example.cartable.dtos.AddToCartDto
import com.example.cartable.dtos.RemoveFromCartDto
import com.example.cartable.models.Cart
import com.example.cartable.repositories.CartRepository
import com.example.cartable.repositories.CustomerRepository
import com.example.cartable.repositories.ItemRepository
import com.example.cartable.services.CartService
import com.example.cartable.exceptions.BadRequestException;
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartServiceImpl (private var itemRepository: ItemRepository,
                       private var customerRepository: CustomerRepository,
                       private var cartRepository: CartRepository): CartService {
    @Transactional(rollbackFor = [Exception::class])
    override fun addToCart(addToCartDto: AddToCartDto): Cart {
        customerRepository.findById(addToCartDto.customerId).orElseThrow {
            throw EntityNotFoundException(MessageConstants.CUSTOMER_NOT_FOUND_MESSAGE)
        }

        val item = itemRepository.findById(addToCartDto.itemId).orElseThrow {
                throw EntityNotFoundException(MessageConstants.ITEM_NOT_FOUND_MESSAGE)
        }

        val existingCart = cartRepository.findByCustomerIdAndItemId(addToCartDto.customerId, addToCartDto.itemId)

        val updatedCart: Cart = if (existingCart != null) {
            // add the previously added quantity to the prospective one
            val updatedQuantity = existingCart.quantity + addToCartDto.quantity
            if (item?.quantity!! < updatedQuantity)
                throw BadRequestException(MessageConstants.NOT_ENOUGH_STOCK_FOR_CART_UPDATE_MESSAGE)
            existingCart.quantity = updatedQuantity

            cartRepository.save(existingCart)
        } else {
            if (item?.quantity!! < addToCartDto.quantity)
                throw BadRequestException(MessageConstants.NOT_ENOUGH_STOCK_FOR_CART_ADD_MESSAGE)

            val cart = Cart(0, addToCartDto.customerId, addToCartDto.itemId, addToCartDto.quantity, item.price!!, null, null)
            cartRepository.save(cart)
        }

        return updatedCart
    }

    override fun findByCustomerId(customerId: Long): List<Cart> {
        return cartRepository.findByCustomerId(customerId)
    }


    override fun emptyCustomerCart(customerId: Long): Boolean {
        customerRepository.findById(customerId).orElseThrow {
            throw EntityNotFoundException(MessageConstants.CUSTOMER_NOT_FOUND_MESSAGE)
        }

        val cartList = cartRepository.findByCustomerId(customerId)
        cartRepository.deleteAll(cartList)

        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun removeFromCart(removeFromCartDto: RemoveFromCartDto): Boolean {
         val cart = cartRepository.findById(removeFromCartDto.cartId).orElseThrow{
            throw EntityNotFoundException(MessageConstants.CART_NOT_FOUND)
        }

        if (removeFromCartDto.removeAllQuantities) {
            cartRepository.delete(cart)
        }

        // reduce the cart quantity by the supplied value
        val updatedQuantity = cart.quantity - removeFromCartDto.quantity

        if (updatedQuantity < 1) {
            cartRepository.delete(cart)
        } else {
            cart.quantity = updatedQuantity
            cartRepository.save(cart)
        }

        return true
    }
}