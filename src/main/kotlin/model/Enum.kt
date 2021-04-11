package model

/**
 * Enum definition data.
 */
data class Enum(
    val name: String,
    val description: String,
    val namespace: String,
    val entries: List<EnumEntry>,
)
