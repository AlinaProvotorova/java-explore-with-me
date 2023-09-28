# java-explore-with-me
Приложение-афиша, позволяющее пользователям делиться информацией о интересных событиях и находить компанию для участия в них.

## Основной сервис:
- Создание и просмотр событий
- Присоединение к событиям
- Комментирование и общение с другими участниками

[*Спецификация REST API основного сервиса.*](ewm-main-service-spec.json)


## Сервис статистики:
- Хранение количества просмотров
- Возможность анализировать работу приложения через выборки данных

[*Спецификация REST API сервиса статистики.*](ewm-stats-service-spec.json)

## Фича коментарии
- Возможность оставлять комментарии к событиям и модерировать их.

`[GET] /admin/comments?from={from}&size={size}` – получить список всех комментариев с пагинацией

`[DELETE] /admin/comments/{commentId}` – удалить комментарий с ID commentId

`[POST] /users/{userId}/comments?eventId={eventId}` – создать новый комментарий к событию c ID eventId пользователем c ID userId

`[PATCH] /users/{userId}/comments/{commentId}` – обновить свой комментарий c ID commentId пользователем c ID userId

`[DELETE] /users/{userId}/comments/{commentId} `- удалить свой комментарий c ID commentId пользователем c ID userId

`[GET] /users/{userId}/comments?eventId={eventId}&from={from}&size={size}` - получить список всех комментариев пользователя c ID userId к событию c ID eventId с пагинацией

`[GET] /users/{userId}/comments?from={from}&size={size}` - получить список всех комментариев пользователя c ID userId с пагинацией

`[GET] /comments?eventId={eventId}&from={from}&size={size}` – получить список всех комментариев к событию c ID eventId с пагинацией

`[GET] /comments/{commentId}` – получить комментарий c ID commentId

## Запуск приложения
Потребуется Java 11, Docker, Git, Apache Maven

1. Склонировать
```bash
git clone https://github.com/AlinaProvotorova/java-explore-with-me.git
```
2. Собрать проект
```bash
mvn clean package
```
3. Запустить через Docker Compose
```bash
docker compose up
```