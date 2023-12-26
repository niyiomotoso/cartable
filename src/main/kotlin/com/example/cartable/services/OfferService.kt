package com.example.cartable.services

import com.example.cartable.models.Cart

interface OfferService {
    fun createOffer(offerSlug: String)
    fun getDiscountedPriceOnCart(cartList: List<Cart>, offerSlug: String): Double
    fun existsBySlugAndActive(slug: String, active: Boolean): Boolean
}