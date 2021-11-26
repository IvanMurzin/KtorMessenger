package ivanmurzin.ru.entities

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val name: String,
    var creator: String? = null
) : MongoDocument()

