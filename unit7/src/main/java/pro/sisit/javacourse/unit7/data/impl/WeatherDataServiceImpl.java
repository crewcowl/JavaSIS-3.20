package pro.sisit.javacourse.unit7.data.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import pro.sisit.javacourse.unit7.data.WeatherDataService;
import pro.sisit.javacourse.unit7.model.Weather;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherDataServiceImpl implements WeatherDataService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Weather weather) {
        jdbcTemplate.update("INSERT INTO weathers (date, weather, temp, city) VALUES (?, ?, ?, ?)",
                weather.getDate(),
                weather.getWeather(),
                weather.getTemp(),
                weather.getCity());
    }

    @Override
    public List<Weather> load() {
        return jdbcTemplate.query("SELECT * FROM weathers",
                (rs, rowNum) ->
                        new Weather (
                                rs.getString("date"),
                                rs.getString("weather"),
                                rs.getDouble("temp"),
                                rs.getString("city")));
    }

}
