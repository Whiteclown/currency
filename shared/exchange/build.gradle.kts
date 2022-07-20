plugins {
	id(Dependencies.Plugins.ANDROID_LIBRARY)
	id(Dependencies.Plugins.KOTLIN_ANDROID)
	kotlin(Dependencies.Plugins.KAPT)
	kotlin(Dependencies.Plugins.SERIALIZATION) version Dependencies.Versions.SERIALIZATION
	id(Dependencies.Plugins.HILT)
	`android-kotlin-convention`
}

dependencies {
	kapt(Dependencies.Hilt.COMPILER)
	implementation(Dependencies.Hilt.ANDROID)

	implementation(Dependencies.Core.CORE)

	implementation(Dependencies.Network.RETROFIT)
	implementation(Dependencies.Network.OKHTTP)
	implementation(Dependencies.Network.OKHTTP_LOGGING_INTERCEPTOR)
	implementation(Dependencies.Network.KOTLINX_SERIALIZATION)
	implementation(Dependencies.Network.KOTLINX_CONVERTER)

	val roomVersion = "2.4.2"

	implementation("androidx.room:room-runtime:$roomVersion")
	annotationProcessor("androidx.room:room-compiler:$roomVersion")

	// To use Kotlin annotation processing tool (kapt)
	kapt("androidx.room:room-compiler:$roomVersion")

	// optional - Kotlin Extensions and Coroutines support for Room
	implementation("androidx.room:room-ktx:$roomVersion")

	implementation("com.google.code.gson:gson:2.8.9")
}