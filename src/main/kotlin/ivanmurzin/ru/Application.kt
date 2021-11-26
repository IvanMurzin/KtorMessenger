package ivanmurzin.ru

import io.ktor.application.*
import ivanmurzin.ru.modules.configureAuthentication
import ivanmurzin.ru.modules.configureSerialization
import ivanmurzin.ru.modules.configureStatusPages
import ivanmurzin.ru.routes.configureRouting

// start server with Netty engine and configuration form application.conf
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// init app modules
// this method is called when the application starts
fun Application.module() {
    configureRouting()
    configureAuthentication()
    configureSerialization()
    configureStatusPages()
}


