package com.example.cartable.unit

import com.example.cartable.constants.OffersMap
import com.example.cartable.models.Cart
import com.example.cartable.repositories.OfferRepository
import com.example.cartable.services.impl.OfferServiceImpl
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class OfferServiceTest {
    @Mock
    lateinit var offerRepository: OfferRepository

    @InjectMocks
    lateinit var offerService: OfferServiceImpl

    @Test
    fun `GetDiscountedPriceOnCart with valid CartList -- should apply offer and return the right Discount`() {
         val testCartList: ArrayList<Cart> = arrayListOf(
                Cart(1L, 1L, 1L, 10, 100.0, null, null),
                Cart(2L, 1L, 2L, 20, 200.0, null, null),
                Cart(3L, 1L, 3L, 30, 300.0, null, null)
        )

        val discountedAmount = offerService.getDiscountedPriceOnCart(testCartList, OffersMap.OFFER_TWO_FOR_ONE)

        Assert.assertEquals(7000.0, discountedAmount, 0.0000)
    }

    @Test
    fun `GetDiscountedPriceOnCart with a CartList that has only 1 stock per item -- should return 0 Discount`() {
        val testCartList: ArrayList<Cart> = arrayListOf(
                Cart(1L, 1L, 1L, 1, 100.0, null, null),
                Cart(2L, 1L, 2L, 1, 200.0, null, null),
                Cart(3L, 1L, 3L, 1, 300.0, null, null),
                Cart(3L, 1L, 4L, 1, 300.0, null, null)
        )

        val discountedAmount = offerService.getDiscountedPriceOnCart(testCartList, OffersMap.OFFER_TWO_FOR_ONE)

        Assert.assertEquals(0.0, discountedAmount, 0.0000)
    }
}