import java.nio.file.Paths

plugins {
    id("org.flywaydb.flyway")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.spring")
}

version = "0.0.1-SNAPSHOT"
description = "diploma-server:module-main"

configurations {
    implementation {
        exclude(module = "spring-boot-starter-tomcat")
    }
    testImplementation {
        exclude(module = "junit-vintage-engine")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${Versions.testcontainers}")
    }
}

dependencies {
    kapt("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")
    kapt("org.springframework:spring-context-indexer")

    implementation(project(":diploma-server:module-common"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
    implementation("io.jsonwebtoken:jjwt-api:${Versions.jsonWebToken}")
    implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
    implementation("org.flywaydb:flyway-core")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // application modules (except for "module-common") MUST NOT be referenced directly in the module-main
    runtimeOnly(project(":diploma-server:module-dnd"))

    runtimeOnly("io.r2dbc:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${Versions.jsonWebToken}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${Versions.jsonWebToken}")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:r2dbc")

    testRuntimeOnly("org.postgresql:postgresql")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

kapt {
    includeCompileClasspath = false
}

flyway {
    url = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/diploma_db"
    user = System.getenv("JDBC_DATABASE_USERNAME") ?: "postgres"
    password = System.getenv("JDBC_DATABASE_PASSWORD") ?: "password"

    driver = "org.postgresql.Driver"
    encoding = "UTF-8"
    locations = arrayOf("classpath:db/migration")
}

tasks {
    val copyLatestClientBuild = register<Copy>("copyLatestClientBuild") {
        val clientPrjDir = project(":diploma-client").projectDir.toString()
        val serverBldDir = project(":diploma-server:module-main").buildDir.toString()

        from(Paths.get(clientPrjDir, "dist"))
        into(Paths.get(serverBldDir, "resources", "main", "static"))
    }

    processResources { dependsOn(copyLatestClientBuild) }

    withType<Test> {
        useJUnitPlatform()
    }
}
