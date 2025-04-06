package ru.spbpu.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.spbpu.weather.model.User;
import ru.spbpu.weather.repository.UserRepository;
import ru.spbpu.weather.security.UserDataDetails;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public boolean loggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails;
    }

    public Optional<User> getCurrentUser() {
        if (loggedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDataDetails personDetails = (UserDataDetails) authentication.getPrincipal();
            String username = personDetails.getUsername();
            return userRepository.findByUsername(username);
        }
        else {
            return Optional.empty();
        }
    }
}
