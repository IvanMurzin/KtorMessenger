# [Ktor Messenger]()

---
This application will show you how to create a simple messenger using Ktor and a little magic.<br>Enjoy C:

---

Api Docs
---
All authorization occurs through the jwt token using Bearer header

---
* ### post /login<br>

request:

```json
{
  "email": "myemail@domain.name",
  "userName": "User Name",
  "passwordHash": "some hash"
}
```

response:

```json
{
  "token": "jwt token"
}
```

* ### post /singin<br>

request:

```json
  {
  "email": "myemail@domain.name",
  "passwordHash": "some hash"
}
```

response:

```json
{
  "token": "jwt token"
}
```

* ### post /createRoom🔒<br>

request:

```json
{
  "name": "room name"
}
```

response:

```json
{
  "name": "room name",
  "creator": "creator name"
}
```

* ### get /getRooms🔒<br>

response:

```json
[
  {
    "name": "room name",
    "creator": "creator name"
  }
]
```

* ### post /sendMessage/{roomName}🔒<br>

request:

```json
{
  "what": "message text"
}
```

response:

```json
{
  "who": "author name",
  "where": "room name",
  "what": "message text"
}
```

* ### get /getMessages/{roomName}🔒<br>

response:

```json
[
  {
    "who": "author name",
    "where": "room name",
    "what": "message text"
  }
]
```