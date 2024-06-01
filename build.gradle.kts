import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java-library")
    id("net.kyori.indra.checkstyle") version "3.1.3"
}

defaultTasks("clean", "build")

logger.lifecycle("""
*******************************************
 You are building WorldGuardRegionProtect!
 If you encounter trouble:
 1) Try running 'build' in a separate Gradle run
 2) Use gradlew and not gradle
 3) If you have a problem, you can join us discord https://discord.gg/kvqvA3GTVF
 
 Output files will be in [subproject]/build/libs
*******************************************
""")

if (!File("$rootDir/.git").exists()) {
    logger.lifecycle("""
    **************************************************************************************
    You need to fork and clone this repository! Don't download a .zip file.
    If you need assistance, consult the GitHub docs: https://docs.github.com/get-started/quickstart/fork-a-repo
    **************************************************************************************
    """.trimIndent()
    ).also { kotlin.system.exitProcess(1) }
}

allprojects {
    //checkstyle
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

subprojects {
    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    tasks.withType<Test> {
        testLogging {
            events = mutableSetOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
    }

}
