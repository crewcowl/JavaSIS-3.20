package pro.sisit.javacourse.unit7.web.impl;

import lombok.RequiredArgsConstructor;
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

    private final String URL = "https://community-open-weather-map.p.rapidapi.com/weather?units=metric&mode=json&q=";
    private final String HOST = "community-open-weather-map.p.rapidapi.com";
    private final String KEY = "681178e147msh92a7f9ccacbf835p150307jsn27b16c020d0b";
    //"cdd96ad95amsheebcca53e65aac1p136010jsn18e30e4e6350";


    private final RestTemplate restTemplate;

    @Override
    public Weather getWeather(String city) {
        String url = this.URL + city;

        final HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", this.HOST);
        headers.set("x-rapidapi-key", this.KEY);
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<WeatherDTO> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET,httpEntity, WeatherDTO.class);

        return new Weather(LocalDate.now().toString()
                ,responseEntity.getBody().getWeather()[0].getMain()
                ,Double.parseDouble(responseEntity.getBody().getMain().getTemp()),city);
    }
}
