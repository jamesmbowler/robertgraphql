
plugins {
    `kotlin-dsl`
    id("com.netflix.dgs.codegen") version "5.7.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.reflections:reflections:0.9.12")
    //implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.netflix.graphql.dgs:graphql-dgs")
    implementation("com.netflix.graphql.dgs.codegen:graphql-dgs-codegen-core:5.7.1")
    implementation("com.netflix.graphql.dgs.codegen:graphql-dgs-codegen-shared-core:5.7.1")
}
