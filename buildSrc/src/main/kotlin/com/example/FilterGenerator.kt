package com.example

import com.squareup.kotlinpoet.*
import graphql.language.InputObjectTypeDefinition
import graphql.language.TypeName
import java.io.File

class FilterGenerator(
    val packageDir: String,
    private val basePackage: String
) {

    fun generateFilter(input: InputObjectTypeDefinition) {
        val className = input.name.capitalize()
        val packageName = basePackage+ ".filters"
        val baseFilterClassName = ClassName("com.example.paymentsv2.filters", "Filter")

        // Create properties for the data class
        val properties = input.inputValueDefinitions.map { inputField ->
            val fieldName = inputField.name
            val type = inputField.type as TypeName
            val typeName = type.name
            val defaultValue = inputField.defaultValue

            PropertySpec.builder(fieldName, ClassName("com.example.paymentsv2.filters", typeName).copy(nullable = true))
                .mutable()
                .initializer("%T()", ClassName("com.example.paymentsv2.filters", typeName))
//                .addAnnotation(AnnotationSpec.builder(JsonProperty::class)
//                    .addMember("\"${inputField.name}\"")
//                    .build()
//                )
                .apply {
                    if (defaultValue != null) {
                        initializer("%T(%S)", ClassName("com.example.paymentsv2.filters", typeName), defaultValue)
                    }
                }
                .build()
        }

        // Create the data class
        val filterClassTypeSpec =  TypeSpec.classBuilder(className)
            .addModifiers(KModifier.DATA)
            .primaryConstructor(FunSpec.constructorBuilder()
                .addParameters(properties.map { parameter ->
                    ParameterSpec.builder(parameter.name, parameter.type.copy(nullable = true))
                        .defaultValue("null")
                        .build()
                })
                .build()).addProperties(properties.map { parameter ->
                    PropertySpec.builder(parameter.name, parameter.type.copy(nullable = true))
                        .initializer(parameter.name)
                        .build()
            })
            .superclass(baseFilterClassName)
            .build()

        val file = FileSpec.builder(packageName, className)
            .addType(filterClassTypeSpec)
            .build()

        file.writeTo(File(packageDir))
    }
}
