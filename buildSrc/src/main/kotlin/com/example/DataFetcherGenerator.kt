package com.example

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

        val inputArgument = ClassName("com.netflix.graphql.dgs", "InputArgument")

        var filterParam: ParameterSpec? = null

        if (filterArgument != null) {
            val filterType = getUnwrappedType(filterArgument) as TypeName
            val filterTypeName = filterType.name

            val groupFilter = document.definitions.filterIsInstance<InputObjectTypeDefinition>().filter {
                it.name == filterTypeName
            }.first()

            val filterName = groupFilter.inputValueDefinitions.first { it.name == "filter" }
            val filterUnwrapped = getUnwrappedType(filterName) as TypeName

            val filterInputDef = document.definitions.filterIsInstance<InputObjectTypeDefinition>().filter {
                it.name == filterUnwrapped.name
            }.first()

            val groupFilterClassName = ClassName(packageName + ".filters", filterTypeName)
            val filterClassName = ClassName(packageName + ".filters", filterUnwrapped.name)
            FilterGenerator(packageDir, packageName).generateFilter(filterInputDef)

            filterParam = ParameterSpec.builder(
                "filter",
                List::class.asClassName().parameterizedBy(
                    ClassName(packageName.substringBeforeLast(".") + ".robert.filters", "FilterGroup")
                        .parameterizedBy(WildcardTypeName.producerOf(
                            ClassName(packageName.substringBeforeLast(".") + ".robert.filters", "Filter")
                        ))
                ).copy(nullable = true)
            )
                //.defaultValue("null")
                .addAnnotation(inputArgument)
                .build()
        }
        //FilterUtilGenerator(packageDir, packageName).generate()

        RepositoryGenerator(packageDir, packageName).generateRepository(name, modelType.name)

        val repository = ClassName(packageName + ".repositories", name + "Repository")
        val joinChildren = ClassName(packageName.substringBeforeLast(".") + ".robert.utils", "RobQueryBuilder")
        val dataFetchingEnvironment = ClassName("graphql.schema", "DataFetchingEnvironment")
        val specification = ClassName("org.springframework.data.jpa.domain", "Specification")
        val dgsComponent = ClassName("com.netflix.graphql.dgs", "DgsComponent")
        val dgsQuery = ClassName("com.netflix.graphql.dgs", "DgsQuery")

        val autowired = ClassName("org.springframework.beans.factory.annotation", "Autowired")

        val fetchFxn = FunSpec.builder(field.name)
            .addModifiers(KModifier.PUBLIC)
            .returns(List::class.asClassName().parameterizedBy(model))
            .addParameter("environment", dataFetchingEnvironment)
            .addStatement(
                "val spec:%T<%T>? = %T().build(environment.selectionSet.immediateFields, filter)",
                specification, model, joinChildren
            )
            .addStatement("return repository.findAll(spec!!)")
            .addAnnotation(dgsQuery)

        if (filterParam !== null) {
            fetchFxn.addParameter(filterParam)
        }

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
            .addFunction(fetchFxn.build())
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

