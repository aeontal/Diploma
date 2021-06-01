# Дипломный проект направления «Тестировщик» (QA-14 Нетология)

Задача: автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

**Документация**
  * [Описание приложения](#описание-приложения)
  * [Задача](#задача)
  * [1.Планирование](#i-планирование)

## Описание приложения

### Бизнес-часть: 

Приложение представляет из себя веб-сервис.

![](doc/img/diploma-service.png)

Приложение предлагает купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте
1. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (Payment Gate)
* кредитному сервису (Credit Gate)

### Техническая часть

Заявлена поддержка двух СУБД:
* MySQL
* PostgreSQL

## Задача

Ключевая задача — автоматизировать сценарии (как позитивные, так и негативные) покупки тура.

Задача разложена на 4 этапа:
1. Планирование автоматизации тестирования
1. Непосредственно сама автоматизация
1. Подготовка отчётных документов по итогам автоматизированного тестирования
1. Подготовка отчётных документов по итогам автоматизации

## 1. Планирование

Тест-план представлен в [`файле`](doc/Plan.md).


