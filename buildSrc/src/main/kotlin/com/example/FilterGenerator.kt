package com.example

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import graphql.language.InputObjectTypeDefinition
import graphql.language.TypeName
import java.io.File

class FilterGenerator(
    val packageDir: String,
    private val basePackage: String
) {

    fun generateFromMapFunction(properties: List<PropertySpec>, className: ClassName): FunSpec {
        return FunSpec.builder("fromMap")
            .addModifiers(KModifier.PUBLIC)
            .addParameter("map", LinkedHashMap::class.asClassName().parameterizedBy(
                String::class.asClassName(),
                LinkedHashMap::class.asClassName().parameterizedBy(
                    String::class.asClassName(),
                    String::class.asClassName()
                )
            ))
            .returns(className)
            .apply {
                for (property in properties) {
                    val propertyName = property.name
                    val propertyType = property.type
                    val filterType = propertyType
                        .copy(nullable = false)

//                    addStatement(
//                        "var $propertyName = map[\"$propertyName\"]?.let { LinkedHashMap(it) }"
//                    )
                    addCode(
                        "val $propertyName = map[\"$propertyName\"]?.let { LinkedHashMap(it) }?.let {\n %T().createFilterField(it, %T::class.java, \"$propertyName\") as %T?\n}\n",
                        ClassName("com.example.paymentsv2.robert.filters", "Filter"),
                        filterType,
                        filterType
                    )
                }

                addStatement(
                    "return %T(" + properties.joinToString(", ") { it.name } + ")",
                    className
                )
            }
            .build()
    }

    fun generateFilter(input: InputObjectTypeDefinition) {
        val className = input.name.capitalize()
        val packageName = basePackage+ ".filters"
        val baseFilterClassName = ClassName("com.example.paymentsv2.robert.filters", "Filter")

        // Create properties for the data class
        val properties = input.inputValueDefinitions.map { inputField ->
            val fieldName = inputField.name
            val type = inputField.type as TypeName
            val typeName = type.name
            val defaultValue = inputField.defaultValue

            PropertySpec.builder(fieldName, ClassName("com.example.paymentsv2.robert.filters", typeName).copy(nullable = true))
                .mutable()
                .initializer("%T()", ClassName("com.example.paymentsv2.robert.filters", typeName))
//                .addAnnotation(AnnotationSpec.builder(JsonProperty::class)
//                    .addMember("\"${inputField.name}\"")
//                    .build()
//                )
                .apply {
                    if (defaultValue != null) {
                        initializer("%T(%S)", ClassName("com.example.paymentsv2.robert.filters", typeName), defaultValue)
                    }
                }
                .build()
        }

        val companion = TypeSpec.companionObjectBuilder()
            .addFunction(generateFromMapFunction(properties, ClassName(packageName, className)))
            .build()


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
            .addType(companion)
            .build()

        val file = FileSpec.builder(packageName, className)
            .addType(filterClassTypeSpec)
            .build()

        file.writeTo(File(packageDir))
    }
}
