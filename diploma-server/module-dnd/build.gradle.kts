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

    implementation(project(":diploma-shared"))
    implementation(project(":diploma-server:commons"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
    implementation("org.apache.pdfbox:pdfbox:${Versions.pdfBox}")

    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:${Versions.testcontainers}")
    testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
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
        useJUnitPlatform {
            excludeTags("integration-test")
        }
        jvmArgs = listOf(
            "-Dspring.profiles.active=test"
        )
    }
}
