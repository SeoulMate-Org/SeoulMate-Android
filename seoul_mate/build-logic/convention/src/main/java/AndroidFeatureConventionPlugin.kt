import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "seoulmate.android.library")
            apply(plugin = "seoulmate.hilt")

            dependencies {
                "implementation"(project(":core:ui"))
                "implementation"(project(":navigator"))
            }
        }
    }
}