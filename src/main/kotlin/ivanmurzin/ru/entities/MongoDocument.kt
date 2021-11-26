package ivanmurzin.ru.entities

import org.bson.codecs.pojo.annotations.BsonId

abstract class MongoDocument {
    @BsonId
    open var id: String? = null
}