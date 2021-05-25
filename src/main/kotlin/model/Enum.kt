package model

/**
 * Enum definition data.
 */
data class Enum(
    val name: String,
    val description: String,
    val namespace: String,
    val entries: List<EnumEntry>,
) : GenerateTarget

/**
 * Enum entry definition data.
 */
data class EnumEntry(
    val name: String,
    val description: String,
    val value: Int,
) {
    var isLast: Boolean = false
}
