package ru.spbpu.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.spbpu.weather.dto.WeatherDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private ApiService() {}

    public static WeatherDto makeRequest(String city) throws IOException {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            String url = "http://goweather.xyz/weather/" + city;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String json = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, WeatherDto.class);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new WeatherDto();
        }
    }
}

