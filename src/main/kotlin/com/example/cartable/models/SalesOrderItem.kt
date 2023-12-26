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
@Table(name = "sales_order_items")
class SalesOrderItem (
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0L,
        var orderId: Long,
        var customerId: Long,
        var itemId: Long,
        var quantity: Int,
        var unitPrice: Double,
        @CreationTimestamp
        var createdAt: Timestamp?,
        @UpdateTimestamp
        var updatedAt: Timestamp?
)
