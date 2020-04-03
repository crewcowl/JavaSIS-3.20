package pro.sisit.javacourse.unit7.data;

import pro.sisit.javacourse.unit7.model.Weather;

import java.util.List;

public interface WeatherDataService {
    void save (Weather weather);
    List<Weather> load ();
}
