package com.example.cartable.models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.sql.Timestamp

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "cart")
class Cart(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0L,
        var customerId: Long,
        var itemId: Long,
        var quantity: Int,
        var unitPrice: Double,
        @CreationTimestamp
        var createdAt: Timestamp?,
        @UpdateTimestamp
        var updatedAt: Timestamp?
)
