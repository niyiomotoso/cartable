package com.example.cartable.models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "orders")
class Order (
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0L,
        var customerId: Long,
        var discountPrice: Double,
        var grossTotalPrice: Double,
        var netTotalPrice: Double,
        @CreationTimestamp
        var createdAt: Timestamp?,
        @UpdateTimestamp
        var updatedAt: Timestamp?
)
