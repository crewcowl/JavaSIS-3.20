package pro.sisit.javacourse.unit7.model;

import lombok.Data;


@Data
public class Weather {
    private final String date;
    private final String weather;
    private final Double temp;
    private final String city;

    private static String DELIMITER = "\n";

    public String weatherToString() {
        return date + DELIMITER + city
                + DELIMITER + "Weather: " + weather
                + DELIMITER + "temp: " + temp + DELIMITER;
    }
}
