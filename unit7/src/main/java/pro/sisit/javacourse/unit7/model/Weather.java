package pro.sisit.javacourse.unit7.model;

import lombok.Data;

import java.time.LocalDate;


@Data
public class Weather {
    private final String date;
    private final String weather;
    private final Double temp;
    private final String city;
}
