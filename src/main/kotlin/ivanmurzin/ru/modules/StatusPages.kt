package ivanmurzin.ru.modules

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import ivanmurzin.ru.utils.AlreadyExistsException
import ivanmurzin.ru.utils.ForbiddenException
import ivanmurzin.ru.utils.UnauthorizedException


// StatusPages are used to correctly handle exceptions
fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<UnauthorizedException> { cause -> // 401
            call.respond(HttpStatusCode.Unauthorized, cause.message ?: "")
        }
        exception<ForbiddenException> { cause -> // 400
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
        }
        exception<AlreadyExistsException> { cause -> // 409
            call.respond(HttpStatusCode.Conflict, cause.message ?: "")
        }
        exception<Exception> { cause -> // 500
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "")
        }
    }
}