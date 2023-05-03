package com.example

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.io.File

class FilterUtilGenerator(
    val packageDir: String,
    private val basePackage: String
) {
    fun generate() {
        val packageName = basePackage+ ".utils"
        val filterUtilClass = ClassName(basePackage+ ".utils", "FilterUtils")

        val filterPackageName = basePackage+ ".filters"

        val filterClass = ClassName("com.example.paymentsv2.robert.filters", "Filter")

        val filtersMapType = ClassName("kotlin.collections", "MutableMap.MutableEntry")
            .parameterizedBy(
                String::class.asTypeName(),
                ArrayList::class.asTypeName().parameterizedBy(
                    LinkedHashMap::class.asTypeName().parameterizedBy(
                        String::class.asTypeName(),
                        LinkedHashMap::class.asTypeName().parameterizedBy(
                            String::class.asTypeName(),
                            String::class.asTypeName()
                        )
                    )
                )
            )

        val parameterSpec = ParameterSpec.builder(
            "filters",
            filtersMapType
        ).build()

        val getTypeFunSpec = FunSpec.builder("getType")
            .addParameter("string", String::class)
            .addParameter(parameterSpec)
            .returns(Set::class.asClassName()
                .parameterizedBy(
                    filterClass.copy(nullable = true)))
            .beginControlFlow("return when (string)")
        val filterClassNames = getFilterClassNames(filterPackageName)
        filterClassNames.forEach { it ->
            val filterClassName = ClassName(filterPackageName, it)
            getTypeFunSpec.addStatement(
                "\"$it\" -> filters?.value?.map { ${filterClassName.simpleName}.fromMap(it) }?.toSet() ?: setOf()"
            )
        }

        getTypeFunSpec.addStatement("else -> throw Exception(\"No class found for \$string\")")
            .endControlFlow()
            .build()

        val filterUtilClassTypeSpec =  TypeSpec.classBuilder("FilterUtilClass")
            .addFunction(getTypeFunSpec.build())
            .build()

        val file = FileSpec.builder(packageName, "FilterUtilClass")

        filterClassNames.forEach { it ->
            file.addImport(filterPackageName, it)
        }

        file.addType(filterUtilClassTypeSpec)

        file.build().writeTo(File(packageDir))
    }

    fun getFilterClassNames(packageName: String): List<String> {
        val directoryPath = packageName.replace('.', '/')
        //val classLoader = Thread.currentThread().contextClassLoader
        //val directory = classLoader.getResource("file:"+directoryPath)?.path
        val dir = File("src/main/kotlin/"+ directoryPath)  ?: return emptyList()
        val filterFiles = dir.listFiles()
            //{ _, name -> name.endsWith("Filter.kt") }
                ?: return emptyList()

        return filterFiles.map { file ->
            file.nameWithoutExtension
        }
    }


}