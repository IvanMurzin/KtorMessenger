package ivanmurzin.ru.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.routing.*
import ivanmurzin.ru.Config

fun Application.configureRouting() {
    install(Authentication) {
        jwt("auth-jwt") {
            this.realm
            verifier( // init my own verifier
                JWT
                    .require(Algorithm.HMAC256(Config.JWT_SECRET))
                    .withIssuer(Config.JWT_ISSUER)
                    .build()
            )
            validate { credential -> // create validation function
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    routing {
        authenticate("auth-jwt") { // only auth users can see main page
            get("/") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal!!.payload.getClaim("email").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $email! Token is expired at $expiresAt ms.")
            }
        }
    }
}
