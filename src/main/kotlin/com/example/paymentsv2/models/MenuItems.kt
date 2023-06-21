package com.example.paymentsv2.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.Instant

@Entity
class MenuItems(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long?,
   var isActive: Boolean? = true,
   var soldOut: Boolean? = false,
   var quantity: Int? = null,
   var name: String?,
   var description: String?,
   var price: BigDecimal?,
   @ManyToMany(mappedBy = "menuItems", cascade = [CascadeType.ALL])
   var menus: Set<Menus> = emptySet(),
   @CreationTimestamp
   val createdOn: Instant? = null,

   @UpdateTimestamp
   val updatedOn: Instant? = null,
) {
   fun toDto() = MenuItemOrdered(
      id = id,
      isActive = isActive,
      name = name,
      description = description,
      price = price,
      //menuId = menus?.first()?.id
   )
}

data class MenuItemOrdered(
   var id: Long?,
   var isActive: Boolean? = null,
   var name: String?,
   var description: String?,
   var price: BigDecimal?,
   //var menuId: Long?
)
