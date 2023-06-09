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
	implementation("org.springframework.session:spring-session-jdbc")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

	implementation("org.thymeleaf:thymeleaf-spring6:3.1.1.RELEASE")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")

	implementation("com.google.firebase:firebase-admin:9.1.1")

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("jakarta.annotation:jakarta.annotation-api")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("io.hypersistence:hypersistence-utils-hibernate-62:3.3.2")
	implementation("com.fasterxml.jackson.module:jackson-module-jakarta-xmlbind-annotations")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("org.springframework.boot:spring-boot-devtools")
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
	generateClient = false // Enable generating the type safe query API
	typeMapping = mutableMapOf(
		"PositiveInt" to "kotlin.Int",
		"Long" to "kotlin.Long",
		"BigDecimal" to "java.math.BigDecimal"
	)
	maxProjectionDepth = 10
}

hibernate {
	enhancement
}
tasks.withType<RobGenerator> {
	schemaPaths = mutableListOf<Any>("${projectDir}/src/main/resources/rob_schema")
	packageName = "com.example.paymentsv2.robgen"
	outputs.upToDateWhen { false }
}

