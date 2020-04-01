package pro.sisit.javacourse.unit7.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.sisit.javacourse.unit7.dto.WeatherDTO;
import pro.sisit.javacourse.unit7.model.Weather;
import pro.sisit.javacourse.unit7.web.WeatherService;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    @Value("${x-rapidapi-key}")
    private String KEY;


    private final RestTemplate restTemplate;

    @Override
    public Weather getWeather(String city) {
        String URL = "https://community-open-weather-map.p.rapidapi.com/weather?units=metric&mode=json&q=";
        String url = URL + city;

        final HttpHeaders headers = new HttpHeaders();
        String HOST = "community-open-weather-map.p.rapidapi.com";
        headers.set("x-rapidapi-host", HOST);
        headers.set("x-rapidapi-key", KEY);
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<WeatherDTO> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET,httpEntity, WeatherDTO.class);

        return new Weather(LocalDate.now().toString()
                ,responseEntity.getBody().getWeather()[0].getMain()
                ,Double.parseDouble(responseEntity.getBody().getMain().getTemp()),city);
    }
}
