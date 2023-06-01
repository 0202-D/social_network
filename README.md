# social_network
 RESTful API для социальной медиа платформы,
позволяющей пользователям регистрироваться, входить в систему, создавать
посты, переписываться, подписываться на других пользователей и получать
свою ленту активности.
<br>
<br>
# Запуск приложения :

1. Скачать или склонировать репозиторий приложения в программу IntelliJ IDEA

2. Упаковать проект в Maven

3. В терминале в корне приложения выполнить команду docker build ./

4. Упаковать образ в контейнер командой docker-compose build

5. Запустить контейнер командой docker-compose up
<br>
<br>
Ознакомиться и протестировать эндпоинты можно в Swagger . 
Доступно по  :  
<a href="http://localhost:8090/swagger-ui/index.html#">http://localhost:8090/swagger-ui/index.html#<a>
<br>
<br>
Приложение запускается на порте 8090 

# Технологии и инструменты :
* Язык программирования: Java
* Фреймворк: Spring Boot
* База данных: PostgreSQL 
* Аутентификация и авторизация: Spring Security
* Документация API: Swagger 

