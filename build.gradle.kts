// Project-level build script: 플러그인 버전만 선언, 적용은 각 모듈에서
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android)      apply false
    alias(libs.plugins.ksp)                 apply false
    alias(libs.plugins.hilt)                apply false
    alias(libs.plugins.kotlin.parcelize)    apply false
}
