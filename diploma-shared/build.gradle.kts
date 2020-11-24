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
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = Versions.javaGlobal
            }
        }
    }

    js {
        browser()
        compilations["main"].kotlinOptions {
                metaInfo = false
                sourceMap = false
                moduleKind = "commonjs"
                outputFile = "${project.buildDir}/compiled-js/${project.name}.js"

        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-common")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-test-common")
                implementation ("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

            }
        }
        val jvmTest by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

            }
        }
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
