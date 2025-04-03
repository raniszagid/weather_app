package ru.spbpu.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbpu.weather.model.RequestHistoryEntity;
import ru.spbpu.weather.repository.RequestRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestService {
    private final RequestRepository requestRepository;
    public void save(RequestHistoryEntity request) {
        requestRepository.save(request);
    }

    public List<RequestHistoryEntity> findAll() {
        return requestRepository.findAll();
    }
}
