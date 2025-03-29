package com.test.flights.repository;

import com.test.flights.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class FlightRepository {
    private List<Flight> flights = new ArrayList<>();

    public void save(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getFlights() {
        return flights;
    }
}