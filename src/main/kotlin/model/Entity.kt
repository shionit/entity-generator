package model

import com.google.common.base.CaseFormat
import model.enums.ColumnType

/**
 * Entity definition data.
 */
data class Entity(
    val name: String,
    val description: String,
    val namespace: String,
    val columns: List<EntityColumn>,
    val metaColumns: List<EntityColumn>,
) : GenerateTarget {
    fun javaName(): String {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)
    }

    fun uniqueEnums(): List<Enum> {
        return columns.filter {
            it.enumClass != null
        }.distinctBy {
            it.enumClass!!.namespace
        }.sortedBy {
            it.enumClass!!.namespace
        }.map { it.enumClass!! }
    }
}

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
    var enumClass: Enum? = null

    fun javaName(): String {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name)
    }

    fun javaType(): String {
        return when (type) {
            ColumnType.ENUM -> enumType.toString()
            ColumnType.INT -> "Integer"
            ColumnType.VARCHAR -> "String"
            ColumnType.BOOLEAN -> "Boolean"
            ColumnType.DATE -> "java.util.Date"
            ColumnType.DATETIME -> "java.sql.Timestamp"
        }
    }
}
