package com.railway.train_service.repository;

import com.railway.train_service.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findByCode(String code);
    boolean existsByCode(String code);
}
