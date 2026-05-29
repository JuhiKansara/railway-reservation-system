package com.railway.train_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.LocalTime;

@Data
public class TrainRequest {

    @NotBlank(message = "Train number is required")
    private String trainNumber;

    @NotBlank(message = "Train name is required")
    private String trainName;

    @NotNull @Min(1)
    private Integer totalSeats;

    @NotBlank
    private String type;                // "EXPRESS", "RAJDHANI" etc

    @NotBlank
    private String sourceStationCode;   // "CSTM"

    @NotBlank
    private String destinationStationCode; // "NDLS"

    @NotNull
    private LocalTime departureTime;    // "06:00"

    @NotNull
    private LocalTime arrivalTime;      // "22:30"

    @NotNull
    private Double distanceKm;

    @NotNull
    private Double fare;
}