package com.example.cartable.services.impl

import com.example.cartable.constants.OffersMap
import com.example.cartable.models.Cart
import com.example.cartable.models.Offer
import com.example.cartable.repositories.OfferRepository
import com.example.cartable.services.OfferService
import org.springframework.stereotype.Service

@Service
class OfferServiceImpl(private val offerRepository: OfferRepository): OfferService {
    override fun createOffer(offerSlug: String) {
        val newOffer = Offer(0, offerSlug, true, null, null)
        offerRepository.save(newOffer)
    }

    override fun getDiscountedPriceOnCart(cartList: List<Cart>, offerSlug: String): Double {
        var totalDiscountedPrice = 0.0
        val offerUnit = getOfferUnitBasedOnOfferSlug(offerSlug)
        for (cart in cartList) {
            if (cart.quantity > 1 && cart.quantity % offerUnit == 0 ) {
                totalDiscountedPrice+= ( (cart.quantity / offerUnit) * cart.unitPrice)
            }
        }

        return totalDiscountedPrice
    }

    override fun existsBySlugAndActive(slug: String, active: Boolean): Boolean {
        return offerRepository.existsBySlugAndActive(slug, active)
    }

    private fun getOfferUnitBasedOnOfferSlug(slug: String): Int {
        return when (slug) {
            OffersMap.OFFER_TWO_FOR_ONE ->
                2
            else ->
                1
        }
    }
}