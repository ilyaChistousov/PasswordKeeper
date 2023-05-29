## PasswpordKeeper
Приложение позволяет сохранять множество паролей в одном месте.
Приложение состоит из 2-ух экранов:
 1) Список всех сохраненных паролей
 ![PasswordList](https://github.com/ilyaChistousov/PasswordKeeper/blob/master/assets/password_list.png)
 Отображение списка происходит с помощью recycler view. Имеется возможность поиска по названию пароля, а также при клике на иконку сохранить пароль в буфер
 2) Детальная информация о пароле/создание нового пароля
 ![PasswordDetail](https://github.com/ilyaChistousov/PasswordKeeper/blob/master/assets/password_list.png)
 Содержит 2 обязательных поля для заполнения: Название, Пароль.
 Имеется возможность сгенерировать случайный пароль 
 
#### Используемые технологии

- Clean Architecture
- MVVM
- Navigation Component
- Hilt
- Room
- Coroutines
- Flow
