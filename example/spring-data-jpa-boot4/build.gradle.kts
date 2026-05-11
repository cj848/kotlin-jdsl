plugins {
    alias(exampleLibs.plugins.spring.boot4)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

group = "com.linecorp.kotlin-jdsl"
version = "3.10.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(exampleLibs.spring.boot4.jpa)
    implementation(exampleLibs.spring.boot4.p6spy)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.springDataJpaBoot4Support)

    runtimeOnly(exampleLibs.h2)

    testImplementation(exampleLibs.spring.boot4.test)
    testImplementation(libs.mockk)
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

// Disable bootJar as we usually run tests or use it as a library reference
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
