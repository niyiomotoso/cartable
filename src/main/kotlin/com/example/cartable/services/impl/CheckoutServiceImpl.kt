package com.example.cartable.services.impl

import com.example.cartable.constants.MessageConstants
import com.example.cartable.constants.OffersMap
import com.example.cartable.dtos.OrderReceipt
import com.example.cartable.models.Cart
import com.example.cartable.models.Order
import com.example.cartable.models.SalesOrderItem
import com.example.cartable.repositories.CartRepository
import com.example.cartable.repositories.OrderRepository
import com.example.cartable.repositories.SalesOrderItemRepository
import com.example.cartable.services.CheckoutService
import com.example.cartable.services.OfferService
import com.example.cartable.exceptions.BadRequestException
import org.springframework.stereotype.Service

@Service
class CheckoutServiceImpl(private var cartRepository: CartRepository, private var offerService: OfferService,
                          private var orderRepository: OrderRepository, private var salesOrderItemRepository: SalesOrderItemRepository): CheckoutService {


    override fun processCheckout(customerId: Long): OrderReceipt {
        val cartList = cartRepository.findByCustomerId(customerId)

        if (cartList.isEmpty())
            throw BadRequestException(MessageConstants.CART_NOT_FOUND)

        val grossTotalPrice = calculateGrossTotalPriceOfCartList(cartList)
        var discountedPrice = 0.0
        // Apply 2-for-1 offer if active
        if (offerService.existsBySlugAndActive(OffersMap.OFFER_TWO_FOR_ONE, true)) {
            discountedPrice = offerService.getDiscountedPriceOnCart(cartList, OffersMap.OFFER_TWO_FOR_ONE)
        }

        val netTotalPrice = grossTotalPrice - discountedPrice
        val newOrder = Order(0, customerId, discountedPrice, grossTotalPrice, netTotalPrice, null, null)

        val savedOrder = orderRepository.save(newOrder)
        val salesOrderItemList = saveSalesOrderItems(cartList, customerId, newOrder.id)

        return OrderReceipt(savedOrder.id, customerId, netTotalPrice, grossTotalPrice, discountedPrice, salesOrderItemList)
    }

    private fun calculateGrossTotalPriceOfCartList(cartList: List<Cart>): Double {
        var totalPrice = 0.0
        for (cart in cartList) {
            totalPrice += (cart.unitPrice * cart.quantity)
        }

        return totalPrice
    }

    private fun saveSalesOrderItems(cartList: List<Cart>, customerId: Long, orderId: Long): List<SalesOrderItem> {
        val itemList: ArrayList<SalesOrderItem> = ArrayList()
        for (cart in cartList) {
            val newItem = SalesOrderItem(0, orderId, customerId, cart.itemId, cart.quantity, cart.unitPrice, null ,null)
            itemList.add(salesOrderItemRepository.save(newItem))
        }

        return itemList
    }
}