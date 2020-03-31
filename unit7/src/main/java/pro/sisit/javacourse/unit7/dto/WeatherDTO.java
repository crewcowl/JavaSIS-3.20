package pro.sisit.javacourse.unit7.dto;

import lombok.Getter;

@Getter
public class WeatherDTO {
    private MainDTO main;
    private WeatherTypeDTO[] weather;
    private String name;
    private String cod;

}
