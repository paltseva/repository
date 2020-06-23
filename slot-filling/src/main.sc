require: slotfilling/slotFilling.sc
    module = sys.zb-common
theme: /

    state: /Start
        q!: $regex</start>
        a: Начнём.

    state:
        intent!: /привет
        a: Привет привет

    state:
        intent!: /пока
        a: Пока пока
    state:
        event!: match
        a: {{$context.intent.answer}}

    state:
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

