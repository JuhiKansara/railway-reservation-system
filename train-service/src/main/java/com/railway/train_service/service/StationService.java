package com.railway.train_service.service;

import com.railway.train_service.dto.StationRequest;
import com.railway.train_service.entity.Station;
import com.railway.train_service.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public Station addStation(StationRequest request) {
        if (stationRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Station code already exists: " + request.getCode());
        }
        Station station = new Station();
        station.setName(request.getName());
        station.setCode(request.getCode().toUpperCase());
        station.setCity(request.getCity());
        station.setState(request.getState());
        return stationRepository.save(station);
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station getByCode(String code) {
        return stationRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Station not found: " + code));
    }
}