import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "Microservices Application Skeleton"

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

allprojects {
    group = "ru.fromiva.app"
    version = "0.0.1-SNAPSHOT"
    repositories { mavenCentral() }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")

    java { sourceCompatibility = JavaVersion.VERSION_17 }
    extra["springCloudVersion"] = "2022.0.4"

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.bootBuildImage {
        builder.set("paketobuildpacks/builder-jammy-base:latest")
    }
}

tasks.bootJar.configure { enabled = false }
