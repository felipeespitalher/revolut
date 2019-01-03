val kotlinVersion = "1.3.11"

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11")
    }
}

plugins {
    kotlin("jvm") version "1.3.11"
    application
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("com.typesafe.akka:akka-http_2.12:10.1.6")

    implementation("com.typesafe.akka:akka-stream_2.12:2.5.9")

    implementation("com.typesafe.akka:akka-http-jackson_2.12:10.1.6")

    implementation("com.google.inject:guice:4.1.0")

    implementation("org.reflections:reflections:0.9.11")

    //testCompile 'com.typesafe.akka:akka-testkit_2.12:2.5.19'
    //testCompile 'junit:junit:4.12'
}

application {
    applicationName = "Revolut"
    group = "com.revolut"
    mainClassName = "com.revolut.MainApplication"
}

repositories {
    mavenCentral()
}

tasks {
    withType<Jar> {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
        val version = "1.0.0"

        archiveName = "${application.applicationName}-$version.jar"

        doFirst {
            configurations.compile.map { if (it.isDirectory) it else zipTree(it) }
        }
    }
}