dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.register<Copy>("copySPA") {
	from(project(":frontend:client").file("dist/client/")).into("build/resources/main/static")
}

tasks["copySPA"].dependsOn(project(":frontend:client").tasks["npm_run_build"])
tasks["processResources"].dependsOn(tasks["copySPA"])
