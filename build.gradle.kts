import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "Microservices Application Skeleton"

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.github.node-gradle.node") version "7.0.1"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

configure(allprojects) {
    group = "ru.fromiva.app"
    version = "0.0.1-SNAPSHOT"
    repositories { mavenCentral() }
}

configure(subprojects - project("spa")) {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")

    java { sourceCompatibility = JavaVersion.VERSION_17 }
    extra["springCloudVersion"] = "2022.0.4"

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
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
        systemProperty("eureka.client.enabled", "false")
    }


    tasks.bootBuildImage {
        builder.set("paketobuildpacks/builder-jammy-base:latest")
    }
}

project("spa") {
    apply(plugin = "com.github.node-gradle.node")

    node {
        version = "20.9.0"
        npmVersion = "10.1.0"
        download = true
    }

    tasks.register<Delete>("clean") {
        delete("dist")
    }
}

tasks["clean"].dependsOn(project(":spa").tasks["clean"])
configure(tasks) { enabled = false }
