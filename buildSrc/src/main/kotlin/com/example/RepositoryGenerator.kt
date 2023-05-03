package com.example

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

class RepositoryGenerator(
    val packageDir: String,
    private val basePackage: String
) {

    fun generateRepository(entityName: String, name: String) {
        val packageName = basePackage+ ".repositories"

        val repositoryClass = ClassName(packageName, entityName + "Repository")
        val entityClass = ClassName("com.example.paymentsv2.models", name)
        val jpaRepository = ClassName("org.springframework.data.jpa.repository", "JpaRepository")
        val jpaSpecificationExecutor = ClassName("org.springframework.data.jpa.repository", "JpaSpecificationExecutor")
        val repository = ClassName("org.springframework.stereotype", "Repository")

        val fileSpec = FileSpec.builder(packageName, entityName + "Repository")
            .addType(
                TypeSpec.interfaceBuilder(repositoryClass)
                .addSuperinterface(jpaRepository.parameterizedBy(entityClass, ClassName("kotlin", "Long")))
                .addSuperinterface(jpaSpecificationExecutor.parameterizedBy(entityClass))
                .addAnnotation(repository)
                .addModifiers(KModifier.PUBLIC)
                .build())
            .addImport("kotlin", "Long")
            .build()

        fileSpec.writeTo(File(packageDir))
    }
}
