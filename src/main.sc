require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём. Это сценария the-wether
        a: поехали 44545454

    state: Приветствие
        intent!: /привет
        a: Привет привет

    state: Прощание
        intent!: /пока
        a: Пока пока
        
    state: Погода
        intent!: /погода
        a: заполнил погоду, слоты {{ $parseTree._fallout}} и {{ $parseTree._fallout2}}

    state: Fallback
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

