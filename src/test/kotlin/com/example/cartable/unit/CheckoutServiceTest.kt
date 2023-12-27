package com.example.cartable.unit

import com.example.cartable.constants.MessageConstants
import com.example.cartable.constants.OffersMap
import com.example.cartable.dtos.OrderReceipt
import com.example.cartable.exceptions.BadRequestException
import com.example.cartable.models.*
import com.example.cartable.repositories.*
import com.example.cartable.services.CartService
import com.example.cartable.services.OfferService
import com.example.cartable.services.impl.CheckoutServiceImpl
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import kotlin.collections.ArrayList

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class CheckoutServiceTest {
    @Mock
    lateinit var itemRepositoryMock: ItemRepository

    @Mock
    lateinit var cartServiceMock: CartService
    @Mock
    lateinit var orderRepositoryMock: OrderRepository

    @Mock
    lateinit var offerServiceMock: OfferService

    @Mock
    lateinit var salesOrderItemRepository: SalesOrderItemRepository

    @InjectMocks
    lateinit var checkoutService: CheckoutServiceImpl


    // Data Providers
    private final val defaultTestPrice = 1.0
    private final val defaultTestQuantity = 10
    private final val defaultCustomerId = 1L
    private final val defaultItemId = 1L
    private final val defaultCartId = 1L
    private final val defaultOrderId = 1L

    private final val testCartList: ArrayList<Cart> = arrayListOf(
            Cart(1L, defaultCustomerId, 1L, 10, 100.0, null, null),
            Cart(2L, defaultCustomerId, 2L, 20, 200.0, null, null),
            Cart(3L, defaultCustomerId, 3L, 30, 300.0, null, null)
    )

    private final val testSalesOrderItemList: ArrayList<SalesOrderItem> = arrayListOf(
            SalesOrderItem(1L, defaultOrderId, defaultCustomerId, 1L, 10, 100.0, null, null),
            SalesOrderItem(2L, defaultOrderId, defaultCustomerId, 2L, 20, 200.0, null, null),
            SalesOrderItem(3L, defaultOrderId, defaultCustomerId, 3L, 30, 300.0, null, null)
    )

    private final val testOfferDiscountPrice = 300.0
    private final val testOrderReceiptWithoutOffer = OrderReceipt(
            defaultOrderId,
            defaultCustomerId,
            14000.0,
            14000.0,
            0.0,
            testSalesOrderItemList
    )

    private final val testOrderReceiptWithOffer = OrderReceipt(
            defaultOrderId,
            defaultCustomerId,
            13700.0,
            14000.0,
            testOfferDiscountPrice,
            testSalesOrderItemList
    )

    @Test
    fun `ProcessCheckout with invalid Customer ID -- should throw BadRequestException`() {
        val exception = assertThrows<BadRequestException> {
            val customerId = -10000000L
            checkoutService.processCheckout(customerId)
        }

        Assert.assertEquals(exception.message, MessageConstants.CART_NOT_FOUND)
    }

    @Test
    fun `ProcessCheckout with valid CartList and No Offer -- should return OrderReceipt with 0 Discount`() {
        val customerId = defaultCustomerId
        injectCartServiceScenarios(customerId)
        invokeOrderRepositoryMock()
        val orderReceipt = checkoutService.processCheckout(customerId)

        Assert.assertEquals(testOrderReceiptWithoutOffer.orderId, orderReceipt.orderId)
        Assert.assertEquals(testOrderReceiptWithoutOffer.discountedPrice, 0.0, 0.00000)
        Assert.assertEquals(testOrderReceiptWithoutOffer.netTotalPrice, orderReceipt.netTotalPrice, 0.00000)
        Assert.assertEquals(testOrderReceiptWithoutOffer.grossTotalPrice, orderReceipt.grossTotalPrice, 0.00000)
        Assert.assertEquals(testOrderReceiptWithoutOffer.customerId, orderReceipt.customerId)
    }

    @Test
    fun `ProcessCheckout with valid CartList and Active 2-for-1 Offer -- should return OrderReceipt with some Discount`() {
        val customerId = defaultCustomerId
        injectCartServiceScenarios(customerId)
        invokeOrderRepositoryMock()
        injectOfferServiceScenarios()
        val orderReceipt = checkoutService.processCheckout(customerId)

        Assert.assertEquals(testOrderReceiptWithOffer.orderId, orderReceipt.orderId)
        Assert.assertEquals(testOrderReceiptWithOffer.discountedPrice, testOfferDiscountPrice, 0.0000)
        Assert.assertEquals(testOrderReceiptWithOffer.customerId, orderReceipt.customerId)
        Assert.assertEquals(testOrderReceiptWithOffer.netTotalPrice, orderReceipt.netTotalPrice, 0.00000)
        Assert.assertEquals(testOrderReceiptWithOffer.grossTotalPrice, orderReceipt.grossTotalPrice, 0.00000)
    }

    private fun injectOfferServiceScenarios() {
        Mockito.`when`(offerServiceMock.existsBySlugAndActive(OffersMap.OFFER_TWO_FOR_ONE, true)).thenReturn(true)
        Mockito.`when`(offerServiceMock.getDiscountedPriceOnCart(testCartList, OffersMap.OFFER_TWO_FOR_ONE)).thenReturn(testOfferDiscountPrice)
    }

    private fun invokeOrderRepositoryMock() {
        val savedOrder = Order(defaultOrderId, defaultCustomerId, 0.0, 0.0, 0.0, null, null)
        Mockito.`when`(orderRepositoryMock.save(ArgumentMatchers.any(Order::class.java))).thenReturn(savedOrder)
    }


    private fun injectCartServiceScenarios(customerId: Long) {
        Mockito.`when`(cartServiceMock.findByCustomerId(customerId)).thenReturn(testCartList)
    }
}