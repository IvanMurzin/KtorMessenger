package ivanmurzin.ru.modules

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import ivanmurzin.ru.utils.AlreadyExistsException
import ivanmurzin.ru.utils.ForbiddenException
import ivanmurzin.ru.utils.UnauthorizedException

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<UnauthorizedException> { cause ->
            call.respond(HttpStatusCode.Unauthorized, cause.message ?: "")
        }
        exception<ForbiddenException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
        }
        exception<AlreadyExistsException> { cause ->
            call.respond(HttpStatusCode.Conflict, cause.message ?: "")
        }
        exception<Exception> { cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "")
        }
    }
}