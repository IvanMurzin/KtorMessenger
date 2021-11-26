package ivanmurzin.ru.modules

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ivanmurzin.ru.entities.User
import ivanmurzin.ru.entities.checkValid
import java.util.*


fun Application.configureAuthentication() {
    install(Authentication) {

        val secret = environment.config.property("jwt.secret").getString()
        val issuer = environment.config.property("jwt.issuer").getString()
        val audience = environment.config.property("jwt.audience").getString()
        val myRealm = environment.config.property("jwt.realm").getString()

        fun createToken(user: User) = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))


        install(Authentication) {
            jwt("auth-jwt") {
                realm = myRealm
                verifier(
                    JWT
                        .require(Algorithm.HMAC256(secret))
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .build()
                )
            }
            routing {
                post("/login") {
                    val user = call.receive<User>()
                    user.checkValid()
                    val token = createToken(user)
                    call.respond(hashMapOf("token" to token))
                }
            }
        }
    }
}
