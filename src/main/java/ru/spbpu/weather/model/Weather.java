package ru.spbpu.weather.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Weather {
    private int temperature;
    private int wind;
    private String description;
    private List<Day> forecast;
}