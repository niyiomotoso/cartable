package com.example.cartable.repositories

import com.example.cartable.models.Offer
import org.springframework.data.jpa.repository.JpaRepository

interface OfferRepository: JpaRepository<Offer, Long> {
    fun findBySlug(slug: String)
    fun existsBySlugAndActive(slug: String, active: Boolean): Boolean
}