package ivanmurzin.ru.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ivanmurzin.ru.database.Database
import ivanmurzin.ru.entities.Message
import ivanmurzin.ru.entities.Room
import ivanmurzin.ru.utils.AlreadyExistsException
import ivanmurzin.ru.utils.ForbiddenException

fun Application.configureChats() {
    routing {
        authenticate("auth-jwt") { // only auth users can send messages
            post("/createRoom") {
                val principal = call.principal<JWTPrincipal>() // check is token valid
                val room = call.receive<Room>()
                if (Database.findOne(Room::name, room.name) != null) // if room already exists
                    throw AlreadyExistsException("Такая комната уже существует")
                room.creator = principal!!.payload.getClaim("userName").asString()
                call.respond(Database.save(room))
            }

            get("/getRooms") {
                call.principal<JWTPrincipal>() // check is token valid
                call.respond(Database.getCol<Room>().find().toList())
            }

            post("/sendMessage/{roomName}") {
                val principal = call.principal<JWTPrincipal>() // check is token valid
                val roomName = call.parameters["roomName"] // get param from uri
                val message = call.receive<Message>()
                Database.findOne(Room::name, roomName) // if there is no room with roomName
                    ?: throw ForbiddenException("Такой комнаты не существует")
                message.who = principal!!.payload.getClaim("userName").asString()
                message.where = roomName
                call.respond(Database.save(message))
            }

            get("/getMessages/{roomName}") {
                call.principal<JWTPrincipal>()  // check is token valid
                val roomName = call.parameters["roomName"]
                call.respond(Database.findAll(Message::where, roomName).toList())
            }
        }
    }
}