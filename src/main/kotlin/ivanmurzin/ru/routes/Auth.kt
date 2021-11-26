package ivanmurzin.ru.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ivanmurzin.ru.Config
import ivanmurzin.ru.database.Database
import ivanmurzin.ru.entities.User
import ivanmurzin.ru.entities.checkValid
import ivanmurzin.ru.utils.AuthUtil.createToken
import ivanmurzin.ru.utils.ForbiddenException


fun Application.configureAuthentication() {
    // init jwt authentication logic
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

        post("/login") { // route to /login
            val user = call.receive<User>()
            user.checkValid() // if user is invalid an exception occurs
            if (Database.findOne(User::email, user.email) != null) // if user already exists
                throw ForbiddenException("Такой пользователь уже существует")
            Database.save(user) // else save them
            val token = createToken(user)
            call.respond(hashMapOf("token" to token)) // respond with token
        }

        post("/signin") {  // route to /signin
            val user = call.receive<User>()
            user.checkValid() // if user is invalid an exception occurs
            val trueUser = Database.findOne(User::email, user.email) // if there is no one user with this email
                ?: throw ForbiddenException("Такого пользователя не существует")
            if (trueUser.passwordHash != user.passwordHash) // if wrong password
                throw ForbiddenException("Неверный пароль")
            val token = createToken(trueUser) // create a token with trueUser because the user info is stored in the jwt
            call.respond(hashMapOf("token" to token))
        }
    }
}
