var OPENWEATHERMAP_API_KEY = $injector.api_key;

function openWeatherMapCurrent(units, lang, lat, lon){
    return $http.get("https://api.openweathermap.org/data/2.5/weather?APPID=${APPID}&units=${units}&lang=${lang}&lat=${lat}&lon=${lon}", {
        timeout: 10000,
        query:{
            APPID: OPENWEATHERMAP_API_KEY,
            units: units,
            lang: lang,
            lat: lat,
            lon: lon
        }
    })
}