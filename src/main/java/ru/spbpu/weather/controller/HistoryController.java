package ru.spbpu.weather.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbpu.weather.dto.RequestDto;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.model.User;
import ru.spbpu.weather.service.RequestService;
import ru.spbpu.weather.service.UserService;
import ru.spbpu.weather.util.WeatherMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
//@Controller
@RequestMapping("/history")
public class HistoryController {
    private final RequestService requestService;
    private final UserService userService;
    private final WeatherMapper mapper;
    @GetMapping
    public List<RequestDto> getHistory() {
        Optional<User> optionalUser = userService.getCurrentUser();
        return optionalUser.map(user -> requestService.findCurrentUserRequests(user).stream().map(mapper::toRequestDto).toList()).orElse(Collections.emptyList());
    }


    /*@PostMapping
    public String create(@ModelAttribute("book") @Valid ,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "bookshelf/new";

        bookService.save(book);
        return "redirect:/weather";
    }*/
}