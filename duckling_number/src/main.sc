require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: 
        q!: $regex</start>
        a: Начнём.dfdfdfdffdfd

    state:
        intent!: /привет
        a: Привет привет

    state:
        intent!: /пока
        a: Пока пока
        
    state: number
        intent!: /число
        a: {{toPrettyString($entities)}}
        

    state:
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

