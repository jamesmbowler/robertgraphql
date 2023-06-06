package com.example.paymentsv2.models

import com.example.paymentsv2.robert.utils.OrderStatus
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.Instant

@Entity
class Orders(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long?,
   var isActive: Boolean? = null,

   @Enumerated(EnumType.STRING)
   var status: OrderStatus? = null,
   var name: String? = null,
   @ManyToOne
   var user: User? = null,
   var total: BigDecimal? = null,
   @ManyToMany(cascade = [CascadeType.ALL])
   @JoinTable(name = "orders_items")
   var items: List<OrderItems>? = null,
   @CreationTimestamp
   val createdOn: Instant? = null,

   @UpdateTimestamp
   val updatedOn: Instant? = null,
)
