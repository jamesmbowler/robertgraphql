//package com.example.paymentsv2.robert
//
//import jakarta.persistence.metamodel.Metamodel
//import org.hibernate.SessionFactory
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//@Transactional
//class TableService(@Autowired private val sessionFactory: SessionFactory) {
//
//    fun getAllEntities() {
//        val tables = mutableListOf<String>()
//        val metadata: Metamodel = sessionFactory.metamodel
//
//        for (entityName in metadata.entities) {
//            tables.add(entityName.name)
//        }
//
//        println(tables.toString())
//    }
//}
