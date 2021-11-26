package ivanmurzin.ru

import io.ktor.application.*
import ivanmurzin.ru.modules.configureAuthentication
import ivanmurzin.ru.modules.configureSerialization
import ivanmurzin.ru.modules.configureStatusPages
import ivanmurzin.ru.routes.configureRouting


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureAuthentication()
    configureSerialization()
    configureStatusPages()
}


