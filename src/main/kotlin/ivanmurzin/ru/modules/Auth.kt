package ivanmurzin.ru.modules

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ivanmurzin.ru.database.Database
import ivanmurzin.ru.entities.User
import ivanmurzin.ru.entities.checkValid
import ivanmurzin.ru.utils.AuthUtil.createToken
import ivanmurzin.ru.utils.ForbiddenException


fun Application.configureAuthentication() {
    routing {

        post("/login") { // route to /login
            val user = call.receive<User>()
            user.checkValid() // if user is invalid an exception occurs
            if (Database.find(User::email, user.email) != null) // if user already exists
                throw ForbiddenException("Такой пользователь уже существует")
            Database.save(user) // else save them
            val token = createToken(user)
            call.respond(hashMapOf("token" to token)) // respond with token
        }

        post("/signin") {  // route to /signin
            val user = call.receive<User>()
            user.checkValid() // if user is invalid an exception occurs
            val trueUser = Database.find(User::email, user.email) // if there is no one user with this email
                ?: throw ForbiddenException("Такого пользователя не существует")
            if (trueUser.passwordHash != user.passwordHash) // if wrong password
                throw ForbiddenException("Неверный пароль")
            val token = createToken(user)
            call.respond(hashMapOf("token" to token))
        }
    }
}
