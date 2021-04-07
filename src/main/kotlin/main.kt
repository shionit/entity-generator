import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.nio.file.Paths

const val INPUT_FILE: String = "EntityDefinition.xlsx"

fun main() {
    FileInputStream(Paths.get(INPUT_FILE).toFile()).use {
        val workbook = WorkbookFactory.create(it)
        for (sheet in workbook.sheetIterator()) {
            println(sheet.sheetName)
            var name = sheet.getRow(0).getCell(1).stringCellValue
            println("B1 $name")
        }
    }

}
