package com.railway.train_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainResponse {

    private Long id;
    private String trainNumber;
    private String trainName;
    private Integer totalSeats;
    private Integer availableSeats;
    private String type;

    // Route details flattened into the response
    private String sourceStationCode;
    private String sourceStationName;
    private String sourceCity;

    private String destinationStationCode;
    private String destinationStationName;
    private String destinationCity;

    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Double distanceKm;
    private Double fare;
}