dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.register<Copy>("copySpa") {
	from(project(":spa").file("dist/spa/")).into("build/resources/main/static")
}

tasks["copySpa"].dependsOn(project(":spa").tasks["npm_run_build"])
tasks["processResources"].dependsOn(tasks["copySpa"])
