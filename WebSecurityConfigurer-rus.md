# Создаем WebSecurityConfigurer
![](img/web-security-configurer/web-security-configurer.png)

Пишем метод securityFilterChain()
![](img/web-security-configurer/web-security-configurer-2.png)
В SpringBoot 2 мы  расширяли класс _WebSecurityConfigurerAdapter_  
переопределяли  метод _configure()_
![](img/web-security-configurer/web-security-configurer-3.png)

Добавляем классу еще один бин
![](img/web-security-configurer/bean-authentication-manager.png)

Запускаем приложение и получаем ощибку 
![](img/web-security-configurer/web-security-configurer-4.png)
Создаем еще один бин
![](img/web-security-configurer/bean-authentication-manager-2.png)



