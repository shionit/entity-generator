package model

import misc.toUpperCamelCase

/**
 * Enum definition data.
 */
data class Enum(
    val name: String,
    val description: String,
    val namespace: String,
    val entries: List<EnumEntry>,
) {
    fun javaName(): String {
        return name.toUpperCamelCase()
    }
}
