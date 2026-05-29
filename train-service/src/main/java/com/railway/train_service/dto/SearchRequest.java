package com.railway.train_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {

    @NotBlank
    private String sourceCode;

    @NotBlank
    private String destinationCode;
}