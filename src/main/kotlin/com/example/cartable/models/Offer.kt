package com.example.cartable.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@Entity
@EntityListeners(AuditingEntityListener::class)
class Offer(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0L,
        @Column(unique = true)
        var slug: String,
        var active: Boolean, // this will act as a feature toggle
        @CreationTimestamp
        var createdAt: Timestamp?,
        @UpdateTimestamp
        var updatedAt: Timestamp?
)
