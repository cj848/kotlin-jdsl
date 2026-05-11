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

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Core Dependencies
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlQueryModel)
    implementation(projects.jpqlRender)

    // Spring Boot & JPA
    compileOnly(libs.spring.boot4.starter.data.jpa)

    // Test
    testImplementation(libs.spring.boot4.starter.data.jpa)
    testImplementation(libs.spring.boot4.starter.test)
    testImplementation(libs.junit6)
    testImplementation(libs.junit6.platform.engine)
    testImplementation(libs.junit6.platform.launcher)
    testImplementation(libs.mockk)
    testImplementation(libs.jakarta.persistence32.api)
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
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
