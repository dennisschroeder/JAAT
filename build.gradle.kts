plugins {
    kotlin("jvm") version "1.5.0-RC"
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.5.0-RC"
    kotlin("plugin.jpa") version "1.5.0-RC"
}

group = "com.dennisschroeder"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-spring")
    apply(plugin = "io.spring.dependency-management")


    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xallow-result-return-type", "-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = "1.8"
        }
    }
}


