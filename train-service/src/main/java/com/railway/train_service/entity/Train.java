package com.railway.train_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "trains")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trainNumber;     // e.g. "12951"

    @Column(nullable = false)
    private String trainName;       // e.g. "Rajdhani Express"

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainType type;

    @OneToOne(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("train")
    private Route route;

    public enum TrainType {
        EXPRESS, SUPERFAST, LOCAL, RAJDHANI, SHATABDI
    }
}
