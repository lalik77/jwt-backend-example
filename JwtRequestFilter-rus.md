# Создаем JwtRequestFilter 
В пэкэйже configuration создаем новый класс фильтр JwtRequestFilter
![](img/jwt-request-filter/do-filter-internal-1.png)

Если Header авторизации не пустой и начинается с Bearer то приступаем к получению
токена
![](img/jwt-request-filter/do-filter-internal-2.png)

Если токен не валидный то  также передаем дальше filterChain

Установка пользователя в Spring Security Context (авторизация через токен)
![](img/jwt-request-filter/do-filter-internal-3.png)

Сейчас этап фильтрации прошел, все хорошо, мы авторизовали в контексте  пользователя 
и отправляем его дальше на WebSecurityConfigurer
