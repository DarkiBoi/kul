plugins {
    id("com.github.johnrengelman.shadow") version "4.0.4"
    java
    kotlin("jvm") version "1.3.72"
}

group = "com.kul"
version = "1.0"

var hopliteVersion = "1.2.3"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.ow2.asm", "asm", "7.3.1")
    implementation("org.ow2.asm", "asm-tree", "7.3.1")
    implementation("org.ow2.asm", "asm-commons", "7.3.1")
    implementation("org.ow2.asm", "asm-util", "7.3.1")
    implementation("com.google.guava:guava:26.0-jre")

    implementation("com.sksamuel.hoplite:hoplite-core:$hopliteVersion")
    implementation("com.sksamuel.hoplite:hoplite-yaml:$hopliteVersion")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        classifier = "release"
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.kul.MainKt"))
        }
    }

    named<Jar>("jar") {

        manifest {
            attributes(mapOf("Main-Class" to "com.kul.MainKt"))
        }

    }

}

tasks {
    build {
        dependsOn(shadowJar)
    }
}