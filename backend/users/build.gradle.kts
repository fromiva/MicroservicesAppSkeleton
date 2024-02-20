dependencies {
	implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("com.h2database:h2")
}
