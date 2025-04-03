package ru.spbpu.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbpu.weather.model.RequestHistoryEntity;

@Repository
public interface RequestRepository extends JpaRepository<RequestHistoryEntity, Integer> {
}
