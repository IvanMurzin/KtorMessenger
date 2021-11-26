package ivanmurzin.ru.database

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import ivanmurzin.ru.Config
import ivanmurzin.ru.entities.MongoDocument
import ivanmurzin.ru.utils.ForbiddenException
import org.litote.kmongo.*
import kotlin.reflect.KProperty1

object Database {

    private val client = KMongo.createClient(Config.MONGO_URL)
    val database: MongoDatabase = client.getDatabase(Config.DATABASE_NAME)

    inline fun <reified DocumentType : MongoDocument> getCol(): MongoCollection<DocumentType> {
        return database.getCollection<DocumentType>(DocumentType::class.simpleName!!.lowercase())
    }

    inline fun <reified DocumentType : MongoDocument> save(document: DocumentType): DocumentType {
        return document.apply { getCol<DocumentType>().insertOne(document) }
    }

    inline fun <reified DocumentType : MongoDocument> findById(id: String): DocumentType? {
        return getCol<DocumentType>().findOneById(id)

    }

    inline fun <reified DocumentType : MongoDocument, FieldType> find(
        field: KProperty1<DocumentType, FieldType>,
        value: FieldType
    ): DocumentType? {
        return getCol<DocumentType>().findOne(field eq value)
    }
}