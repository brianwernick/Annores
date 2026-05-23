import util.ProjectConfig

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.devbrackets.android.annoresdemo"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = "com.devbrackets.android.annoresdemo"
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = 1
        versionName = "1.0.0-demo"
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
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(project(":library"))

    implementation(libs.appcompat)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.material.icons.core)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
}
