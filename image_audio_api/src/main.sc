require: functions.js

theme: /
    
    state:
        q!: start ччч
        a: Вы сказали и бот ответил {{$parseTree.text}} copy
    
    state: 
        q!: *
        a: скажите что-то осмысленное
    
    state: image
        q!: JA image
        a: изображение
        image: https://248305.selcdn.ru/public_test/255/256/JtRAnNB2EYoldTfo.jpg
        
    state: audio
        q!: JA audio
        a: аудио
        audio: https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3 || name = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    
    state: buttons
        q!: JA buttons
        a: кнопки
        buttons:
            "Кнопка1" -> /NormalButtons1
            "Кнопка2" -> /NormalButtons2
            
    state: NormalButtons1
        a: Результат нажатия кнопки 1
        
    state: NormalButtons2
        a: Результат нажатия кнопки 2
        
    state: inline
        q!: JA inlineButtons
        a: 123
        inlineButtons:
            {text:"Просмотреть", url:"http://ya.ru"}
    
    state: getWeather
        q!: JA meteo
        script:
            openWeatherMapCurrent("metric", "ru", 59, 30).then(function(res) {
                if (res && res.weather) {
                    $reactions.answer("Сегодня в Питере " + res.weather[0].description + ", " + Math.round(res.main.temp) + " C");
                } else {
                    $reactions.answer("что-то сервер барахлит. не могу узнать погоду");
                }
            }).catch(function (err) {
                $reactions.answer("что-то сервер барахлит. не могу узнать погоду.");
            });
