import org.flywaydb.gradle.task.AbstractFlywayTask

plugins {
    id("org.flywaydb.flyway")
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    id("com.github.ayltai.spring-graalvm-native-plugin") version "1.4.3"

    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.noarg")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "diploma-server:rest-api"

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

    implementation("javax.cache:cache-api:1.0.0") // required to compile native-image

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.flywaydb:flyway-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:postgresql:${Versions.testcontainers}")
    testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
}

val springActiveProfiles = "-Dspring.profiles.active=default,dev,test"

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

kapt {
    includeCompileClasspath = false
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

flyway {
    url = System.getenv("DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/diploma_db"
    user = System.getenv("DATABASE_USERNAME") ?: "postgres"
    password = System.getenv("DATABASE_PASSWORD") ?: "password"

    driver = "org.postgresql.Driver"
    encoding = "UTF-8"
    locations = arrayOf("classpath:db/migration")
}

nativeImage {
    mainClassName = "org.arhor.diploma.DiplomaAppKt"

    reportExceptionStackTraces      = true
    traceClassInitializationFor     = listOf("")
    maxHeapSize                     = "8G"
    javaVersion                     = "11"
    toolVersion                     = "21.0.0.2"

    removeXmlSupport  = true
    removeSpelSupport = false
    removeYamlSupport = false
    removeJmxSupport  = true

    initializeAtBuildTime = listOf(
        "jdk.management.jfr.SettingDescriptorInfo",
        "jdk.xml.internal.SecuritySupport",
        "jdk.xml.internal.JdkXmlUtils",
        "javax.xml.parsers.FactoryFinder",
        "com.oracle.truffle.js.scriptengine.GraalJSEngineFactory",
        "com.sun.org.apache.xerces.internal.util.PropertyState",
        "com.sun.org.apache.xerces.internal.impl.XMLEntityManager",
        "com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl",
        "com.sun.org.apache.xerces.internal.impl.dtd.XMLNSDTDValidator",
        "com.sun.org.apache.xerces.internal.util.FeatureState",
        "com.sun.org.apache.xerces.internal.impl.XMLEntityScanner",
        "com.sun.org.apache.xerces.internal.impl.XMLScanner",
        "com.sun.org.apache.xerces.internal.impl.XMLDTDScannerImpl",
        "com.sun.org.apache.xerces.internal.impl.Constants",
        "com.sun.org.apache.xerces.internal.impl.XMLVersionDetector",
        "com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl",
        "com.sun.org.apache.xerces.internal.util.XMLSymbols",
        "com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDProcessor",
        "com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidator",
        "com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl",
        "com.sun.org.apache.xerces.internal.util.XMLChar",
        "com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl",
        "com.sun.org.apache.xerces.internal.impl.XMLEntityManager\$EncodingInfo",
        "com.sun.org.apache.xerces.internal.xni.NamespaceContext",
        "com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator",
        "com.sun.jmx.mbeanserver.Introspector",
        "com.sun.jmx.defaults.JmxProperties",
        "com.sun.jmx.mbeanserver.MBeanInstantiator",
        "com.sun.jmx.mbeanserver.MXBeanLookup",
        "org.apache.commons.logging.LogFactory",
        "org.apache.logging.log4j.core.async.AsyncLoggerContext",
        "org.apache.logging.log4j.core.config.yaml.YamlConfiguration",
        "org.apache.logging.log4j.core.pattern.JAnsiTextRenderer",
        "org.springframework.core.codec.Decoder"
    )
}

tasks {
    bootRun {
        jvmArgs = listOf(
            springActiveProfiles,
            "-XX:+UseSerialGC",
            "-XX:MaxRAM=100m",
            "-Xss512k"
        )
    }

    withType<AbstractFlywayTask> {
        dependsOn("processResources")
    }

    withType<Test> {
        useJUnitPlatform {
            excludeTags("integration-test")
        }
        jvmArgs = listOf(springActiveProfiles)
    }
}
