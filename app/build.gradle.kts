import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "net.imshit.aircraftwar"
    compileSdk = 33
    defaultConfig {
        applicationId = namespace
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        resourceConfigurations.addAll(listOf("zh", "en"))
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("js") {
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
        }
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs["debug"]
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["js"]
        }
    }
//    /**
//     * The productFlavors block is where you can configure multiple product flavors.
//     * This lets you create different versions of your app that can
//     * override the defaultConfig block with their own settings. Product flavors
//     * are optional, and the build system does not create them by default.
//     *
//     * This example creates a free and paid product flavor. Each product flavor
//     * then specifies its own application ID, so that they can exist on the Google
//     * Play Store, or an Android device, simultaneously.
//     *
//     * If you declare product flavors, you must also declare flavor dimensions
//     * and assign each flavor to a flavor dimension.*/
//    flavorDimensions "name"
//    productFlavors {
//        trial {
//            dimension "name"
//            versionNameSuffix "trial"
//        }
//        normal {
//            dimension "name"
//            versionNameSuffix ""
//        }
//    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}
/**
 * The dependencies block in the module-level build configuration file
 * specifies dependencies required to build only the module itself.
 * To learn more, go to Add build dependencies.
 */
dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.compose.material3:material3-android:1.1.0-beta02")
    implementation("androidx.compose.material3:material3-window-size-class-android:1.1.0-beta02")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}