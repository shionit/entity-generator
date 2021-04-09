package model

/**
 * Enum definition data.
 */
data class Enum(
    val name: String,
    val namespace: String,
    val description: String,
    val entries: List<EnumEntry>,
)
