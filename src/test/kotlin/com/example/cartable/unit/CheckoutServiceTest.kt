package com.example.cartable.unit

import com.example.cartable.constants.MessageConstants
import com.example.cartable.dtos.AddToCartDto
import com.example.cartable.dtos.RemoveFromCartDto
import com.example.cartable.exceptions.BadRequestException
import com.example.cartable.models.Cart
import com.example.cartable.models.Customer
import com.example.cartable.models.Item
import com.example.cartable.repositories.CartRepository
import com.example.cartable.repositories.CustomerRepository
import com.example.cartable.repositories.ItemRepository
import com.example.cartable.services.CheckoutService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.util.*

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class CheckoutServiceTest {
    @Autowired
    lateinit var checkoutService: CheckoutService

    @MockBean
    var itemRepositoryMock: ItemRepository? = null
    @MockBean
    var customerRepositoryMock: CustomerRepository? = null
    @MockBean
    lateinit var cartRepositoryMock: CartRepository

    val defaultTestPrice = 1.0
    val defaultTestQuantity = 10
    val defaultCustomerId = 1L
    val defaultItemId = 1L
    val defaultCartId = 1L

//    @Test
//    fun `AddToCart with Invalid customer Id -- should throw EntityNotFoundException`() {
//        val exception = assertThrows<EntityNotFoundException> {
//            val cartDto = AddToCartDto(1, -10000000, defaultTestQuantity)
//            // cartService.addToCart(cartDto)
//        }
//
//        Assert.assertEquals(exception.message, MessageConstants.CUSTOMER_NOT_FOUND_MESSAGE)
//    }

    @Test
    fun `ProcessCheckout with invalid Customer ID -- should throw BadRequestException`() {
        val exception = assertThrows<BadRequestException> {
            val customerId = -10000000L
            checkoutService.processCheckout(customerId)
        }

        Assert.assertEquals(exception.message, MessageConstants.CART_NOT_FOUND)
    }


    @Test
    fun `ProcessCheckout with valid CartList -- should return OrderReceipt`() {
        val exception = assertThrows<BadRequestException> {
            val customerId = -10000000L
            checkoutService.processCheckout(customerId)
        }

        Assert.assertEquals(exception.message, MessageConstants.CART_NOT_FOUND)
    }
//
//    @Test
//    fun `RemoveFromCart remove all item quantities in the cart -- should return True`() {
//        val cartDto = RemoveFromCartDto(defaultCartId, defaultTestQuantity, true)
//        invokeRemoveFromCartRepositoryMocks(cartDto)
//
//        val deleted = cartService.removeFromCart(cartDto)
//        Assert.assertEquals(true, deleted)
//    }

    private fun invokeItemRepositoryMocks(addToCartDto: AddToCartDto) {
        val itemMock = Optional.of(Item(defaultItemId, "Mock", defaultTestPrice, defaultTestQuantity, null, null))
        Mockito.`when`(itemRepositoryMock?.findById(addToCartDto.itemId)).thenReturn(itemMock)
    }

    private fun invokeCustomerRepositoryMocks(addToCartDto: AddToCartDto) {
        val customerMock = Optional.of(Customer(defaultCustomerId, "Mock", null, null))
        Mockito.`when`(customerRepositoryMock?.findById(addToCartDto.customerId)).thenReturn(customerMock)
    }

    private fun invokeAddToCartRepositoryMocks(addToCartDto: AddToCartDto) {
        val cartMock = Cart(0, addToCartDto.customerId, addToCartDto.itemId, addToCartDto.quantity, defaultTestPrice, null, null)
        Mockito.`when`(cartRepositoryMock.save(cartMock)).thenReturn(cartMock)
    }

    private fun invokeRemoveFromCartRepositoryMocks(removeFromCartDto: RemoveFromCartDto) {
        val existingCartMock = Optional.of(Cart(defaultCartId, defaultCustomerId, defaultItemId, defaultTestQuantity, defaultTestPrice, null, null))
        Mockito.`when`(cartRepositoryMock.findById(removeFromCartDto.cartId)).thenReturn(existingCartMock)
    }
}