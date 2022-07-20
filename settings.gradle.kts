pluginManagement {
	repositories {
		gradlePluginPortal()
		google()
		mavenCentral()
	}
}

dependencyResolutionManagement {
	repositories {
		google()
		mavenCentral()
	}
}

rootProject.name = "currency"
include(
	":app",
	":core",
	":features:popular",
	":features:favorite",
	":features:sorting",
	":shared:exchange",
)
