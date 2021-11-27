package ivanmurzin.ru

object Config {
//    var MONGO_URL = "mongodb://localhost:27018"
    var MONGO_URL = "mongodb+srv://Alduin:Volan@cluster0.dabxs.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"
//    var DATABASE_NAME = "KtorMessenger"
    var DATABASE_NAME = "Cluster0"

    var JWT_LIFETIME_H = "24"
    var JWT_ISSUER = "ktormessenger"
    var JWT_SECRET = "my deepest secret"
}