package ru.spbpu.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbpu.weather.dto.WeatherDto;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.service.ApiService;
import ru.spbpu.weather.service.RequestService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final RequestService requestService;

    @GetMapping("/{city}")
    public WeatherDto get(@PathVariable("city") String city) throws Exception {
        RequestHistoryEntity request = new RequestHistoryEntity();
        request.setAddress(city);
        request.setRequestTimestamp(LocalDateTime.now());
        requestService.save(request);
        return ApiService.makeRequest(city);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
