A service for social network.
Main functions:
- registration and authorization user
- find user, add friend, delete friend, get list of friends
  java 11+; Spring; H2; Docker.
Authorization implementation borrowed from https://habr.com/ru/post/545610/
Registration without authorization is not configured yet!
For Test: login: test@google.com, password: 3761P-560a
  How use:
***
## Для запуска со своего компьютера
### host http://localhost:8080/
```
git clone https://github.com/NestyChe/S7
./gradlew bootRun
```
## Docker
### Image creation and start
```
docker build --build-arg JAR_FILE=build/libs/\*.jar -t s7-0.0.1-snapshot .
docker run -p 8080:8080 s7-0.0.1-snapshot
```
## Запрос на удаленный сервер
```
http://84.252.130.81:8080/
```
## Пути
```
@POST /rest/registration/user
@POST /auth/login
@GET /rest/find-user
@POST /rest/add-user-tofriends
@GET /rest/get-all-friends
@DELETE /rest/delete-friend
```