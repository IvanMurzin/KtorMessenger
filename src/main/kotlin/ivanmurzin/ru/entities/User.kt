package ivanmurzin.ru.entities

import ivanmurzin.ru.utils.ForbiddenException
import ivanmurzin.ru.utils.MyRegexp
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val userName: String? = null,
    val passwordHash: String // save pass hash for more security
) : MongoDocument()

fun User.checkValid() {
    if (!Regex(MyRegexp.emailRegex).matches(email)) // check email with my regexp
        throw ForbiddenException("Email введен некорректно")
}


