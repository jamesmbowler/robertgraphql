package com.example

import graphql.language.Document
import graphql.language.ObjectTypeDefinition
import graphql.parser.MultiSourceReader
import graphql.parser.Parser
import graphql.parser.ParserOptions
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Paths

open class RobGenerator : DefaultTask() {

    companion object {
        private const val SDL_MAX_ALLOWED_SCHEMA_TOKENS: Int = Int.MAX_VALUE
       // private val logger: Logger = LoggerFactory.getLogger(RobGenerator::class.java)
    }

    @InputFiles
    var schemaPaths = mutableListOf<Any>("${project.projectDir}/src/main/resources/rob_schema")

    @Input
    var packageName = ""

    @OutputDirectory
    fun getOutputDir(): File {
        return Paths.get("${project.projectDir}/src/main/kotlin").toFile()
    }

    @TaskAction
    fun run() {
        val schemaPaths = schemaPaths.map { Paths.get(it.toString()).toFile() }.sorted().toSet()
        schemaPaths.filter { !it.exists() }.forEach {
            logger.warn("Schema location ${it.absolutePath} does not exist")
        }
        logger.info("Processing schema files:")
        schemaPaths.forEach {
            logger.info("Processing $it")
        }

        val parser = Parser()

        val readerBuilder = MultiSourceReader.newMultiSourceReader()

        var schemas: Set<String> = emptySet()

        var schemaFiles = schemaPaths.sorted()
            .flatMap { it.walkTopDown() }
            .filter { it.isFile }
            .filter { it.name.endsWith(".graphql") || it.name.endsWith(".graphqls") }

        for (schemaFile in schemaFiles) {
            readerBuilder.string("\n", "codegen")
            readerBuilder.reader(schemaFile.reader(), schemaFile.name)
        }
        for (schema in schemas) {
            readerBuilder.string(schema, null)
        }
        val options = ParserOptions.getDefaultParserOptions().transform { builder ->
            builder.maxTokens(SDL_MAX_ALLOWED_SCHEMA_TOKENS).maxWhitespaceTokens(SDL_MAX_ALLOWED_SCHEMA_TOKENS)
        }
        val document = readerBuilder.build().use { reader ->
            parser.parseDocument(reader, options)
        }

        generateDataFetchers(document, packageName)
    }

    private fun generateDataFetchers(document: Document, packageName: String) {
        val defs = document.definitions
            .filterIsInstance<ObjectTypeDefinition>()
            .filter { it.name == "Query" }

        var fetchers = defs.map {
            DatafetcherGenerator("${project.projectDir}/src/main/kotlin", packageName, document).generate(it)
        }


        FilterUtilGenerator("${project.projectDir}/src/main/kotlin", packageName).generate()
    }
}