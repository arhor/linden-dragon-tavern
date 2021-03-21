pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/release") }
        gradlePluginPortal()
    }
}

rootProject.name = "diploma"

include(":diploma-client")
include(":diploma-server:commons")
include(":diploma-server:rest-api")
include(":diploma-shared")
include(":diploma-test-utils")
include(":diploma-extensions:slf4j-ext")
