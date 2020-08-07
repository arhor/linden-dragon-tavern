plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "diploma-shared"

kotlin {

    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }

    js {
        val main by compilations.getting {
            kotlinOptions {
                metaInfo = false
                sourceMap = false
                moduleKind = "commonjs"
                outputFile = "${project.buildDir}/compiled-js/${project.name}.js"
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

            }
        }
        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }
    }
}
