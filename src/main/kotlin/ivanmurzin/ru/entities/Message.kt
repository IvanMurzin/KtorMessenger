package ivanmurzin.ru.entities

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    var who: String? = null, // who send message
    var where: String? = null, // which room the message was sent to
    var what: String // message text
) : MongoDocument()