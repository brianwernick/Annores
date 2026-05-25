import release.PublicationManager
import util.ProjectConfig

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
}

dependencies {
    // Kotlin
    api(libs.kotlin.stdlib)

    // Compose
    api(libs.ui)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
}

android {
    namespace = "com.devbrackets.android.annores"

    compileSdk = ProjectConfig.compileSdk
    defaultConfig {
        minSdk = ProjectConfig.minSdk
    }

    sourceSets {
        getByName("main") {
            kotlin.directories.add("src/main/kotlin")
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    lint {
        abortOnError = false
    }
}

tasks.register<Jar>("androidJavaDocJar") {
    description = "Builds the Javadoc Jar needed for publication"
    archiveClassifier.set("javadoc")

    from(tasks.dokkaGenerate)
}

tasks.register<Jar>("androidSourcesJar") {
    description = "Builds the Sources Jar needed for publication"
    archiveClassifier.set("sources")

    android.sourceSets {
        from(getByName("main").kotlin.directories)
    }
}

/**
 * Deploy to Maven Central (Sonatype)
 * `$ ./gradlew clean library:assembleRelease androidJavaDocJar androidSourcesJar generatePomFileForNexusPublication publishNexusPublicationToSonatypeRepository closeSonatypeStagingRepository`
 *
 * Publish locally (~/.m2):
 * `$ ./gradlew clean library:assembleRelease androidJavaDocJar androidSourcesJar generatePomFileForNexusPublication publishToMavenLocal -PskipSigning=true`
 *
 * ** NOTE: **
 * This expects the following environment variables to be present
 *  - `SONATYPE_TOKEN_USERNAME` : The username for the user token in Sonatype
 *  - `SONATYPE_TOKEN_PASSWORD` : The password for the user token in Sonatype
 *  - `SIGNING_KEY_ID` : The ID for the GPG signing key to sign the library with
 *  - `SIGNING_KEY` : The GPG key to sign the library with
 *  - `SIGNING_KEY_PASSWORD` : The password for the `SIGNING_KEY`
 */
afterEvaluate {
    publishing {
        publications {
            PublicationManager.configure(
                project,
                create<MavenPublication>("nexus"),
                tasks.getByName("bundleReleaseAar"),
                tasks.getByName("androidJavaDocJar"),
                tasks.getByName("androidSourcesJar")
            )
        }
    }

    signing {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY_ID"),
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_KEY_PASSWORD"),
        )

        val skipSigning = findProperty("skipSigning")?.toString()?.toBoolean() ?: false
        isRequired = !skipSigning

        sign(publishing.publications.getByName("nexus"))
    }
}