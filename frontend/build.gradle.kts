plugins {
    base
    id("com.github.node-gradle.node") version "7.0.1"
}

configure(subprojects) {
    apply(plugin = "com.github.node-gradle.node")

    node {
        version = "20.9.0"
        npmVersion = "10.1.0"
        download = true
    }

    tasks.register<Delete>("clean") {
        delete("dist")
    }
    rootProject.tasks["clean"].dependsOn(tasks["clean"])
}

configure(tasks) { enabled = false }
