package com.test.flights.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.flights.Flight;
import com.test.flights.repository.FlightRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


//reads info from mock data json file and makes java Flight objects
@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public String loadData() throws IOException {
        String path = "./data/flights.json";
        ClassPathResource resource = new ClassPathResource(path);
        byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(data, StandardCharsets.UTF_8);
    }

    public void parseData() throws IOException {
        String json = loadData();
        ObjectMapper mapper = new ObjectMapper();
        Flight[] flights = mapper.readValue(json, Flight[].class);
        for (Flight flight : flights) {
            flightRepository.save(flight);
        }
    }

    public List<Flight> getFlights() throws IOException {
        return flightRepository.getFlights();
    }

    @PostConstruct
    public void init() throws IOException {
        parseData();
    }
}
