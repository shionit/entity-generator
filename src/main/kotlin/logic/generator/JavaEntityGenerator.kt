package logic.generator

import com.github.mustachejava.DefaultMustacheFactory
import model.Definition
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Java Entity file generator.
 */
class JavaEntityGenerator {

    companion object {
        const val JAVA_EXTENSION = ".java"
    }

    fun execute(definition: Definition) {
        val factory = DefaultMustacheFactory()
        val currentDir = Paths.get("").toAbsolutePath()
        val outputRootDir = Paths.get(currentDir.toString(), "src/main/java/")
        outputRootDir.toFile().deleteRecursively()

        // generate Enums
        generateEnums(definition, outputRootDir, factory)

        // generate Entities
        generateEntities(definition, outputRootDir, factory)
    }

    private fun generateEnums(definition: Definition, outputRootDir: Path, factory: DefaultMustacheFactory) {
        val mustache = factory.compile("Enum.mustache")
        definition.enumMap.mapValues {
            val outputDir = Paths.get(
                outputRootDir.toString(),
                it.value.namespace.replace('.', '/')
            )
            outputDir.toFile().mkdirs()
            val outputFile = Paths.get(
                outputDir.toString(),
                it.value.name + JAVA_EXTENSION,
            )
            PrintWriter(BufferedWriter(FileWriter(outputFile.toFile()))).use { w ->
                mustache.execute(w, it.value).flush()
            }
        }
    }

    private fun generateEntities(definition: Definition, outputRootDir: Path, factory: DefaultMustacheFactory) {
        val mustache = factory.compile("Entity.mustache")
        definition.entities.forEach {
            val outputDir = Paths.get(
                outputRootDir.toString(),
                it.namespace.replace('.', '/')
            )
            outputDir.toFile().mkdirs()
            val outputFile = Paths.get(
                outputDir.toString(),
                it.javaName() + JAVA_EXTENSION,
            )
            PrintWriter(BufferedWriter(FileWriter(outputFile.toFile()))).use { w ->
                mustache.execute(w, it).flush()
            }
        }
    }
}
