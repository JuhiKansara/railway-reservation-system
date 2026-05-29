package com.railway.train_service.service;

import com.railway.train_service.dto.TrainRequest;
import org.springframework.transaction.annotation.Transactional;
import com.railway.train_service.dto.TrainResponse;
import com.railway.train_service.entity.*;
import com.railway.train_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;

    @Transactional
    public Train addTrain(TrainRequest request) {
        if (trainRepository.existsByTrainNumber(request.getTrainNumber())) {
            throw new RuntimeException("Train number already exists");
        }

        // Fetch stations — fail fast if either doesn't exist
        Station source = stationRepository
                .findByCode(request.getSourceStationCode().toUpperCase())
                .orElseThrow(() -> new RuntimeException(
                        "Source station not found: " + request.getSourceStationCode()));

        Station destination = stationRepository
                .findByCode(request.getDestinationStationCode().toUpperCase())
                .orElseThrow(() -> new RuntimeException(
                        "Destination station not found: " + request.getDestinationStationCode()));

        // Build train
        Train train = new Train();
        train.setTrainNumber(request.getTrainNumber());
        train.setTrainName(request.getTrainName());
        train.setTotalSeats(request.getTotalSeats());
        train.setAvailableSeats(request.getTotalSeats()); // all seats available initially
        train.setType(Train.TrainType.valueOf(request.getType().toUpperCase()));
        Train savedTrain = trainRepository.save(train);

        // Build route and link to train
        Route route = new Route();
        route.setTrain(savedTrain);
        route.setSourceStation(source);
        route.setDestinationStation(destination);
        route.setDepartureTime(request.getDepartureTime());
        route.setArrivalTime(request.getArrivalTime());
        route.setDistanceKm(request.getDistanceKm());
        route.setFare(request.getFare());
        savedTrain.setRoute(route);

        routeRepository.save(route);

        return savedTrain;
    }

    @Transactional(readOnly = true)
    public List<Route> searchTrains(String sourceCode, String destCode) {
        return routeRepository.findBySourceAndDestination(
                sourceCode.toUpperCase(),
                destCode.toUpperCase()
        );
    }

    @Transactional(readOnly = true)
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Train getTrainByNumber(String trainNumber) {
        return trainRepository.findByTrainNumber(trainNumber)
                .orElseThrow(() -> new RuntimeException("Train not found: " + trainNumber));
    }

    public TrainResponse mapToResponse(Train train) {
        TrainResponse response = new TrainResponse();
        response.setId(train.getId());
        response.setTrainNumber(train.getTrainNumber());
        response.setTrainName(train.getTrainName());
        response.setTotalSeats(train.getTotalSeats());
        response.setAvailableSeats(train.getAvailableSeats());
        response.setType(train.getType().name());

        Route route = train.getRoute();
        if (route != null) {
            response.setSourceStationCode(route.getSourceStation().getCode());
            response.setSourceStationName(route.getSourceStation().getName());
            response.setSourceCity(route.getSourceStation().getCity());

            response.setDestinationStationCode(route.getDestinationStation().getCode());
            response.setDestinationStationName(route.getDestinationStation().getName());
            response.setDestinationCity(route.getDestinationStation().getCity());

            response.setDepartureTime(route.getDepartureTime());
            response.setArrivalTime(route.getArrivalTime());
            response.setDistanceKm(route.getDistanceKm());
            response.setFare(route.getFare());
        }

        return response;
    }
}