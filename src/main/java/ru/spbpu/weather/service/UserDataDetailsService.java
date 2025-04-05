package ru.spbpu.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spbpu.weather.model.User;
import ru.spbpu.weather.repository.UserRepository;
import ru.spbpu.weather.security.UserDataDetails;

import java.util.Optional;

@Service
public class UserDataDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDataDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDataDetails(user.get());
    }

    public boolean check(String s) {
        Optional<User> user = userRepository.findByUsername(s);
        return user.isEmpty();
    }
}
