package ru.spbpu.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbpu.weather.dto.WeatherDto;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.model.User;
import ru.spbpu.weather.service.ApiService;
import ru.spbpu.weather.service.RequestService;
import ru.spbpu.weather.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final RequestService requestService;
    private final UserService userService;

    @GetMapping("/{city}")
    public WeatherDto get(@PathVariable("city") String city) throws Exception {
        RequestHistoryEntity request = new RequestHistoryEntity();
        request.setAddress(city);
        request.setRequestTimestamp(LocalDateTime.now());
        Optional<User> optionalUser = userService.getCurrentUser();
        optionalUser.ifPresent(request::setUser);
        requestService.save(request);
        return ApiService.makeRequest(city);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
