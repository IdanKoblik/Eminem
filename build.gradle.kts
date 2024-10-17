plugins {
    kotlin("jvm") version "2.0.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val releaseWorkflow = "IdanKoblik/Jukebox/.github/workflows/release.yml"
val snapshot: Boolean = System.getenv("GITHUB_WORKFLOW_REF") == null || !(System.getenv("GITHUB_WORKFLOW_REF").startsWith(releaseWorkflow))

group = "com.github.idankoblik.eminem"
version = figureVersion()

repositories {
    mavenCentral()
    maven {
        name = "ApartiumNexus"
        url = uri("https://nexus.voigon.dev/repository/apartium/")
    }

    maven {
        name = "apartium-releases"
        url = uri("https://nexus.voigon.dev/repository/apartium-releases")
    }

    maven {
        name = "ApartiumMaven"
        url = uri("https://nexus.voigon.dev/repository/beta-snapshots")
        credentials {
            username = (System.getenv("APARTIUM_NEXUS_USERNAME")
                ?: project.findProperty("apartium.nexus.username")).toString()
            password = (System.getenv("APARTIUM_NEXUS_PASSWORD")
                ?: project.findProperty("apartium.nexus.password")).toString()
        }
    }

    maven {
        name = "ApartiumMaven"
        url = uri("https://nexus.voigon.dev/repository/beta-releases")
        credentials {
            username = (System.getenv("APARTIUM_NEXUS_USERNAME")
                ?: project.findProperty("apartium.nexus.username")).toString()
            password = (System.getenv("APARTIUM_NEXUS_PASSWORD")
                ?: project.findProperty("apartium.nexus.password")).toString()
        }
    }
}

dependencies {
    implementation("com.github.idankoblik.jukebox:paper:dev-20241017.185259-6")
    implementation("com.github.idankoblik.jukebox:shared:dev-20241017.185259-6")
    implementation("com.github.idankoblik.jukebox:adventure:dev-20241017.185259-7")

    implementation("net.apartium.cocoa-beans:common:${findProperty("cocoabeans.version")}")
    implementation("net.kyori:adventure-platform-bukkit:${findProperty("kyoribukkit.version")}")

    compileOnly("org.github.paperspigot:paperspigot-api:${findProperty("paperspigot.version")}")
    compileOnly("net.kyori:adventure-api:${project.findProperty("kyori.version")}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

fun figureVersion(): String {
    return (if (System.getenv("VERSION") == null) "dev" else System.getenv("VERSION")) + (if (snapshot) "-SNAPSHOT" else "")
}