package com.example.move.model





data class Weather(
    val city:CityAndWeather = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0

)


fun getDefaultCity() = CityAndWeather("Москва", 55.755826, 37.617299900000035)

fun getWorldCities()= listOf(
    Weather(CityAndWeather("Лондон", 51.5085300, -0.1257400), 1, 2),
    Weather(CityAndWeather("Токио", 35.6895000, 139.6917100), 3, 4),
    Weather(CityAndWeather("Париж", 48.8534100, 2.3488000), 5, 6),
    Weather(CityAndWeather("Берлин", 52.52000659999999, 13.404953999999975), 7, 8),
    Weather(CityAndWeather("Рим", 41.9027835, 12.496365500000024), 9, 10),
    Weather(CityAndWeather("Минск", 53.90453979999999, 27.561524400000053), 11, 12),
    Weather(CityAndWeather("Стамбул", 41.0082376, 28.97835889999999), 13, 14),
    Weather(CityAndWeather("Вашингтон", 38.9071923, -77.03687070000001), 15, 16),
    Weather(CityAndWeather("Киев", 50.4501, 30.523400000000038), 17, 18),
    Weather(CityAndWeather("Пекин", 39.90419989999999, 116.40739630000007), 19, 20)
)


fun getRussianCities()= listOf(
    Weather(CityAndWeather("Москва", 55.755826, 37.617299900000035), 1, 2),
    Weather(CityAndWeather("Санкт-Петербург", 59.9342802, 30.335098600000038), 3, 3),
    Weather(CityAndWeather("Новосибирск", 55.00835259999999, 82.93573270000002), 5, 6),
    Weather(CityAndWeather("Екатеринбург", 56.83892609999999, 60.60570250000001), 7, 8),
    Weather(CityAndWeather("Нижний Новгород", 56.2965039, 43.936059), 9, 10),
    Weather(CityAndWeather("Казань", 55.8304307, 49.06608060000008), 11, 12),
    Weather(CityAndWeather("Челябинск", 55.1644419, 61.4368432), 13, 14),
    Weather(CityAndWeather("Омск", 54.9884804, 73.32423610000001), 15, 16),
    Weather(CityAndWeather("Ростов-на-Дону", 47.2357137, 39.701505), 17, 18),
    Weather(CityAndWeather("Уфа", 54.7387621, 55.972055400000045), 19, 20)
)