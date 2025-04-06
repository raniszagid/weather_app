package ru.spbpu.weather.util;

import ru.spbpu.weather.dto.DayDto;
import ru.spbpu.weather.dto.WeatherDto;
import ru.spbpu.weather.model.Day;
import ru.spbpu.weather.model.Weather;

import java.util.List;

public class WeatherMapper {
    private static final String temperatureUnit = " °C";
    private static final String windUnit = " km/h";

    public static Weather toWeatherEntity(WeatherDto dto) {
        String[] temperatureArray = dto.getTemperature().split(" ");
        int temperatureNumber = Integer.parseInt(temperatureArray[0]);
        String[] windArray = dto.getWind().split(" ");
        int windNumber = Integer.parseInt(windArray[0]);
        List<Day> list = dto.getForecast().stream().map(WeatherMapper::toDayEntity).toList();
        return Weather.builder()
                .temperature(temperatureNumber)
                .wind(windNumber)
                .description(dto.getDescription())
                .forecast(list)
                .build();
    }

    public static WeatherDto toWeatherDto(Weather entity) {
        String temperatureString = temperatureSymbol(entity.getTemperature()) + entity.getTemperature() + temperatureUnit;
        String windString = entity.getWind() + windUnit;
        List<DayDto> list = entity.getForecast().stream().map(WeatherMapper::toDayDto).toList();
        return WeatherDto.builder()
                .temperature(temperatureString)
                .wind(windString)
                .description(entity.getDescription())
                .forecast(list)
                .build();
    }
    private static Day toDayEntity(DayDto dto) {
        int dayNumber = Integer.parseInt(dto.getDay());
        String[] temperatureArray = dto.getTemperature().split(" ");
        int temperatureNumber = Integer.parseInt(temperatureArray[0]);
        String[] windArray = dto.getWind().split(" ");
        int windNumber = Integer.parseInt(windArray[0]);
        return Day.builder()
                .day(dayNumber)
                .temperature(temperatureNumber)
                .wind(windNumber)
                .build();
    }

    private static DayDto toDayDto(Day entity) {
        String temperatureString = temperatureSymbol(entity.getTemperature()) + entity.getTemperature() + temperatureUnit;
        String windString = entity.getWind() + windUnit;
        return DayDto.builder()
                .day(String.valueOf(entity.getDay()))
                .temperature(temperatureString)
                .wind(windString)
                .build();
    }

    private static String temperatureSymbol(int value) {
        if (value > 0)
            return "+";
        else if (value < 0)
            return "-";
        else
            return "";
    }
}
