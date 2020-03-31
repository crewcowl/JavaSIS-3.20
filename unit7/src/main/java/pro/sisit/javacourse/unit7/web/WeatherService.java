package pro.sisit.javacourse.unit7.web;

import pro.sisit.javacourse.unit7.model.Weather;

public interface WeatherService {
    Weather getWeather(String city);
}
