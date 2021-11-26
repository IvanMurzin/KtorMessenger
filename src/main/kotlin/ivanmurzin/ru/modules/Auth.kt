package ivanmurzin.ru.modules

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
    install(Authentication) { // install required module

        install(Authentication) {
            jwt("auth-jwt") {
                this.realm
                verifier( // init my own verifier
                    JWT
                        .require(Algorithm.HMAC256(Config.JWT_SECRET))
                        .withIssuer(Config.JWT_ISSUER)
                        .build()
                )
            }
            routing {
                post("/login") {
                    val user = call.receive<User>()
                    user.checkValid() // if user is invalid an exception occurs
                    if (Database.find(User::email, user.email) != null)
                        throw ForbiddenException("Такой пользователь уже существует")
                    Database.save(user)
                    val token = createToken(user)
                    call.respond(hashMapOf("token" to token)) // respond with token
                }
                post("/signin") {
                    val user = call.receive<User>()
                    user.checkValid() // if user is invalid an exception occurs
                    val trueUser = Database.find(User::email, user.email)
                        ?: throw ForbiddenException("Такого пользователя не существует")
                    if (trueUser.passwordHash != user.passwordHash)
                        throw ForbiddenException("Неверный пароль")
                    val token = createToken(user)
                    call.respond(hashMapOf("token" to token))
                }
            }
        }
    }
}
