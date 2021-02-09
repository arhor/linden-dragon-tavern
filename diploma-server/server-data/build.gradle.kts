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
description = "server-data"

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
    kapt("org.springframework:spring-context-indexer")

    runtimeOnly("org.postgresql:postgresql")

    implementation(project(":diploma-server:server-commons"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    api("org.springframework.boot:spring-boot-starter-cache")
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.flywaydb:flyway-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:postgresql:${Deps.testcontainers}")
    testImplementation("org.testcontainers:junit-jupiter:${Deps.testcontainers}")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Deps.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Deps.javaGlobal)
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

flyway {// move to the env variables
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/diploma_db"
    user = "postgres"
    password = "password"
    locations = arrayOf("classpath:db/migration")
    encoding = "UTF-8"
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }

    withType<AbstractFlywayTask> {
        dependsOn("processResources")
    }

    withType<Test> {
        useJUnitPlatform()
    }
}
