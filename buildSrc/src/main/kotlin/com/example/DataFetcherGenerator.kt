package com.example

/*
 *
 *  Copyright 2020 Netflix, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import graphql.language.*
import graphql.language.TypeName
import java.io.File

class DatafetcherGenerator(
    val packageDir: String,
    private val packageName: String,
    private val document: Document
) {
    fun generate(query: ObjectTypeDefinition): List<Boolean?>? {
        return query.fieldDefinitions.map { field ->
            createDatafetcher(field)
        }
    }

    private fun createDatafetcher(field: FieldDefinition): Boolean? {
        val fetcherPackageName = "$packageName.datafetchers"

        val name = field.name.capitalize()
        val dataFetcherClassName = ClassName(fetcherPackageName, name+ "DataFetcher")
        var filterClassName = ClassName(packageName + ".filters", name+"Filter")
        //val model = ClassName(packageName + ".models", name)

        val filterArgument = getFieldFilterArgument(field.inputValueDefinitions)

        val modelType = getUnwrappedTypeReturn(field) as TypeName
        val model = ClassName(packageName.substringBeforeLast(".") + ".models", modelType.name)

        if (filterArgument != null) {
            val filterType = getUnwrappedType(filterArgument) as TypeName
            val filterTypeName = filterType.name
            val filter = document.definitions.filterIsInstance<InputObjectTypeDefinition>().filter {
                it.name == filterTypeName
            }.first()
            filterClassName = ClassName(packageName + ".filters", filterTypeName)
            FilterGenerator(packageDir, packageName).generateFilter(filter)

        }
        //FilterUtilGenerator(packageDir, packageName).generate()

        RepositoryGenerator(packageDir, packageName).generateRepository(name, modelType.name)

        val repository = ClassName(packageName + ".repositories", name + "Repository")
        val joinChildren = ClassName(packageName.substringBeforeLast(".") + ".robert.utils", "JoinChildren")
        val dataFetchingEnvironment = ClassName("graphql.schema", "DataFetchingEnvironment")
        val specification = ClassName("org.springframework.data.jpa.domain", "Specification")
        val dgsComponent = ClassName("com.netflix.graphql.dgs", "DgsComponent")
        val dgsQuery = ClassName("com.netflix.graphql.dgs", "DgsQuery")
        val inputArgument = ClassName("com.netflix.graphql.dgs", "InputArgument")
        val autowired = ClassName("org.springframework.beans.factory.annotation", "Autowired")

        val dataFetcher = TypeSpec.classBuilder(dataFetcherClassName)
            .addAnnotation(dgsComponent)
            .addModifiers(KModifier.PUBLIC)
            .addProperty(
                PropertySpec.builder("repository", repository)
                    .addAnnotation(autowired)
                    .addModifiers(KModifier.LATEINIT)
                    .mutable()
                    .build()
            )
            .addFunction(
                FunSpec.builder(field.name)
                    .addModifiers(KModifier.PUBLIC)
                    .returns(List::class.asClassName().parameterizedBy(model))
                    .addParameter("environment", dataFetchingEnvironment)
                    .addParameter(
                        ParameterSpec.builder("filter", List::class.asClassName().parameterizedBy(filterClassName))
                            //.defaultValue("null")
                            .addAnnotation(inputArgument)
                            .build()
                    )
                    .addStatement(
                        "val spec:%T<%T>? = %T().fetchChildEntity(environment.selectionSet.immediateFields, filter)",
                        specification, model, joinChildren
                    )
                    .addStatement("return repository.findAll(spec!!)")
                    .addAnnotation(dgsQuery)
                    .build()
            )
            .build()

        val file = FileSpec.builder(fetcherPackageName, name + "DataFetcher")
            .addType(dataFetcher)
            .build()

        file.writeTo(File(packageDir))
        return true
    }
    fun getUnwrappedType(inputValueDefinition: InputValueDefinition): Type<out Type<*>>? {
        var type = inputValueDefinition.type
        while (type is ListType) {
            type = type.type
        }
        return type
    }

    fun getUnwrappedTypeReturn(inputValueDefinition: FieldDefinition): Type<out Type<*>>? {
        var type = inputValueDefinition.type
        while (type is ListType) {
            type = type.type
        }
        return type
    }

    fun getFieldFilterArgument(inputValueDefinitions: MutableList<InputValueDefinition>): InputValueDefinition? {
        return inputValueDefinitions.firstOrNull { it.name == "filter"}
    }
}

