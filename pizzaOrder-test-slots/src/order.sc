theme: /Order
    state: Pizza
        intent!: /OrderPizza
        script:
            $session.PizzaName = $parseTree._PizzaName
            $session.PizzaSize = $parseTree._PizzaSize
            $session.PizzaCount = $parseTree._PizzaCount
        go!: /Order/Result
        
    state: Result
        a: Название пиццы: {{ $session.PizzaName }}
        a: Размер пиццы: {{ $session.PizzaSize }}
        a: Количество: {{ $session.PizzaCount }}