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
import com.example.cartable.services.CartService
import jakarta.persistence.EntityNotFoundException
import org.junit.Assert.assertEquals
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
class CartServiceTest {
    @Autowired
    lateinit var cartService: CartService

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

    @Test
    fun `AddToCart with Invalid customer Id -- should throw EntityNotFoundException`() {
        val exception = assertThrows<EntityNotFoundException> {
            val cartDto = AddToCartDto(1, -10000000, defaultTestQuantity)
            cartService.addToCart(cartDto)
        }

        assertEquals(exception.message, MessageConstants.CUSTOMER_NOT_FOUND_MESSAGE)
    }

    @Test
    fun `AddToCart with Invalid item Id -- should throw EntityNotFoundException`() {
        val exception = assertThrows<EntityNotFoundException> {
            val cartDto = AddToCartDto(-100000000, defaultCustomerId, defaultTestQuantity)
            injectCustomerRepositoryScenarios(cartDto)
            cartService.addToCart(cartDto)
        }

        assertEquals(exception.message, MessageConstants.ITEM_NOT_FOUND_MESSAGE)
    }

    @Test
    fun `AddToCart with Invalid Quantity -- should throw BadRequestException`() {
        val exception = assertThrows<BadRequestException> {
            val cartDto = AddToCartDto(defaultItemId, defaultCustomerId, 100000000)
            injectCustomerRepositoryScenarios(cartDto)
            injectItemRepositoryScenarios(cartDto)
            cartService.addToCart(cartDto)
        }

        assertEquals(exception.message, MessageConstants.NOT_ENOUGH_STOCK_FOR_CART_ADD_MESSAGE)
    }

//    @Test
    fun `AddToCart with Valid Add Cart Parameters-- should return created cart`() {
        val quantityToAdd = 3
        val itemToAdd = defaultItemId;
        val expectedCart = Cart(0, defaultCustomerId, itemToAdd, quantityToAdd, defaultTestPrice, null, null)
        val cartDto = AddToCartDto(itemToAdd, defaultCustomerId, quantityToAdd)
        injectCustomerRepositoryScenarios(cartDto)
        injectItemRepositoryScenarios(cartDto)
        injectAddToCartRepositoryScenarios(cartDto)
        val actualCart = cartService.addToCart(cartDto)

        assertEquals(expectedCart, actualCart)
    }

    @Test
    fun `RemoveFromCart with Invalid Cart Id -- should throw EntityNotFoundException`() {
        val exception = assertThrows<EntityNotFoundException> {
            val cartDto = RemoveFromCartDto(-100000000, defaultTestQuantity, false)
            cartService.removeFromCart(cartDto)
        }

        assertEquals(exception.message, MessageConstants.CART_NOT_FOUND)
    }

    @Test
    fun `RemoveFromCart remove all item quantities in the cart -- should return True`() {
        val cartDto = RemoveFromCartDto(defaultCartId, defaultTestQuantity, true)
        invokeRemoveFromCartRepositoryMocks(cartDto)

        val deleted = cartService.removeFromCart(cartDto)
        assertEquals(true, deleted)
    }

    private fun injectItemRepositoryScenarios(addToCartDto: AddToCartDto) {
        val itemMock = Optional.of(Item(defaultItemId, "Mock", defaultTestPrice, defaultTestQuantity, null, null))
        Mockito.`when`(itemRepositoryMock?.findById(addToCartDto.itemId)).thenReturn(itemMock)
    }

    private fun injectCustomerRepositoryScenarios(addToCartDto: AddToCartDto) {
        val customerMock = Optional.of(Customer(defaultCustomerId, "Mock", null, null))
        Mockito.`when`(customerRepositoryMock?.findById(addToCartDto.customerId)).thenReturn(customerMock)
    }

    private fun injectAddToCartRepositoryScenarios(addToCartDto: AddToCartDto) {
        val cartMock = Cart(0, addToCartDto.customerId, addToCartDto.itemId, addToCartDto.quantity, defaultTestPrice, null, null)
        Mockito.`when`(cartRepositoryMock.save(cartMock)).thenReturn(cartMock)
    }

    private fun invokeRemoveFromCartRepositoryMocks(removeFromCartDto: RemoveFromCartDto) {
        val existingCartMock = Optional.of(Cart(defaultCartId, defaultCustomerId, defaultItemId, defaultTestQuantity, defaultTestPrice, null, null))
        Mockito.`when`(cartRepositoryMock.findById(removeFromCartDto.cartId)).thenReturn(existingCartMock)
    }
}
