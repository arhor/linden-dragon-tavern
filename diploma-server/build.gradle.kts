import org.flywaydb.gradle.task.AbstractFlywayTask

plugins {
    id("org.flywaydb.flyway")
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

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven {
        setUrl("https://repo.spring.io/milestone")
    }
}

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

    runtimeOnly("org.postgresql:postgresql")

    implementation(project(":diploma-shared"))
    implementation(project(":diploma-server:commons"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.jsonwebtoken:jjwt:${Versions.jsonWebToken}")
    implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
    implementation("org.apache.pdfbox:pdfbox:${Versions.pdfBox}")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.flywaydb:flyway-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:postgresql:${Versions.testcontainers}")
    testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
}

val applicationMainClass = "org.arhor.diploma.DiplomaApp"
val springActiveProfiles = "-Dspring.profiles.active=default,dev"

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

flyway {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/diploma_db"
    user = "postgres"
    password = "password"
    locations = arrayOf("classpath:db/migration")
    encoding = "UTF-8"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

springBoot {
    mainClass.set(applicationMainClass)
}

tasks {
    withType<AbstractFlywayTask> {
        dependsOn("processResources")
    }

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
