package ru.spbpu.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.service.RequestService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final RequestService requestService;

    @GetMapping("/{city}")
    public void get(@PathVariable("city") String city) {
        RequestHistoryEntity request = new RequestHistoryEntity();
        request.setAddress(city);
        request.setRequestTimestamp(LocalDateTime.now());
        requestService.save(request);
    }

}
