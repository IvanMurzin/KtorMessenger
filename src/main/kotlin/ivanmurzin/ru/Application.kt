package ivanmurzin.ru

import io.ktor.application.*
import ivanmurzin.ru.modules.configureCors
import ivanmurzin.ru.modules.configureSerialization
import ivanmurzin.ru.modules.configureStatusPages
import ivanmurzin.ru.routes.configureAuthentication
import ivanmurzin.ru.routes.configureChats

// start server with Netty engine and configuration form application.conf
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// init app modules
// this method is called when the application starts
fun Application.module() {
    configureCors()
    configureAuthentication()
    configureSerialization()
    configureStatusPages()
    configureChats()
}


