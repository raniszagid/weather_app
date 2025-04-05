package ru.spbpu.weather.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.spbpu.weather.model.User;
import ru.spbpu.weather.security.JWTUtil;
import ru.spbpu.weather.security.UserDataDetails;
import ru.spbpu.weather.service.RegistrationService;
import ru.spbpu.weather.dto.AuthenticationDTO;
import ru.spbpu.weather.service.UserDataDetailsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final UserDataDetailsService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<String> performRegistration(@RequestBody @Valid AuthenticationDTO authenticationDTO,
                                                      BindingResult bindingResult) {
        User user = new User(authenticationDTO.getUsername(), authenticationDTO.getPassword());
        //userValidator.validate(user, bindingResult);
        checkBindingResult(bindingResult);
        registrationService.register(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDataDetails personDetails = (UserDataDetails) authentication.getPrincipal();
            username = personDetails.getUsername();
        }
        else {
            username = "xxxx";
        }
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    private void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append("; ");
            }
            throw new RuntimeException(errorMsg.toString());
        }
    }
}
