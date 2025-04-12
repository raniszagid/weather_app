package ru.spbpu.weather.util;

import org.junit.jupiter.api.Test;
import ru.spbpu.weather.dto.DayDto;
import ru.spbpu.weather.dto.WeatherDto;
import ru.spbpu.weather.model.Weather;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherMapperTests {
    @Test
    void testMapping() {

        DayDto day1 = DayDto.builder()
                .day("1")
                .wind("15 km/h")
                .temperature("+1 °C")
                .build();
        DayDto day2 = DayDto.builder()
                .day("2")
                .temperature("15 °C")
                .wind("0 km/h")
                .build();

        WeatherDto weatherDto = WeatherDto.builder()
                .temperature("-12 °C")
                .wind("20 km/h")
                .forecast(List.of(day1, day2))
                .build();

        WeatherMapper weatherMapper = new WeatherMapper(null, null);
        Weather entity = weatherMapper.toWeatherEntity(weatherDto);

        assertEquals(-12, entity.getTemperature());
        assertEquals(20, entity.getWind());
    }
}
