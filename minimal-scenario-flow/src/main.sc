theme: /

    state: newNode_0
        random:
            a: Привет, я тестовый бот, давай проверим работу основных блоков
            a: Алоха, я тестовый смартап, давай проверим работу основных блоков
            a: Коничива, я тестовый вайтборд, давай проверим работу основных блоков
        go!: /newNode_1
    @IntentGroup
        {
          "boundsTo" : "/newNode_0",
          "actions" : [ {
            "buttons" : [ {
              "name" : "меню",
              "transition" : "/newNode_27"
            } ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_1
        state: 1
            q: $AGREEMENT
            e: привет
            e: хай
            e: здравствуй

            go!: /newNode_4

        state: 2
            q: $NEGATION

            go!: /newNode_5

        state: 3
            q: $UNCERTAINTY

            go!: /newNode_6

        state: Fallback
            event: noMatch
            go!: /newNode_2
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_1",
                name: "newNode_1 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "меню"
                    , transition: "/newNode_27"
                    },
                  ]);
                }
            });

    state: newNode_2
        a: скажи либо да, либо нет, а то щас непонятно ничего
        # Transition /newNode_3
        go!: /newNode_0
    @InputText
        {
          "boundsTo" : "",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "prompt" : "Как тебя зовут?",
          "varName" : "name",
          "then" : "/newNode_9"
        }
    state: newNode_4 || modal = true
        a: Как тебя зовут?

        state:
            q: * *start *
            script:
              Zenflow.start();

        state: CatchText
            q: *
            script:
                addClientVarToSession("name", $parseTree.text);
            go!: /newNode_9
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_4",
                name: "newNode_4 buttons",
                handler: function($context) {
                }
            });

    state: newNode_5
        a: ладно, если передумаешь, то дай мне знать

    state: newNode_6
        a: а ты не думай, просто действуй
        buttons:
            "хорошо" -> /newNode_4
            "не хочу" -> /newNode_5
    @InputNumber
        {
          "boundsTo" : "",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "prompt" : "А сколько тебе лет?",
          "varName" : "age",
          "failureMessage" : [ "Введите число от 0 до 200" ],
          "then" : "/newNode_8",
          "minValue" : 0,
          "maxValue" : 200
        }
    state: newNode_9
        a: А сколько тебе лет?

        state: CatchNumber
            q: * $Number *
            script:
                var failureMessages = [
                    "Введите число от 0 до 200"
                ];
                var failureRandom = failureMessages[$reactions.random(failureMessages.length)];
                if ($parseTree._Number < 0) {
                    $reactions.answer(failureRandom);
                } else
                if ($parseTree._Number > 200) {
                    $reactions.answer(failureRandom);
                } else
                {
                    addClientVarToSession("age", $parseTree._Number);
                    $temp.age_ok = true;
                }
            if: $temp.age_ok
                go!: /newNode_8
            else:
                go: CatchNumber

        state: CatchAll
            q: *
            go!: ..
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_9",
                name: "newNode_9 buttons",
                handler: function($context) {
                }
            });

    state: newNode_8
        if: $session.age < 18
            go!: /newNode_10
        elseif: $session.age > 18 && $session.age < 90
            go!: /newNode_12
        elseif: $session.age > 90 && $session.age < 110
            go!: /newNode_13
        elseif: $session.age > 110
            go!: /newNode_14
        else:
            go!: /newNode_15

    state: newNode_10
        a: Да ты еще совсем зеленый, {{$session.name}}
        # Transition /newNode_11
        go!: /newNode_20

    state: newNode_12
        a: Давай продолжим, {{$session.name}}
        # Transition /newNode_17
        go!: /newNode_20

    state: newNode_13
        a: Ты хоть видишь этот текст, {{$session.name}}, в таком-то возрасте... Ну ладно, давай продолжать
        # Transition /newNode_18
        go!: /newNode_20

    state: newNode_14
        a: Так долго не живут, {{$session.name}}! Но продолжим...
        # Transition /newNode_19
        go!: /newNode_20

    state: newNode_15
        a: ой, что-то пошло не так
        # Transition /newNode_16
        go!: /newNode_9

    state: newNode_20
        a: ты предпочитаешь кошек или собак?
        go!: /newNode_21
    @IntentGroup
        {
          "boundsTo" : "/newNode_20",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : true
        }
    state: newNode_21
        state: 1
            q!: $cat

            go!: /newNode_42

        state: 2
            q!: $dog

            go!: /newNode_44

        state: Fallback
            event: noMatch
            go!: /newNode_23
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_21",
                name: "newNode_21 buttons",
                handler: function($context) {
                }
            });

    state: newNode_42
        image: https://pbs.twimg.com/media/Dl8iXpvWwAEk_A3.jpg:large
        random:
            audio: http://tkles-mvp000375.vm.esrt.cloud.sbrf.ru:9000/ci02552725-sm-smartapp-ba/372/373/audio/mGojCxKhL43UkoFG.mp3?channels={"incompatible":["OUTGOING_CALLS","GOOGLE_ASSISTANCE","ALEXA"],"compatible":["AIMYBOX","ALISA","MESSENGERS"]} || name = "Cat Casino — Cat he's a Kitty Cat.mp3"
        script:
            $reactions.timeout({interval: _.template('3 seconds', {variable: '$session'})($session), targetState: '/newNode_22'});

    state: newNode_44
        image: https://avatars.mds.yandex.net/get-pdb/1337375/51146ed9-9189-4f3d-985c-61e2b69c916b/s1200
        random:
            audio: http://tkles-mvp000375.vm.esrt.cloud.sbrf.ru:9000/ci02552725-sm-smartapp-ba/372/373/audio/Eg7l1HCtdA0aNC89.mp3?channels={"incompatible":["OUTGOING_CALLS","GOOGLE_ASSISTANCE","ALEXA"],"compatible":["AIMYBOX","ALISA","MESSENGERS"]} || name = "90_rington.online_like-a-boss.mp3"
        script:
            $reactions.timeout({interval: _.template('3 seconds', {variable: '$session'})($session), targetState: '/newNode_24'});

    state: newNode_22
        a: да, котики клевые! продолжим
        # Transition /newNode_26
        go!: /newNode_27

    state: newNode_24
        a: собака - лучший друг человека! продолжим
        # Transition /newNode_25
        go!: /newNode_27

    state: newNode_27
        a: выбери что-то из списка или напиши
        go!: /newNode_71
    @IntentGroup
        {
          "boundsTo" : "/newNode_27",
          "actions" : [ {
            "buttons" : [ {
              "name" : "изображение локально",
              "transition" : "/newNode_28"
            }, {
              "name" : "изображение по ссылке",
              "transition" : "/newNode_29"
            }, {
              "name" : "аудио локально",
              "transition" : "/newNode_30"
            }, {
              "name" : "аудио по ссылке",
              "transition" : "/newNode_34"
            }, {
              "name" : "системные сущности",
              "transition" : "/newNode_35"
            }, {
              "name" : "завершить",
              "transition" : "/newNode_47"
            }, {
              "name" : "в начало",
              "transition" : "/newNode_0"
            }, {
              "name" : "дальше",
              "transition" : "/newNode_66"
            } ],
            "type" : "buttons"
          } ],
          "global" : true
        }
    state: newNode_71
        state: 1
            e!: изображение по ссылке

            go!: /newNode_29

        state: 2
            e!: аудио локально

            go!: /newNode_30

        state: 3
            e!: аудио по ссылке

            go!: /newNode_34

        state: 4
            e!: системные сущности

            go!: /newNode_35

        state: 5
            e!: завершить

            go!: /newNode_47

        state: 6
            e!: в начало
            e!: start
            e!: старт
            e!: рестарт
            e!: restart

            go!: /newNode_0

        state: 7
            q!: изображение локально

            go!: /newNode_28

        state: Fallback
            event: noMatch
            go!: /newNode_27
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_71",
                name: "newNode_71 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "изображение локально"
                    , transition: "/newNode_28"
                    },
                    {text: "изображение по ссылке"
                    , transition: "/newNode_29"
                    },
                    {text: "аудио локально"
                    , transition: "/newNode_30"
                    },
                    {text: "аудио по ссылке"
                    , transition: "/newNode_34"
                    },
                    {text: "системные сущности"
                    , transition: "/newNode_35"
                    },
                    {text: "завершить"
                    , transition: "/newNode_47"
                    },
                    {text: "в начало"
                    , transition: "/newNode_0"
                    },
                    {text: "дальше"
                    , transition: "/newNode_66"
                    },
                  ]);
                }
            });

    state: newNode_66
        a: выбери что-то из списка или напиши
        go!: /newNode_72
    @IntentGroup
        {
          "boundsTo" : "/newNode_66",
          "actions" : [ {
            "buttons" : [ {
              "name" : "номер телефона",
              "transition" : "/newNode_60"
            }, {
              "name" : "назад",
              "transition" : "/newNode_27"
            }, {
              "name" : "http",
              "transition" : "/newNode_67"
            } ],
            "type" : "buttons"
          } ],
          "global" : true
        }
    state: newNode_72
        state: 1
            e!: номер телефона

            go!: /newNode_60

        state: 2
            e!: http

            go!: /newNode_67

        state: Fallback
            event: noMatch
            go!: /newNode_66
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_72",
                name: "newNode_72 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "номер телефона"
                    , transition: "/newNode_60"
                    },
                    {text: "назад"
                    , transition: "/newNode_27"
                    },
                    {text: "http"
                    , transition: "/newNode_67"
                    },
                  ]);
                }
            });

    state: newNode_28
        image: http://tkles-mvp000375.vm.esrt.cloud.sbrf.ru:9000/ci02552725-sm-smartapp-ba/372/373/gEz5GBo0MbXVmRjI.jpg
        # Transition /newNode_31
        go!: /newNode_27

    state: newNode_29
        image: https://avatars.mds.yandex.net/get-pdb/2839356/e3d98f17-26ac-422d-96c7-c76a15f84c49/s1200?webp=false
        # Transition /newNode_32
        go!: /newNode_27

    state: newNode_30
        random:
            audio: http://tkles-mvp000375.vm.esrt.cloud.sbrf.ru:9000/ci02552725-sm-smartapp-ba/372/373/audio/Teg9MoTtrblRCxHP.mp3?channels={"incompatible":["OUTGOING_CALLS","GOOGLE_ASSISTANCE","ALISA","ALEXA"],"compatible":["AIMYBOX","MESSENGERS"]} || name = "noch_v_derevne.mp3"
        # Transition /newNode_33
        go!: /newNode_27

    state: newNode_34
        random:
            audio: https://cdn.drivemusic.me/dl/online/ihbX-1Mlrk7Lty8qHgqysw/1593482230/download_music/2015/10/bez-imeni-zhurchanie-ruchja.mp3 || name = "https://cdn.drivemusic.me/dl/online/ihbX-1Mlrk7Lty8qHgqysw/1593482230/download_music/2015/10/bez-imeni-zhurchanie-ruchja.mp3"
        # Transition /newNode_36
        go!: /newNode_27

    state: newNode_35
        a: напиши любой город
        go!: /newNode_37
    @IntentGroup
        {
          "boundsTo" : "/newNode_35",
          "actions" : [ {
            "buttons" : [ {
              "name" : "меню",
              "transition" : "/newNode_27"
            } ],
            "type" : "buttons"
          } ],
          "global" : true
        }
    state: newNode_37
        state: 1
            q!: $CITY

            go!: /newNode_64

        state: Fallback
            event: noMatch
            go!: /newNode_56
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_37",
                name: "newNode_37 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "меню"
                    , transition: "/newNode_27"
                    },
                  ]);
                }
            });

    state: newNode_47
        image: http://tkles-mvp000375.vm.esrt.cloud.sbrf.ru:9000/ci02552725-sm-smartapp-ba/372/373/JgvwNDBU68I55BSw.png
    @EndSession
        {
          "boundsTo" : "/newNode_47",
          "actions" : [ ]
        }
    state: newNode_46
        script:
            $session = new Object();
            $response.endSession = true;
    @InputPhoneNumber
        {
          "boundsTo" : "",
          "actions" : [ {
            "buttons" : [ {
              "name" : "меню",
              "transition" : "/newNode_27"
            } ],
            "type" : "buttons"
          } ],
          "prompt" : "Введите номер телефона",
          "varName" : "phone",
          "failureMessage" : [ "Некорректный номер телефона" ],
          "then" : "/newNode_61"
        }
    state: newNode_60
        a: Введите номер телефона

        state: CatchPhoneNumber
            q: * $mobilePhoneNumber *
            script:
                addClientVarToSession("phone", $parseTree._mobilePhoneNumber);
            go!: /newNode_61

        state: WrongPhoneNumber
            script:
                var failureMessages = [
                    "Некорректный номер телефона"
                ];
                $temp.failureRandom = failureMessages[$reactions.random(failureMessages.length)];
                $reactions.answer($temp.failureRandom);
            go: ../CatchPhoneNumber

        state: CatchAll
            q: *
            go!: ../WrongPhoneNumber
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_60",
                name: "newNode_60 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "меню"
                    , transition: "/newNode_27"
                    },
                  ]);
                }
            });
    @HttpRequest
        {
          "boundsTo" : "",
          "title" : "Http-запрос get config",
          "actions" : [ ],
          "url" : "http://localhost:9010/version",
          "method" : "GET",
          "body" : "",
          "okState" : "/newNode_68",
          "errorState" : "/newNode_69",
          "timeout" : 0,
          "headers" : [ {
            "name" : "Product",
            "value" : "FLOW"
          } ],
          "vars" : [ {
            "name" : "version",
            "value" : "$httpResponse"
          } ]
        }
    state: newNode_67
        script:
            var headers = {
                "Product": _.template("FLOW", {variable: '$session'})($session)
            };
            var result = $http.query("http://localhost:9010/version", {
                method: "GET",
                headers: headers,
                query: $session,
                timeout: 0 || 10000
            });
            var $httpResponse = result.data;
            $session.httpStatus = result.status;
            $session.httpResponse = $httpResponse;
            if (result.isOk && result.status >= 200 && result.status < 300) {
                addClientVarToSession("version", $httpResponse);
                $reactions.transition("/newNode_68");
            } else {
                $reactions.transition("/newNode_69");
            }

    state: newNode_38
        if: $session.CITY.capital == "true"
            go!: /newNode_48
        elseif: $session.CITY.capital == "false"
            go!: /newNode_49
        else:
            go!: /newNode_63

    state: newNode_56
        a: непонятненько, ты сказал {{$session.queryText}}
        # Transition /newNode_57
        go!: /newNode_35

    state: newNode_61
        a: ваш номер телефона {{$session.phone}}
        # Transition /newNode_62
        go!: /newNode_27

    state: newNode_64
        a: ваш город {{$session.CITY}}
        # Transition /newNode_65
        go!: /newNode_38

    state: newNode_68
        image: https://ss.sport-express.ru/userfiles/materials/125/1254757/large.jpg
        a: запрос выполнен успешно! вот ответ
        a: {{$session.version}}
        script:
            $reactions.timeout({interval: _.template('5 seconds', {variable: '$session'})($session), targetState: '/newNode_66'});

    state: newNode_69
        a: ой, что-то пошло не так
        # Transition /newNode_70
        go!: /newNode_66

    state: newNode_48
        a: Это столица
        image: https://avatars.mds.yandex.net/get-pdb/1676693/030f6dd7-8100-4a34-a00a-b22e40d6e710/s1200?webp=false
        # Transition /newNode_50
        go!: /newNode_52

    state: newNode_49
        a: это не столица
        image: https://mtdata.ru/u21/photoDCFC/20788059586-0/original.jpg
        # Transition /newNode_51
        go!: /newNode_52

    state: newNode_63
        a: почему-то не записалась переменная capital
        script:
            $reactions.timeout({interval: _.template('3 seconds', {variable: '$session'})($session), targetState: '/newNode_52'});

    state: newNode_52
        a: Назови любое имя
        go!: /newNode_53
    @IntentGroup
        {
          "boundsTo" : "/newNode_52",
          "actions" : [ {
            "buttons" : [ {
              "name" : "меню",
              "transition" : "/newNode_27"
            } ],
            "type" : "buttons"
          } ],
          "global" : true
        }
    state: newNode_53
        state: 1
            q!: $NAME

            go!: /newNode_58

        state: Fallback
            event: noMatch
            go!: /newNode_54
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_53",
                name: "newNode_53 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "меню"
                    , transition: "/newNode_27"
                    },
                  ]);
                }
            });

    state: newNode_54
        a: непонятненько, ты сказал {{$session.queryText}}
        # Transition /newNode_55
        go!: /newNode_52

    state: newNode_58
        a: ты назвал имя {{$session.NAME.name}}
        # Transition /newNode_59
        go!: /newNode_27
    @Transition
        {
          "boundsTo" : "",
          "actions" : [ ],
          "then" : "/newNode_20"
        }
    state: newNode_23
        go!: /newNode_20