package model

import misc.toUpperCamelCase

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
        return name.toUpperCamelCase()
    }

    fun javaImportText(): String {
        return "import TODO;"    // TODO
    }
}
