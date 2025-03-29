package com.test.flights.controller;

import com.test.flights.Flight;
import com.test.flights.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


//method that retrieves all the flight details from a json file
@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @GetMapping("/search")
    public List<Flight> searchFlights() throws IOException {
        return flightService.getFlights();
    }
}