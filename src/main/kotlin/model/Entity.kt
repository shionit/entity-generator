package model

/**
 * Entity definition data.
 */
data class Entity(
    val name: String,
    val description: String,
    val namespace: String,
    val columns: List<EntityColumn>,
)
