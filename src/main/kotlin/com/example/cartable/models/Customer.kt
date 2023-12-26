package com.example.cartable.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "customers")
class Customer(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0L,
        var name: String,
        @CreationTimestamp
        var createdAt: Timestamp?,
        @UpdateTimestamp
        var updatedAt: Timestamp?
)
