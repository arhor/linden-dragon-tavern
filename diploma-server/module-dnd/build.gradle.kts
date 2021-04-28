plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.spring")
}

version = "0.0.1-SNAPSHOT"
description = "diploma-server:module-dnd"

configurations {
    testImplementation {
        exclude(module = "junit-vintage-engine")
    }
}

dependencies {
    kapt("org.springframework:spring-context-indexer")

    implementation(projects.diplomaServer.commons)

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
    implementation("org.apache.pdfbox:pdfbox:${Versions.pdfBox}")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation(projects.diplomaTestUtils)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

kapt {
    includeCompileClasspath = false
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }

    withType<Test> {
        useJUnitPlatform()
    }
}
