plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "diploma-shared"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = Versions.javaGlobal
            }
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(/*IR*/) {
        browser()
        compilations["main"].kotlinOptions {
                metaInfo = false
                sourceMap = false
                moduleKind = "commonjs"
                outputFile = "${project.buildDir}/compiled-js/${project.name}.js"

        }
    }

    sourceSets {
        // Common
        val commonMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-common")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        // JVM
        val jvmMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
            }
        }

        // JS
        val jsMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-js")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-js")
            }
        }
    }
}
