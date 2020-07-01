require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.

    state: Bye
        intent!: /пока
        a: Пока пока
        
    # Test ParseTree 
    # $entities counts startPos by tokens
    # $parseTree counts startPos by symbols
    # люблю животных кота, собаку и рыбок
    state: Animals
        intent!: /Люблю животных
        a: {{toPrettyString($parseTree)}}
        a: {{toPrettyString($entities)}}
        
    # Test slots
    # хочу оформить доставку на айфон
    state: slots
        intent!: /доставка
        a: {{toPrettyString($parseTree)}}
        a: доставка оформлена
    
    # Test parseTree whithout slots
    state: no_slots
        intent!: /привет
        a: {{toPrettyString($parseTree)}}  
        a: Привет привет
        
    # Test parseTree whith entities
    # люблю ловить рыбку с собачкой для котика
    state: entities
        intent!: /рыбалка
        a: {{toPrettyString($parseTree)}}  
        a: прекрасное хобби!
        
    # Test parseTree whith entities and slots
    # люблю березки
    state: entities_and_slots
        intent!: /люблю деревья
        a: {{toPrettyString($parseTree)}}  
        a: природа - самое ценное в этом мире!
    
              

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

