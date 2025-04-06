package ru.spbpu.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.model.User;
import ru.spbpu.weather.service.RequestService;
import ru.spbpu.weather.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/history")
public class HistoryController {
    private final RequestService requestService;
    private final UserService userService;
    @GetMapping
    public List<RequestHistoryEntity> getHistory() {
        Optional<User> optionalUser = userService.getCurrentUser();
        if (optionalUser.isPresent()) {
            return requestService.findCurrentUser(optionalUser.get());
        }
        return Collections.emptyList();
    }
}
