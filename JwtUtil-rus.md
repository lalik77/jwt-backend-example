# Создаем класс JwtUtil
![](img/jwt-example.png)
Payload таже называется Claims
![](img/jwt-util/util-jwt-util-4.png)
SECRET_KEY будет содержать секретное слово спомощью которым шифруется payload
TOKEN_VALIDITY - 60 sec * 60 min * 5 hour * 1000 ms - Время жизни токена
<br> Генерим токена
![](img/jwt-util/generate-token-1.png)
<br> Достаем expirationdate из токена
![](img/jwt-util/get-expiration-date-from-token.png)
<br> Токен истек или нет
![](img/jwt-util/is-token-expired-1.png)
<br> Проверка токена на валидацию
![](img/jwt-util/is-valid-token.png)
<br> Пишем метод getUserNameFromToken()
![](img/jwt-util/get-username-from-token-1.png)
<br> Реализум метод getClaimsFromToken(), для этого нам понадобится еще один 
вспомогательный метод getAllClaimsFromToken()
![](img/jwt-util/get-all-claims-from-token.png)
Вызываем getAllClaimsFromToken() внутри getClaimsFromToken()
![](img/jwt-util/get-claims-from-token.png)

Возвращаемся к _private Date getExpirationDateFromToken(final String token)_ 
![](img/jwt-util/get-expiration-date-from-token.png)
![](img/jwt-util/get-expiration-date-from-token-2.png)

Возвращаемся к  _public String getUsernameFromToken(final String token)_
![](img/jwt-util/get-username-from-token-2.png)
Протестируем как работает метод, сделаем вызов в main методе.
![](img/jwt-util/main-test-get-username-from-token-1.png)
Получаем ошибку
![](img/jwt-util/main-test-get-username-from-token-log-trace-1.png)
Добавляем зависимость 
![](img/jwt-util/pom-xml.png)
Получаем другую ошибку, но уже связанную с неправильным токеном
![](img/jwt-util/main-test-get-username-from-token-log-trace-2.png)
Генерируем на сайте jwt token, но все равно получаем ошибку
![](img/jwt-util/main-test-get-username-from-token-log-trace-3.png)
Дальше пишем JwtUtil класс
ObjectMapper - обьект данного класса позволяет замапить строчку в Json или
наборот из Json прочитать значение по ключу
![](img/jwt-util/get-username-from-token-3.png)
Делаем небольшой рефакторинг по методу generateToken()
![](img/jwt-util/generate-token-2.png)
Запускаем еще один тест, с помощью главного метода spring boot генерируем новый токен
![](img/jwt-util/main-generate-token-1.png)
![](img/jwt-util/main-generate-token-2.png)
eyJhbGciOiJIUzUxMiJ9.
eyJzdWIiOiJ7XG4gIFwidXNlck5hbWVcIjogXCJBbGV4XCIsXG4gIFwidXNlclBhc3N3b3JkXCI6IFwicGFzc3dvcmRcIlxufSIsImlhdCI6MTcwNjM3NTg1NywiZXhwIjoxNzA2MzkzODU3fQ.
YmtVcycOywxafymiL2raLcVxFn_mt6U1YgEyKm1sa4gQqeQDpd3WvcKj5tHvJBf-qbmyKek2GCJujxHVqp4dwA

Вставляем сгенерированный токен в строку и запускаем заново приложение чтобы протестить метод
_public String getUsernameFromToken(final String token)_
![](img/jwt-util/main-test-get-username-from-token-log-trace-4.png)
![](img/jwt-util/main-test-get-username-from-token-log-trace-5.png)


Какую информацию будем класть в payload чтобы сгенерировать jwt token ? 
Будем класть Информацию о пользователе , тогда надо рефакторить метод 
_public String generateToken(final String payload)_ 
![](img/jwt-util/generate-token-3.png)
![](img/jwt-util/generate-token-4.png)