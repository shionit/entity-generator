package logic.generator

import com.github.mustachejava.DefaultMustacheFactory
import model.Definition
import java.io.PrintWriter

/**
 * Java Entity file generator.
 */
class JavaEntityGenerator {

    fun execute(definition: Definition) {
        val factory = DefaultMustacheFactory()
        // generate Enums
        generateEnums(definition, factory)

        // generate Entities
        generateEntities(definition, factory)
    }

    private fun generateEnums(definition: Definition, factory: DefaultMustacheFactory) {
        val mustache = factory.compile("Enum.mustache")
        for (enum in definition.enumMap.values) {
            mustache.execute(PrintWriter(System.out), enum).flush()
        }
    }

    private fun generateEntities(definition: Definition, factory: DefaultMustacheFactory) {
        val mustache = factory.compile("Entity.mustache")
        for (entity in definition.entities) {
            mustache.execute(PrintWriter(System.out), entity).flush()
        }
    }
}
