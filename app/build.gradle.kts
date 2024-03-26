plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)

}

android {
    namespace = "com.boikinhdich.quekinhdich"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.boikinhdich.quekinhdich"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        buildFeatures {
            viewBinding = true
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
        sourceSets {
            getByName("main") {
                assets {
                    srcDirs("src/main/assets")
                }
            }
        }
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        implementation("com.google.code.gson:gson:2.8.8")
        implementation("com.mlsdev.animatedrv:library:2.0.0")
        implementation("tk.zielony:carbon:0.16.0.1")
        implementation("com.google.android.play:review:2.0.1")
        implementation("com.google.android.play:review-ktx:2.0.1")

        //firebase
        implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
        implementation("com.google.firebase:firebase-analytics")
        implementation("com.google.firebase:firebase-crashlytics")
        implementation("com.google.firebase:firebase-storage-ktx")

        //Remote Config gradle dependency
        implementation("com.google.firebase:firebase-config:21.5.0")

        //admob
        implementation("com.google.android.gms:play-services-ads:22.6.0")

//        implementation("com.github.Dinhvietanhk5.NSExtension:nsdialog:3.0.1")

        //rating bar
//        implementation("com.hedgehog.ratingbar:app:1.1.2")

    }
}

dependencies {
    implementation(libs.firebase.config)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.ads)
}