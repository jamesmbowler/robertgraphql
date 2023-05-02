import com.example.RobGenerator
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.10"
	kotlin("plugin.spring") version "1.8.10"
	id("org.hibernate.orm") version "6.2.0.CR4"
	kotlin("plugin.jpa") version "1.8.10"
	id("com.netflix.dgs.codegen") version "5.7.0"
	id("com.example.myplugin")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

configurations {
	all {
		exclude("spring-boot-starter-logging")
		exclude("logback-classic")
	}
}

dependencies {
	implementation("ch.qos.logback:logback-classic:1.4.7")
	implementation("org.slf4j:slf4j-api:2.0.7")

	implementation("com.netflix.graphql.dgs.codegen:graphql-dgs-codegen-core:5.7.1")
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:6.0.1"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
	implementation("com.graphql-java:graphql-java:19.2")

	//implementation("org.springframework.boot:spring-boot-starter-data-rest")
	//implementation("org.springframework.boot:spring-boot-starter-graphql")
	//implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("jakarta.annotation:jakarta.annotation-api")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	//developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	//annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	//testImplementation("org.springframework.boot:spring-boot-starter-test")
	//testImplementation("org.springframework:spring-webflux")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	//implementation(gradleApi())
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

//tasks.register<JavaExec>("runTableService") {
//	classpath = sourceSets["main"].runtimeClasspath
//	mainClass.set("com.example.paymentsv2.robert.TableServiceMain")
//	args("--spring.main.web-application-type=none", "--spring.main.lazy-initialization=true", "--tableservice.run-on-startup=true")
//}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
	schemaPaths = mutableListOf<Any>("${projectDir}/src/main/resources/rob_schema")
	packageName = "com.example.paymentsv2.generated" // The package name to use to generate sources
	generateClient = true // Enable generating the type safe query API
	typeMapping = mutableMapOf(
		"PositiveInt" to "kotlin.Int"
	)
}

hibernate {
	enhancement
}
tasks.withType<RobGenerator> {
	schemaPaths = mutableListOf<Any>("${projectDir}/src/main/resources/rob_schema")
	packageName = "com.example.paymentsv2.robgen"
	outputs.upToDateWhen { false }
}