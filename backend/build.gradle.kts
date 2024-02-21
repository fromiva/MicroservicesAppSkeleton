import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "Microservices Application Skeleton"

plugins {
    base
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

configure(allprojects) {
    group = "com.example.app"
    version = "0.0.1-SNAPSHOT"
    repositories { mavenCentral() }
}

configure(subprojects) {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")

    java { sourceCompatibility = JavaVersion.VERSION_21 }
    extra["springCloudVersion"] = "2023.0.0"

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
            jvmTarget = "21"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        systemProperty("eureka.client.enabled", "false")
    }


    tasks.bootBuildImage {
        builder.set("paketobuildpacks/builder-jammy-base:latest")
    }

    rootProject.tasks["bootJar"].dependsOn(tasks["bootJar"])
}

configure(subprojects
        - project("registry")
        - project("config")) {

    dependencies {
        implementation("org.springframework.cloud:spring-cloud-starter-config")
    }
}

configure(tasks) { enabled = false }
