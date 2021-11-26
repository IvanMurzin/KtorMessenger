package ivanmurzin.ru

object Config {
    var MONGO_URL = "mongodb://localhost:27018"
    var DATABASE_NAME = "KtorMessenger"

    var JWT_LIFETIME_H = "24"
    var JWT_ISSUER = "ktormessenger"
    var JWT_SECRET = "my deepest secret"
}