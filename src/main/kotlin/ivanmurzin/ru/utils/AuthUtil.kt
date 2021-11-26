package ivanmurzin.ru.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ivanmurzin.ru.Config
import ivanmurzin.ru.entities.User
import java.security.MessageDigest
import java.util.*

object AuthUtil {
    private val algorithm = Algorithm.HMAC256(Config.JWT_SECRET)

    fun createToken(user: User): String = JWT.create()
        .withIssuer(Config.JWT_ISSUER)
        .withSubject(user.id)
        .withClaim("email", user.email)
        .withClaim("pass_hash", user.passwordHash)
        .withExpiresAt(Date(System.currentTimeMillis() + +Config.JWT_LIFETIME_H.toInt() * 3600000))
        .sign(algorithm)

    fun String.toSha256(): String {
        val bytes = toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}