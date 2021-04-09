package model

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
}
