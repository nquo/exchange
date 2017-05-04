# Exchange app
Приложение Exchange построено на использовании паттерна MVP и использовании Dependency Injection (Google Dagger2).

Приложение начинает свою работу с создания кастомного Application класса, который хранит ссылку на синглтон ValutesRepositoryComponent.


Всего в приложении используется два Dagger компонента:
- MainComponent - фрагмент основного активити приложения.
- ValutesRepositoryComponent - управление данными. Включает в себя работу с локальной базой и работу с удаленным сервером.

## Логика работы приложения:
При запуске производится попытка загрузить данные с удаленного сервера и сохранить их в базу с перезаписью. Если данные с удаленного сервера не получены, то проверяется кэш данных в локальной базе.

Элементы UI с которыми взаимодействует пользователь:
- Два выпадающих списка типа spinner, куда загружаются курсы валют.
- Три FAB кнопки (конвертация, очистка поля ввода и смена типов валют)
- Поле ввода суммы для конвертации
