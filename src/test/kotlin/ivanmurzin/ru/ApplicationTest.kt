package ivanmurzin.ru

import ivanmurzin.ru.entities.MongoDocument
import ivanmurzin.ru.entities.User
import ivanmurzin.ru.utils.MyRegexp
import kotlin.reflect.KProperty1
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() {
        println(Regex(MyRegexp.emailRegex).matches("lala"))
    }


}