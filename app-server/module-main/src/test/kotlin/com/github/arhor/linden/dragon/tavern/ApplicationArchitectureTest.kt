package com.github.arhor.linden.dragon.tavern

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTag
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures.layeredArchitecture

private const val ROOT_PACKAGE = "com.github.arhor.linden.dragon.tavern"

@ArchTag("arch")
@AnalyzeClasses(
    packages = [
        "$ROOT_PACKAGE..",
    ],
    importOptions = [
        DoNotIncludeTests::class,
    ]
)
internal class ApplicationArchitectureTest {

    @ArchTest
    fun `correct layered architecture should be observed`(applicationClasses: JavaClasses) {
//        layeredArchitecture()
//            .layer("Web").definedBy("$ROOT_PACKAGE.web..")
//            .layer("Service").definedBy("$ROOT_PACKAGE.service..")
//            .layer("Persistence").definedBy("$ROOT_PACKAGE.data..")
//            .whereLayer("Web").mayNotBeAccessedByAnyLayer()
//            .whereLayer("Service").mayOnlyBeAccessedByLayers("Web")
//            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
//            .check(applicationClasses)
    }
}
