require: functions.js

theme: /

    state: Start
        q!: * (*start|reset)
        script:
            $session.name = "Unknown_name"
        a: Начнем. Как тебя зовут?
        
        state: Имя
            q: * @pymorphy.name *
            script:
                $session.name = $parseTree.value
            a: Привет, {{$session.name}}! Поиграем в викторину?
            
        state: noMatch
            event: noMatch
            go!: ../../Непонятное имя
    
                
    state: Привет
        q!: * (*привет*|*хай*|*здравствуй*) *
        a: Привет-привет, {{$session.name}}! Сыграем в викторину?
        
    state: Меня зовут
        q!: * (*меня зовут*|*мое имя*) @pymorphy.name *
        q!: * @pymorphy.name * 
        script:
            $session.name = $parseTree.value
        go!: /Привет
        
    state: Непонятное имя
        q!: * (*меня зовут*|*мое имя*) *
        script: 
            $session.name = getName($parseTree.text)
        go!: /Привет
        
    state: Yes
        q: @Yes
        go!: /Викторина
        
    state: No
        q: @No
        a: Жаль. Может передумаешь?
        
        state: Ладно
            q: * (*может быть*|*ладно*|*уговорил*) *
            go!: ../../Викторина
            
        state: Нет
            q: @No
            a: Что поделаешь..
        
    state: Как дела от пользователя
        q!: * (*Как дела*|*как ты*) *
        a: У меня все отлично! А у тебя? 
    
    state: Начнем
        q!: * (*давай*|*начнем*|*начинай*) *
        go!: /Викторина
        
        
    state: Хорошо
        q!: * (*отлично*|*хорошо*|*класс*|*прекрасно*) *
        a: Это здорово! Хочешь сыграть в викторину?
        
    state: Плохо
        q!: * (*плохо*|*не очень*|*ужасно*|*отвратительно*) *
        a: Но почему?
        
        state: Почему
            q: * (*не знаю*|*так сложилось*|*встал не с той ноги*) *
            a: Хочешь, расскажу тебе шутку?
            
            state: Хочу
                q: * (*давай*|*конечно*|*хочу*) *
                q: @Yes
                a: Знаю отличную шутку про UDP, но не факт, что она до тебя дойдет, {{$session.name}}
            
            state: Не надо
                q: * (*нет*|*не надо*|*только не это*|*не хватало*|*избавь меня*) *
                a: Очень жаль! 
                
    state: Нормально
        q!: * (*норм*|*не плохо*|*неплохо*|*средне*) *
        a: Что ж, тоже хорошо. Поиграем в викторину?
                
    state:
        q!: * (*викторин*|*играть*) *
        go!: /Викторина
                
    state: Викторина
        a: Выбирай правильный ответ с помощью кнопки или просто напиши
        go!: /Викторина/Вопрос 1
        
        state: Вопрос 1
            a: Какая из перечисленных рек — самая длинная?
            buttons:
                "Нева" -> /Викторина/Вопрос 1/False
                "Миссисипи" -> /Викторина/Вопрос 1/False
                "Волга" -> /Викторина/Вопрос 1/False
                "Нил" -> /Викторина/Вопрос 1/True
                
            state: noMatch
                event: noMatch
                a: Непонятно, попробуй еще раз
                go!: /Викторина/Вопрос 1
                
            state: True
                a: Правильно, {{$session.name}}! Самая длинная река из перечисленных — Нил. Ее протяженность составляет 6650 км. А вот длина Невы — всего 74 км
                go!: /Викторина/Вопрос 2
                
            state: False
                a: Неправильно, {{$session.name}}! Попробуй снова
                go!: /Викторина/Вопрос 1
                
        state: Вопрос 2
            a: Какой по счету от Солнца является Земля?
            buttons:
                "Третья" -> /Викторина/Вопрос 2/True
                "Пятая" -> /Викторина/Вопрос 2/False
                "Вторая" -> /Викторина/Вопрос 2/False
                "Первая" -> /Викторина/Вопрос 2/False
                
            state: Цифры
                q: * @duckling.number *
                if: $parseTree.value == 3
                    go!: /Викторина/Вопрос 2/True
                else:
                    go!: /Викторина/Вопрос 2/False
                    
            state: noMatch
                event: noMatch
                a: Непонятно, попробуй еще раз
                go!: /Викторина/Вопрос 2
                
            state: True
                a: Правильно, {{$session.name}}! Ближе всех к Солнцу находится Меркурий, Венера — вторая, а Земля замыкает тройку
                go!: ../../../Конец
                
            state: False
                a: Неправильно, {{$session.name}}! Попробуй еще раз!
                go!: /Викторина/Вопрос 2
                
    state: Конец
        a: У меня закончились вопросы, {{$session.name}}! Увидимся, когда меня обучат новым!
                    
    state: Погода
        q: * ~погода *
        a: Погода как всегда чудесная, {{$session.name}}
                

    state: noMatch
        event!: noMatch
        random:
            a: Что-то непонятно ничего...
            a: А ну-ка повтори