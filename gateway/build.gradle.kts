dependencies {
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}
