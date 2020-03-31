package pro.sisit.javacourse.unit7.parser.impl;

import org.springframework.stereotype.Service;
import pro.sisit.javacourse.unit7.model.Weather;
import pro.sisit.javacourse.unit7.parser.WeatherParser;

@Service
public class WeatherParserImpl implements WeatherParser {

    private static String DELIMITER = "\n";

    @Override
    public String weatherToString(Weather weather) {
        return weather.getDate() + DELIMITER + weather.getCity()
                + DELIMITER + "Weather: " + weather.getWeather()
                + DELIMITER + "temp: " + weather.getTemp() + DELIMITER;
    }

}
