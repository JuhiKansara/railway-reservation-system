package com.railway.train_service.contoller;

import com.railway.train_service.dto.TrainRequest;
import com.railway.train_service.dto.TrainResponse;
import com.railway.train_service.entity.Route;
import com.railway.train_service.entity.Train;
import com.railway.train_service.service.TrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @PostMapping
    public ResponseEntity<TrainResponse> addTrain(
            @Valid @RequestBody TrainRequest request) {
        Train train = trainService.addTrain(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainService.mapToResponse(train));
    }

    @GetMapping
    public ResponseEntity<List<TrainResponse>> getAllTrains() {
        return ResponseEntity.ok(
                trainService.getAllTrains()
                        .stream()
                        .map(trainService::mapToResponse)
                        .collect(java.util.stream.Collectors.toList())
        );
    }

    @GetMapping("/{trainNumber}")
    public ResponseEntity<TrainResponse> getByNumber(
            @PathVariable String trainNumber) {
        Train train = trainService.getTrainByNumber(trainNumber);
        return ResponseEntity.ok(trainService.mapToResponse(train));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Route>> searchTrains(
            @RequestParam String sourceCode,
            @RequestParam String destinationCode) {
        return ResponseEntity.ok(
                trainService.searchTrains(sourceCode, destinationCode));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("train-service is UP");
    }
}
