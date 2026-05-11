import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot4)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kover)
}

group = "com.linecorp.kotlin-jdsl"
version = "3.10.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.spring.boot4.starter.data.jpa)
    compileOnly(libs.spring.batch6.infrastructure)
    compileOnly(libs.jakarta.persistence32.api)
    compileOnly(libs.slf4j)

    // Core Dependencies
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlQueryModel)
    implementation(projects.jpqlRender)

    testImplementation(libs.spring.boot4.starter.data.jpa)
    testImplementation(libs.spring.batch6.infrastructure)
    testImplementation(libs.jakarta.persistence32.api)
    testImplementation(libs.junit6)
    testImplementation(libs.junit6.platform.engine)
    testImplementation(libs.junit6.platform.launcher)
    testImplementation(libs.mockk)
    testImplementation(libs.assertJ)
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xallow-kotlin-package")
    }
}

tasks.test {
    useJUnitPlatform()
}

// Disable bootJar as this is a library
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("")
}
