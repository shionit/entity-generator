package logic

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
        val reader = EntityDefinitionReader()
        val definition = reader.readBook(workbook)

        // debug log
        println(definition.metadata)
        println(definition.enumMap)
        println(definition.entities)

        // TODO: #5  create Java definition files
        // TODO: #6  create SQL DDL files
    }
}
