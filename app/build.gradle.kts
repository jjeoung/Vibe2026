plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)          // KAPT 대신 KSP: Room·Hilt 컴파일 속도 향상
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace   = "com.dopaminecat"
    compileSdk  = 34

    defaultConfig {
        applicationId = "com.dopaminecat"
        minSdk        = 26   // O: UsageStatsManager QueryUsageStats 안정화 버전
        targetSdk     = 34
        versionCode   = 1
        versionName   = "1.0.0"

        // Hilt 전용 테스트 러너 (HiltTestApplication 주입)
        testInstrumentationRunner = "com.dopaminecat.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled   = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable        = true
            applicationIdSuffix = ".debug"
            versionNameSuffix   = "-DEBUG"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose     = true
        buildConfig = true  // BuildConfig.DEBUG 등 플래그 사용
    }
    composeOptions {
        // Kotlin 1.9.24 대응 Compose 컴파일러 버전
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Room: 스키마 파일을 버전 관리에 포함 (마이그레이션 추적용)
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

configurations.all {
    resolutionStrategy {
        force("androidx.work:work-runtime:2.9.1")
        force("androidx.work:work-runtime-ktx:2.9.1")
        force("androidx.datastore:datastore-core:1.1.1")
        force("androidx.datastore:datastore-preferences-core:1.1.1")
        force("androidx.lifecycle:lifecycle-runtime:2.7.0")
        force("androidx.lifecycle:lifecycle-common:2.7.0")
        force("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
        force("androidx.core:core:1.13.1")
        force("androidx.core:core-ktx:1.13.1")
        force("androidx.collection:collection:1.4.0")
    }
}

dependencies {
    // ── Core ──────────────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)

    // ── Compose (BOM으로 버전 일괄 관리) ──────────────────────────────────
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    // ── Lifecycle / collectAsStateWithLifecycle ────────────────────────────
    implementation(libs.bundles.lifecycle)

    // ── Navigation ────────────────────────────────────────────────────────
    implementation(libs.navigation.compose)

    // ── Hilt (DI) ─────────────────────────────────────────────────────────
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    ksp(libs.hilt.androidx.compiler)

    // ── Room (고양이 상태 · 코인 · 앱 목표 영속화) ────────────────────────
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // ── DataStore (권한 동의 여부, 온보딩 완료 플래그) ────────────────────
    implementation(libs.datastore.preferences)

    // ── WorkManager (주기적 UsageStats 폴링) ──────────────────────────────
    implementation(libs.work.runtime.ktx)

    // ── Glance (Compose 기반 홈 화면 위젯) ───────────────────────────────
    implementation(libs.bundles.glance)

    // ── Coroutines ────────────────────────────────────────────────────────
    implementation(libs.coroutines.android)

    // ── Unit Tests ────────────────────────────────────────────────────────
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)          // Flow 테스트: .test { }
    testImplementation(libs.coroutines.test)  // runTest, TestScope

    // ── Android Instrumented Tests ────────────────────────────────────────
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.room.testing)  // in-memory Room DB 검증
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // ── Debug Only ────────────────────────────────────────────────────────
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
