package ivanmurzin.ru.modules

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*

fun Application.configureCors() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        header(HttpHeaders.Accept)
        header(HttpHeaders.AcceptLanguage)
        header(HttpHeaders.AcceptEncoding)

        header(HttpHeaders.Authorization)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.ContentLength)
        header(HttpHeaders.Host)
        header(HttpHeaders.Cookie)

        header("DNT")
        //allowCredentials = true
        anyHost()
    }
}