package ivanmurzin.ru

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import ivanmurzin.ru.routes.*
import ivanmurzin.ru.utils.MyRegexp

class ApplicationTest {
    @Test
    fun testRoot() {
        println(Regex(MyRegexp.emailRegex).matches("lala"))
    }
}