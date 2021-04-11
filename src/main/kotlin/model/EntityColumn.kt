package model

import misc.toLowerCamelCase
import model.enums.ColumnType

/**
 * Column definition data of Entity.
 */
data class EntityColumn(
    val name: String,
    val description: String,
    val type: ColumnType,
    val notNull: Boolean,
) {
    var length: Int? = null
    var pk: Int = 0 // if zero then not PK
    var enumType: String? = null

    fun javaName(): String {
        return name.toLowerCamelCase()
    }

    fun javaType(): String {
        return when (type) {
            ColumnType.ENUM -> enumType.toString()
            ColumnType.INT -> "BigDecimal"
            ColumnType.VARCHAR -> "String"
            ColumnType.BOOLEAN -> "Boolean"
            ColumnType.DATE -> "Date"
            ColumnType.DATETIME -> "DateTime"
        }
    }
}
