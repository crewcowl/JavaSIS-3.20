package pro.sisit.javacourse.unit7.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MainDTO {
        private String temp;
        @JsonProperty("feels_like")
        private String feelsLike;
        @JsonProperty("temp_min")
        private String tempMin;
        @JsonProperty("temp_max")
        private String tempMax;
        private String pressure;
        private String humidity;
}
