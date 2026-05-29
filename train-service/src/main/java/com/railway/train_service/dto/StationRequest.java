package com.railway.train_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StationRequest {

    @NotBlank(message = "Station name is required")
    private String name;

    @NotBlank(message = "Station code is required")
    @Size(max = 10, message = "Code must be 10 chars or less")
    private String code;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;
}