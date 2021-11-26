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
    install(Authentication) { // install required module

        // get values form application.conf
        val secret = environment.config.property("jwt.secret").getString()
        val issuer = environment.config.property("jwt.issuer").getString()
        val audience = environment.config.property("jwt.audience").getString()
        val myRealm = environment.config.property("jwt.realm").getString()

        fun createToken(user: User) = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000L)) // jwt valid for 1 hour
            .sign(Algorithm.HMAC256(secret))


        install(Authentication) {
            jwt("auth-jwt") {
                realm = myRealm
                verifier( // init my own verifier
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
                    user.checkValid() // if user is invalid an exception occurs
                    val token = createToken(user)
                    call.respond(hashMapOf("token" to token)) // respond with token
                }
            }
        }
    }
}
