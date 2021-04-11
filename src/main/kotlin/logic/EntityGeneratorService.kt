package logic

import logic.generator.JavaEntityGenerator
import logic.reader.EntityDefinitionReader
import org.apache.poi.ss.usermodel.Workbook

/**
 * Entity generator main service.
 */
class EntityGeneratorService {

    /**
     * Read Entity definition from workbook
     * and Generate source files.
     */
    fun process(workbook: Workbook) {
        // read Excel file and Create Java Objects
        val reader = EntityDefinitionReader()
        val definition = reader.readBook(workbook)

        // debug log
        println(definition.metadata)
        println(definition.enumMap)
        println(definition.entities)

        // create Java definition files
        val javaGenerator = JavaEntityGenerator()
        javaGenerator.execute(definition)

        // TODO: #6  create SQL DDL files
    }
}
