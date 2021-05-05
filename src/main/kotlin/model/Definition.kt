package model

/**
 * Entity definitions integration data.
 */
data class Definition(
    val enumMap: Map<String, Enum>,
    val entities: List<Entity>,
)
