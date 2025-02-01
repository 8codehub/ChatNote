pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SendMe"
include(":app")
include(":feature")
include(":core-data")
include(":core-domain")
include(":core-ui")
include(":navigation")
include(":common")
include(":feature:home-list")
include(":feature:direct-notes")
include(":feature:home-list:homelist-ui")
include(":feature:home-list:homelist-data")
include(":feature:home-list:homelist-domain")
include(":feature:direct-notes:directnotes-data")
include(":feature:direct-notes:directnotes-domain")
include(":feature:direct-notes:directnotes-ui")
