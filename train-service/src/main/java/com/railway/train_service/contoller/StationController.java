package com.railway.train_service.contoller;

import com.railway.train_service.dto.StationRequest;
import com.railway.train_service.entity.Station;
import com.railway.train_service.service.StationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trains/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<Station> addStation(
            @Valid @RequestBody StationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(stationService.addStation(request));
    }

    @GetMapping
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Station> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(stationService.getByCode(code));
    }
}
