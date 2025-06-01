plugins {
    java
    id("com.gradleup.shadow") version("8.3.6")
}

group = "me.xemor"
version = "1.1.0"
java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21


repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://jitpack.io/") }
    maven { url = uri("https://repo.minebench.de/") }
    maven { url = uri("https://repo.extendedclip.com/releases/") }
    maven { url = uri("https://repo.xemor.zip/releases")}
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://mvn-repo.arim.space/lesser-gpl3")}
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://repo.nexomc.com/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("com.fasterxml.jackson.core:jackson-core:2.18.0")
    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.7.0")
    compileOnly("com.nexomc:nexo:1.6.0")
    implementation("me.xemor:configurationdata:4.4.1")
    implementation("me.xemor:foliahacks:1.7.5")
    implementation("space.arim.morepaperlib:morepaperlib:0.4.3")
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.12")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-rc.12")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("com.google.inject:guice:7.0.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        val folder = System.getenv("pluginFolder")
        relocate("me.xemor.configurationdata", "me.xemor.enchantedteleporters.configurationdata")
        relocate("me.xemor.foliahacks", "me.xemor.enchantedteleporters.foliahacks")
        relocate("space.arim.morepaperlib", "me.xemor.enchantedteleporters.morepaperlib")
        relocate("io.github.revxrsal.lamp", "me.xemor.enchantedteleporters.lamp")
        relocate("org.bstats", "me.xemor.enchantedteleporters.bstats")
        relocate("com.google.inject", "me.xemor.enchantedteleporters.guice")
        if (folder != null) {
            destinationDirectory.set(file(folder))
        }

        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    processResources{
        expand(project.properties)

        inputs.property("version", rootProject.version)
        filesMatching("plugin.yml") {
            expand("version" to rootProject.version)
        }
    }
}