plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.noarg")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "diploma-server"

configurations {
    developmentOnly
    runtimeClasspath {
        extendsFrom(developmentOnly.get())
    }
    implementation {
        exclude(module = "spring-boot-starter-tomcat")
    }
    testImplementation {
        exclude(module = "junit-vintage-engine")
    }
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    kapt("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")
    kapt("org.springframework:spring-context-indexer")

    implementation(project(":diploma-shared"))
    implementation(project(":diploma-server:server-commons"))
    implementation(project(":diploma-server:server-data"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.jsonwebtoken:jjwt:${Versions.jsonWebToken}")
    implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
    implementation("org.apache.pdfbox:pdfbox:${Versions.pdfBox}")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

val applicationMainClass = "org.arhor.diploma.DiplomaApp"
val springActiveProfiles = "-Dspring.profiles.active=default,dev"

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

springBoot {
    mainClass.set(applicationMainClass)
}

tasks {
    withType<Test> {
        useJUnitPlatform {
            excludeTags(/*"integration"*/)
        }
        jvmArgs = listOf(springActiveProfiles)
    }

    jar {
        manifest {
            attributes["Main-Class"] = applicationMainClass
        }
    }

    bootRun {
        jvmArgs = listOf(
            springActiveProfiles,
            "-XX:+UseSerialGC",
            "-XX:MaxRAM=100m",
            "-Xss512k"
        )
    }
}
