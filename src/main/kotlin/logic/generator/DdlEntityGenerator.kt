package logic.generator

import com.github.mustachejava.DefaultMustacheFactory
import model.Definition
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Path
import java.nio.file.Paths

/**
 * DDL Entity file generator.
 */
class DdlEntityGenerator {

    companion object {
        const val DDL_EXTENSION = ".sql"
    }

    fun execute(definition: Definition) {
        val factory = DefaultMustacheFactory()
        val currentDir = Paths.get("").toAbsolutePath()
        val outputRootDir = Paths.get(currentDir.toString(), "src/db/ddl/")
        outputRootDir.toFile().deleteRecursively()

        // generate Entities
        generateEntities(definition, outputRootDir, factory)
    }

    private fun generateEntities(definition: Definition, outputRootDir: Path, factory: DefaultMustacheFactory) {
        val mustache = factory.compile("Ddl.mustache")
        outputRootDir.toFile().mkdirs()
        definition.entities.forEach {
            val outputFile = Paths.get(
                outputRootDir.toString(),
                it.name + DDL_EXTENSION,
            )
            PrintWriter(BufferedWriter(FileWriter(outputFile.toFile()))).use { w ->
                mustache.execute(w, it).flush()
            }
        }
    }
}
