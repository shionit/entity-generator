package model

import com.google.common.base.CaseFormat

/**
 * Entity definition data.
 */
data class Entity(
    val name: String,
    val description: String,
    val namespace: String,
    val columns: List<EntityColumn>,
) {
    fun javaName(): String {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)
    }

    fun javaImportText(): String {
        return "import TODO;"    // TODO
    }
}
