package ru.spbpu.weather.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbpu.weather.dto.DayDto;
import ru.spbpu.weather.dto.RequestDto;
import ru.spbpu.weather.dto.WeatherDto;
import ru.spbpu.weather.model.Day;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.model.Weather;
import ru.spbpu.weather.repository.DayRepository;
import ru.spbpu.weather.repository.WeatherRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class WeatherMapper {
    private static final String temperatureUnit = " °C";
    private static final String windUnit = " km/h";
    private final WeatherRepository weatherRepository;
    private final DayRepository dayRepository;

    public RequestDto toRequestDto(RequestHistoryEntity entity) {
        Optional<Weather> result = weatherRepository.findWeatherByRequestId(entity.getId());
        RequestDto requestDto = RequestDto.builder()
                .city(entity.getAddress())
                .requestTimestamp(entity.getRequestTimestamp())
                .build();
        result.ifPresent(weather -> requestDto.setResult(toWeatherDto(weather)));
        return requestDto;
    }

    public Weather toWeatherEntity(WeatherDto dto) {
        String[] temperatureArray = dto.getTemperature().split(" ");
        int temperatureNumber = Integer.parseInt(temperatureArray[0]);
        String[] windArray = dto.getWind().split(" ");
        int windNumber = Integer.parseInt(windArray[0]);
        Weather weather = new Weather();
        weather.setTemperature(temperatureNumber);
        weather.setWind(windNumber);
        weather.setDescription(dto.getDescription());
        return weather;
    }

    public List<Day> getForecast(WeatherDto dto, Weather entity) {
        return dto.getForecast().stream().map(d -> toDayEntity(d, entity)).toList();
    }

    public WeatherDto toWeatherDto(Weather entity) {
        String temperatureString = temperatureSymbol(entity.getTemperature()) + entity.getTemperature() + temperatureUnit;
        String windString = entity.getWind() + windUnit;
        List<DayDto> list = dayRepository.findDaysByWeatherId(entity.getId()).stream().map(this::toDayDto).toList();
        return WeatherDto.builder()
                .temperature(temperatureString)
                .wind(windString)
                .description(entity.getDescription())
                .forecast(list)
                .build();
    }
    private Day toDayEntity(DayDto dto, Weather weather) {
        int dayNumber = Integer.parseInt(dto.getDay());
        String[] temperatureArray = dto.getTemperature().split(" ");
        int temperatureNumber = Integer.parseInt(temperatureArray[0]);
        String[] windArray = dto.getWind().split(" ");
        int windNumber = Integer.parseInt(windArray[0]);
        Day day = new Day();
        day.setTemperature(temperatureNumber);
        day.setDay(dayNumber);
        day.setWind(windNumber);
        day.setWeather(weather);
        return day;
    }

    private DayDto toDayDto(Day entity) {
        String temperatureString = temperatureSymbol(entity.getTemperature()) + entity.getTemperature() + temperatureUnit;
        String windString = entity.getWind() + windUnit;
        return DayDto.builder()
                .day(String.valueOf(entity.getDay()))
                .temperature(temperatureString)
                .wind(windString)
                .build();
    }

    private String temperatureSymbol(int value) {
        if (value > 0)
            return "+";
        else if (value < 0)
            return "-";
        else
            return "";
    }
}