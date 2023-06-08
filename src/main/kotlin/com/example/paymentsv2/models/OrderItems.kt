package com.example.paymentsv2.models

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
class OrderItems(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long?,
   var isActive: Boolean? = null,
   var comments: String?,
   var menuItemId: Long?,

   @ManyToMany(mappedBy = "items")
   val orders: List<Orders>? = null,

   @Column(name = "menu_item", columnDefinition = "json")
   @Type(JsonType::class)
   var menuItem: MenuItemOrdered? = null,

//   @Column(name = "menu_item", insertable = false, updatable = false)
//   var menuItemString: String? = null,

)
