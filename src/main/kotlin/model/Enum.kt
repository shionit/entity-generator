package model

data class Enum(
    val name: String,
    val namespace: String,
    val description: String,
    var entries: List<EnumEntry>
    )
