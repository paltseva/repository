require: slotfilling/slotFilling.sc
  module = sys.zb-common

theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.
        script:
            $context.client = {}
            $context.session = {}

    state:
        intent!: /Погода
        a: Стейт Погода

    state:
        intent!: /Доставка
        a: Стейт Доставка
    
    state:
        intent!: /Заказ
        a: Стейт Заказ
        
    state:
       event!: match
       a: {{$context.intent.answer}}
    


    state:
       event!: noMatch
       a: Я не понял. Вы сказали: {{$request.query}}

