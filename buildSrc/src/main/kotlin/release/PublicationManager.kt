package release

import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication

object PublicationManager {
    @JvmStatic
    fun configure(
        project: Project,
        publication: MavenPublication,
        vararg artifacts: Any,
    ) {
        val libraryInfo = ReleaseInfo.getLibraryInfo(project)

        project.group = libraryInfo.groupId
        project.version = libraryInfo.version.name

        publication.apply {
            groupId = libraryInfo.groupId
            artifactId = libraryInfo.artifactId
            version = libraryInfo.version.name

            setArtifacts(artifacts.asIterable())

            pom {
                configure(
                    libraryInfo = libraryInfo,
                    project = project
                )
            }
        }
    }

    private fun MavenPom.configure(
        libraryInfo: LibraryInfo,
        project: Project,
    ) {
        description?.set("A Jetpack Compose library for retrieving annotated string resources")
        name?.set(libraryInfo.groupId + ":" + libraryInfo.artifactId)
        url?.set("https://github.com/brianwernick/Annores")

        developers {
            developer {
                name?.set("Brian Wernick")
                email?.set("brian@devbrackets.com")
                organization?.set("DevBrackets")
                organizationUrl?.set("https://devbrackets.com")
            }
        }

        licenses {
            license {
                name?.set("The Apache License, Version 2.0")
                url?.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        scm {
            connection?.set("scm:git:github.com/brianwernick/Annores.git")
            developerConnection?.set("scm:git:ssh://github.com/brianwernick/Annores.git")
            url?.set("https://github.com/brianwernick/Annores/tree/main")
        }

        // The generated POM doesn't include dependencies for Android artifacts,
        // so we manually add them here
        withXml {
            Dependencies.appendAll(
                node = asNode().appendNode("dependencies"),
                configurations = project.configurations
            )
        }
    }
}