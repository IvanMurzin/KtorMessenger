package ivanmurzin.ru.entities

import ivanmurzin.ru.utils.ForbiddenException
import ivanmurzin.ru.utils.MyRegexp
import kotlinx.serialization.Serializable

@Serializable
data class User(val email: String, val password: String)

fun User.checkValid() {
    if (!Regex(MyRegexp.emailRegex).matches(email))
        throw ForbiddenException("Email введен некорректно")
    if (password.length < 6 || password.length > 16)
        throw ForbiddenException("Пароль должен быть от 6 до 16 символов")
}


