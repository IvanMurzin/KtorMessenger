package ivanmurzin.ru.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ivanmurzin.ru.Config
import ivanmurzin.ru.entities.User
import java.util.*

object AuthUtil {
    private val algorithm = Algorithm.HMAC256(Config.JWT_SECRET)

    fun createToken(user: User): String = JWT.create() // create jwt token that stores email passwordHash and userName
        .withIssuer(Config.JWT_ISSUER)
        .withSubject(user.id)
        .withClaim("email", user.email)
        .withClaim("userName", user.userName)
        .withExpiresAt(Date(System.currentTimeMillis() + Config.JWT_LIFETIME_H.toInt() * 3600000))
        .sign(algorithm)
}