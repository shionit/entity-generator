package model

/**
 * Entity definitions integration data.
 */
data class Definition(
    val metadata: Entity,
    val enumMap: Map<String, Enum>,
    val entities: List<Entity>,
)
