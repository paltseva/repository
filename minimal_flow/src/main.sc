theme: /

    state: newNode_0
        a: Начнём
        go!: /newNode_5
    @IntentGroup
        {
          "boundsTo" : "/newNode_0",
          "actions" : [ {
            "buttons" : [ {
              "name" : "меню",
              "transition" : "/newNode_1"
            } ],
            "type" : "buttons"
          } ],
          "global" : true
        }
    state: newNode_5
        state: 1
            e!: $menu

            go!: /newNode_1

        state: Fallback
            event: noMatch
            go!: /newNode_6
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_5",
                name: "newNode_5 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "меню"
                    , transition: "/newNode_1"
                    },
                  ]);
                }
            });

    state: newNode_6
        random:
            a: Я не понимаю...
            a: Не умею такое делать
            a: Что?
            a: Не понял..

    state: newNode_1
        random:
            a: Выбери пункт меню
            a: Выбери, что хочешь проверить
        buttons:
            "изображение локально" -> /newNode_2
            "изображение по ссылке" -> /newNode_3
            "текст" -> /newNode_4
            "ввод числа" -> /newNode_7
            "ввод текста" -> /newNode_9
            "номер телефона" -> /newNode_11
            "http" -> /newNode_13
            "дальше" -> /newNode_16

    state: newNode_16
        a: Выбери пункт меню
        buttons:
            "интенты" -> /newNode_30
            "готовые интенты" -> /newNode_19
            "faq" -> /newNode_32
            "аудио локально" -> /newNode_33
            "переход по таймауту" -> /newNode_34
            "условия" -> /newNode_37
            "дальше" -> /newNode_40
            "назад" -> /newNode_1

    state: newNode_40
        a: Выбери пункт меню
        buttons:
            "завершение сценария" -> /newNode_41
            "в начало" -> /newNode_1
            "назад" -> /newNode_16

    state: newNode_2
        image: https://smartapp-s3.sberdevices.ru/temporary-botadmin/707/708/f0zuNoe8LNpo5R63.jpg

    state: newNode_3
        image: https://images.app.goo.gl/zgNtUfaNhLBPiXrz6

    state: newNode_4
        a:  1 Когда-то я в годину зрелых лет
            В дремучий лес зашел и заблудился.
            Потерян был прямой и верный след…
             
             
            4 Нет слов таких, чтоб ими я решился
            Лес мрачный и угрюмый описать,
            Где стыл мой мозг и ужас тайный длился:
             
             
            7 Так даже смерть не может устрашать…
            Но в том лесу, зловещей тьмой одетом,
            Средь ужасов обрел я благодать.
             
             
            10 Попал я в чащу дикую; нигде там
            Я не нашел, объят каким-то сном,
            Знакомого пути по всем приметам.

    state: newNode_7
        InputNumber:
            actions = [{"buttons":[],"type":"buttons"}]
            prompt = Введите число
            varName = number
            failureMessage = ["Введите число от 1 до 100","ошибочка какая-то"]
            then = /newNode_8
            minValue = 1
            maxValue = 100

    state: newNode_9
        InputText:
            actions = [{"buttons":[],"type":"buttons"}]
            prompt = Введите текст
            varName = text
            then = /newNode_10

    state: newNode_11
        InputPhoneNumber:
            actions = [{"buttons":[],"type":"buttons"}]
            prompt = Введите номер телефона
            varName = phone
            failureMessage = ["Некорректный номер телефона"]
            then = /newNode_12

    state: newNode_13
        HttpRequest:
            url = https://api.adviceslip.com/advice/search/spiders
            method = GET
            body = 
            okState = /newNode_14
            errorState = /newNode_15
            timeout = 0
            headers = []
            vars = [{"name":"response","value":"$httpResponse"}]

    state: newNode_30
        a: Скажите котик в разных формах, потом собачка в разных формах
        go!: /newNode_31
    @IntentGroup
        {
          "boundsTo" : "/newNode_30",
          "actions" : [ {
            "buttons" : [ {
              "name" : "Назад",
              "transition" : "/newNode_16"
            } ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_31
        state: 1
            e: $cat

            go!: /newNode_24

        state: 2
            q: $dog

            go!: /newNode_25

        state: Fallback
            event: noMatch
            go!: /newNode_26
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_31",
                name: "newNode_31 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "Назад"
                    , transition: "/newNode_16"
                    },
                  ]);
                }
            });

    state: newNode_19
        a: Назови любую страну
        go!: /newNode_20
    @IntentGroup
        {
          "boundsTo" : "/newNode_19",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_20
        state: 1
            q: $COUNTRY

            go!: /newNode_22

        state: Fallback
            event: noMatch
            go!: /newNode_21
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_20",
                name: "newNode_20 buttons",
                handler: function($context) {
                }
            });

    state: newNode_32
        a: Спросите "кто ты" или "что ты" или "как тестить" или "что делать"

    state: newNode_33
        random:
            audio: https://smartapp-s3.sberdevices.ru/temporary-botadmin/707/708/audio/AL7IsqgJ2BIEpYb9.mp3?channels={"incompatible":["OUTGOING_CALLS","GOOGLE_ASSISTANCE","ALEXA"],"compatible":["MESSENGERS","ALISA","AIMYBOX"]} || name = "file_example_MP3_700KB.mp3"

    state: newNode_34
        a: Через 3 секунды произойдет переход
        script:
            $reactions.timeout({interval: _.template('3 seconds', {variable: '$session'})($session), targetState: '/newNode_35'});

    state: newNode_37
        InputNumber:
            actions = [{"buttons":[],"type":"buttons"}]
            prompt = Введите число
            varName = numberChoice
            failureMessage = ["Введите число от 1 до 100"]
            then = /newNode_36
            minValue = 1
            maxValue = 100

    state: newNode_41
        a: завершаю сценарий
        go!: /newNode_42

    state: newNode_42
        EndSession:

    state: newNode_8
        a: ваше число: {{$session.number}}

    state: newNode_10
        a:  Вы ввели текст:
            {{$session.text}}

    state: newNode_12
        a: Вы ввели номер телефона {{$session.phone}}

    state: newNode_14
        a: Запрос завершен успешно. Ответ: {{$session.response}}
        buttons:
            "назад" -> /newNode_1

    state: newNode_15
        a: Запрос завершен с ошибкой.. {{$session.httpStatus}}

    state: newNode_21
        a: я такой страны не знаю
        # Transition /newNode_23
        go!: /newNode_19

    state: newNode_22
        a: Ты назвал страну {{$session.Сountry}}

    state: newNode_24
        a: Вы назвали интент {{$session.cat}}
        # Transition /newNode_29
        go!: /newNode_30

    state: newNode_25
        a: Вы назвали интент {{$session.dog}}
        # Transition /newNode_28
        go!: /newNode_30

    state: newNode_26
        a: не нахожу интентов
        # Transition /newNode_27
        go!: /newNode_30

    state: newNode_35
        a: Переход после таймаута в 3 секунды

    state: newNode_36
        if: $session.numberChoice < 50
            go!: /newNode_38
        else:
            go!: /newNode_39

    state: newNode_38
        a: число меньше 50

    state: newNode_39
        a: число больше 50