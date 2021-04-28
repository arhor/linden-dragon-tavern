enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/release") }
        gradlePluginPortal()
    }
}

rootProject.name = "diploma"

include(":diploma-client")
include(":diploma-server:commons")
include(":diploma-server:module-dnd")
include(":diploma-server:server-app")
include(":diploma-shared")
include(":diploma-test-utils")
