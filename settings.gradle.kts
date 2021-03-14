pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://repo.spring.io/release") }
    }
}

rootProject.name = "diploma"

include(":diploma-client")
include(":diploma-server:commons")
include(":diploma-server:rest-api")
include(":diploma-shared")
include(":diploma-test-utils")
