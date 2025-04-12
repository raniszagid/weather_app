package ru.spbpu.weather.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Day {
    private int day;
    private int temperature;
    private int wind;
}