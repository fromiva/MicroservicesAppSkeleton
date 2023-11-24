description = "Microservices Application Skeleton"

plugins { base }

configure(allprojects) {
    group = "com.example.app"
    version = "0.0.1-SNAPSHOT"
    repositories { mavenCentral() }
}

configure(tasks) { enabled = false }
