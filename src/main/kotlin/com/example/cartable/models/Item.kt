package com.example.cartable.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@Entity
@EntityListeners(AuditingEntityListener::class)
class Item(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0L,
        var name: String,
        var price: Double?,
        var quantity: Int,
        @CreationTimestamp
        var createdAt: Timestamp?,
        @UpdateTimestamp
        var updatedAt: Timestamp?
)