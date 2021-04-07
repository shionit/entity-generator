package model

import model.enums.ColumnType

data class EntityColumn(
    val name: String,
    val description: String,
    val type: ColumnType,
    val length: Int,
    val notNull: Boolean,
    val pk: Int,
    val enumType: String
    )
