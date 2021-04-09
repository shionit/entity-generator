import logic.EntityGeneratorService
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.nio.file.Paths

const val INPUT_FILE: String = "EntityDefinition.xlsx"

/**
 * Application entrypoint.
 */
fun main() {
    FileInputStream(Paths.get(INPUT_FILE).toFile()).use {
        val workbook = WorkbookFactory.create(it)
        val service = EntityGeneratorService()
        service.process(workbook)
    }
}
