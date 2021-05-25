package logic.reader

import model.Definition
import model.Entity
import model.EntityColumn
import model.Enum
import model.EnumEntry
import model.enums.ColumnType
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import kotlin.system.exitProcess

/**
 * Entity definition reader class.
 */
class EntityDefinitionReader {

    companion object {
        const val META_SHEET_NAME = "META"
        const val FIRST_COLUMN_DEFINITION_ROWNUM = 5
    }

    /**
     * Read Entity definitions from workbook.
     */
    fun readBook(workbook: Workbook): Definition {
        val metaColumns = readMetaColumns(workbook)

        val enums = readEnums(workbook)
        val enumMap = enums.associateBy { it.name }

        val entities = readEntities(workbook, metaColumns)
        entities.forEach { entity ->
            entity.columns.filter {
                !it.enumType.isNullOrBlank()
            }.forEach {
                it.enumClass = enumMap.get(it.enumType)
            }
        }

        return Definition(enumMap, entities)
    }

    private fun readMetaColumns(workbook: Workbook): List<EntityColumn> {
        val sheet = workbook.getSheet(META_SHEET_NAME)
        if (sheet == null) {
            println("META sheet not found.")
            exitProcess(-1)
        }
        return readColumns(sheet)
    }

    private fun readColumns(sheet: Sheet): List<EntityColumn> {
        val columns = emptyList<EntityColumn>().toMutableList()
        for (i in FIRST_COLUMN_DEFINITION_ROWNUM..sheet.lastRowNum) {
            val row = sheet[i]
            val name = row[0].stringCellValue
            if (name.isEmpty()) {
                break
            }
            val description = row[1].stringCellValue
            val type = ColumnType.valueOf(row[2].stringCellValue)
            val notNullText = row[4].stringCellValue
            val notNull = "Y" == notNullText
            val column = EntityColumn(name, description, type, notNull)
            // optional properties
            val lengthNum = row[3].numericCellValue
            column.length = when (type) {
                ColumnType.INT -> lengthNum.toInt()
                ColumnType.VARCHAR -> lengthNum.toInt()
                else -> null
            }
            val pkNum = row[5].numericCellValue
            column.pk = pkNum.toInt()
            if (type == ColumnType.ENUM) {
                column.enumType = row[6].stringCellValue
            }
            columns.add(column)
        }
        return columns
    }

    private fun readEnums(workbook: Workbook): List<Enum> {
        val enums = emptyList<Enum>().toMutableList()
        for (sheet in workbook) {
            if (sheet == null) {
                continue
            }
            val sheetType = sheet[0, 0].stringCellValue
            if (sheetType != "EnumName") {
                continue
            }
            val name = sheet[1, 0].stringCellValue
            val description = sheet[1, 1].stringCellValue
            val namespace = sheet[1, 2].stringCellValue
            val entries = readEntries(sheet)
            enums.add(Enum(name, description, namespace, entries))
        }
        return enums
    }

    private fun readEntries(sheet: Sheet): List<EnumEntry> {
        val entries = emptyList<EnumEntry>().toMutableList()
        for (i in FIRST_COLUMN_DEFINITION_ROWNUM..sheet.lastRowNum) {
            val row = sheet[i]
            val name = row[0].stringCellValue
            if (name.isEmpty()) {
                break
            }
            val description = row[1].stringCellValue
            val value = row[2].numericCellValue.toInt()
            entries.add(EnumEntry(name, description, value))
        }
        entries.last().isLast = true
        return entries
    }

    private fun readEntities(workbook: Workbook, metaColumns: List<EntityColumn>): List<Entity> {
        val entities = emptyList<Entity>().toMutableList()
        for (sheet in workbook) {
            if (sheet == null) {
                continue
            }
            val sheetType = sheet[0, 0].stringCellValue
            if (sheetType != "EntityName" || sheet.sheetName == META_SHEET_NAME) {
                continue
            }
            val name = sheet[1, 0].stringCellValue
            val description = sheet[1, 1].stringCellValue
            val namespace = sheet[1, 2].stringCellValue
            val columns = readColumns(sheet)
            entities.add(Entity(name, description, namespace, columns, metaColumns))
        }
        return entities
    }
}

operator fun Sheet.get(n: Int): Row {
    return this.getRow(n) ?: this.createRow(n)
}

operator fun Row.get(n: Int): Cell {
    return this.getCell(n) ?: this.createCell(n, CellType.BLANK)
}

operator fun Sheet.get(x: Int, y: Int): Cell {
    val row = this[y]
    return row[x]
}
