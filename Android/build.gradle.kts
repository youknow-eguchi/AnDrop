plugins {
    id("com.android.application") version "7.2.0-alpha05" apply false
    id("com.android.library") version "7.2.0-alpha05" apply false
    kotlin("android") version "1.6.0" apply false
    kotlin("plugin.serialization") version "1.6.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}