package model

import com.google.common.base.CaseFormat

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
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)
    }
}

/**
 * Enum entry definition data.
 */
data class EnumEntry(
    val name: String,
    val description: String,
    val value: Int,
)
