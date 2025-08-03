import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrains.kotlinAndroid)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
    alias(libs.plugins.hiltAndroid)
    id("kotlin-parcelize")
    alias(libs.plugins.androidJunit5)
}

if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "com.google.firebase.crashlytics")
}

if (file("agconnect-services.json").exists()) {
    apply(plugin = "com.huawei.agconnect")
}

android {
    namespace = "com.ronaldsantos.catholicliturgy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ronaldsantos.catholicliturgy"
        minSdk = 24
        targetSdk = 36
        versionCode = 1004
        versionName = "1.0.4"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            ndk {
                debugSymbolLevel = "FULL"
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            resValue("string", "build_variant", "release")
        }

        create("releaseLog") {
            initWith(buildTypes.getByName("release"))
            isDebuggable = true
            resValue("string", "build_variant", "releaseLog")
        }

        debug {
            applicationIdSuffix = ".dev"
            enableUnitTestCoverage = false
            enableAndroidTestCoverage = false
            isDebuggable = true
            resValue("string", "build_variant", "debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

android.applicationVariants.all {
    val variantName = name
    kotlin.sourceSets {
        getByName("main") {
            kotlin.srcDir(File("build/generated/ksp/$variantName/kotlin"))
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.core)

    implementation(libs.bundles.navigation)
    ksp(libs.destinationKsp)

    implementation(libs.bundles.accompanist)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.bundles.testRuntime)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.androidTest)
    androidTestRuntimeOnly(libs.bundles.androidTestRuntime)

    debugImplementation(libs.bundles.androidXDebug)

    implementation(libs.bundles.network)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // hilt
    implementation(libs.bundles.hilt)
    kapt(libs.com.google.dagger.hilt.compiler)

    // Compose Markdown
    implementation(libs.composeMarkdown)

    // Firebase
    implementation(platform(libs.com.google.firebase.bom))
    implementation(libs.bundles.firebase)

    // Paging
    implementation(libs.androidx.paging.compose)
    compileOnly(libs.androidx.paging.runtime)
}