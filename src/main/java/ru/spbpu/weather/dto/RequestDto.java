package ru.spbpu.weather.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RequestDto {
    private String city;
    private LocalDateTime requestTimestamp;
    private WeatherDto result;
}
