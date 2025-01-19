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
include(":home-list")
include(":direct-notes")
include(":home-list:homelist-ui")
include(":home-list:homelist-data")
include(":home-list:homelist-domain")
include(":direct-notes:directnotes-data")
include(":direct-notes:directnotes-domain")
include(":direct-notes:directnotes-ui")
include(":core-data")
include(":core-domain")
include(":core-ui")
include(":navigation")
