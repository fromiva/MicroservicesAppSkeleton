import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
}

configure(subprojects) {
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

    rootProject.tasks["clean"].dependsOn(tasks["clean"])
}

configure(subprojects
        - project(":backend:registry")
        - project(":backend:config")) {

    dependencies {
        implementation("org.springframework.cloud:spring-cloud-starter-config")
    }
}

configure(tasks) { enabled = false }
