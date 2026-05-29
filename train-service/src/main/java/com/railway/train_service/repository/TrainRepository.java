package com.railway.train_service.repository;

import com.railway.train_service.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByTrainNumber(String trainNumber);
    boolean existsByTrainNumber(String trainNumber);
}
