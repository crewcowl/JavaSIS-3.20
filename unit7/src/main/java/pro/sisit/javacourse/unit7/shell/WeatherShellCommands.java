package pro.sisit.javacourse.unit7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pro.sisit.javacourse.unit7.data.WeatherDataService;
import pro.sisit.javacourse.unit7.model.Weather;
import pro.sisit.javacourse.unit7.parser.WeatherParser;
import pro.sisit.javacourse.unit7.web.WeatherService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class WeatherShellCommands {


    private final WeatherService weatherService;
    private final WeatherDataService weatherDataService;
    private final WeatherParser weatherParser;

    private Weather lastWeather;

    @ShellMethod(key ="weather", value = "print weather for city")
    public String getWeather (
            @ShellOption() String city){
        this.lastWeather = weatherService.getWeather(city);
        return weatherParser.weatherToString(lastWeather);
    }


    @ShellMethod("Save information")
    public String save() {
        if (lastWeather == null) {
            return "В буфере нет информации о погоде";
        } else {
            weatherDataService.save(lastWeather);
            return "Информация сохранена";
        }
    }

    @ShellMethod("Load information")
    public String show () {
        return weatherDataService.load().stream()
                .map(weatherParser::weatherToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
