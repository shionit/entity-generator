package model

data class Entity(
    val name: String,
    val namespace: String,
    val description: String,
    var columns: List<EntityColumn>
    )
