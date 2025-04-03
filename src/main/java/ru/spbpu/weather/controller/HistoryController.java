package ru.spbpu.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.service.RequestService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/history")
public class HistoryController {
    private final RequestService requestService;
    @GetMapping
    public List<RequestHistoryEntity> getAll() {
        return requestService.findAll();
    }
}
