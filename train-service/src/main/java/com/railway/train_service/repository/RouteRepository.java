package com.railway.train_service.repository;

import com.railway.train_service.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("SELECT r FROM Route r WHERE " +
            "r.sourceStation.code = :sourceCode AND " +
            "r.destinationStation.code = :destCode")
    List<Route> findBySourceAndDestination(
            @Param("sourceCode") String sourceCode,
            @Param("destCode") String destCode
    );
}
