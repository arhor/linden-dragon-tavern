plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "server-commons"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.code.findbugs:jsr305:${Deps.findbugsJsr305}")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.assertj:assertj-core:${Deps.assertJCore}")
    testImplementation("org.mockito:mockito-core:${Deps.mockitoCore}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Deps.junitJupiter}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Deps.junitJupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Deps.junitJupiter}")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Deps.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Deps.javaGlobal)
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

