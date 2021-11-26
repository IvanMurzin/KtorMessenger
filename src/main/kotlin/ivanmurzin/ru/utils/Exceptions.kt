package ivanmurzin.ru.utils


class UnauthorizedException(message: String?, cause: Throwable? = null) : Exception(message, cause)
class ForbiddenException(message: String?, cause: Throwable? = null) : Exception(message, cause)
class AlreadyExistsException(message: String?, cause: Throwable? = null) : Exception(message, cause)
