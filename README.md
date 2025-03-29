# Project

The application has two pages, the first lets the client select a flight with the possibility to filter a specific flight.
The origin city is always Tallinn (for simplicity), the destination can be one of these cities: Helsinki, Rome, Berlin, Madrid, Lisbon, Paris, Vienna, Stockholm, Oslo, London.
The departure and arrival dates, as well as the price, can be adjusted.

When the client selects a flight (clicks on a row), the client is taken to the seat arrangement page.
On the seat arrangement page there is a view of the plane, the taken seats are gray and the available seats are blue.
The client can select the number of tickets (seats) they want to purchase on the flight. 
The recommended seats are yellow, and are the first available seats on the plane.

# Back-End

To run the back-end navigate to the src/main/java/com.test.flights folder. You need to run the main method of the FlightApplication class.

# flight-project - Front end

To run the front-end navigate to the src/frontend/flight-project folder on the terminal and follow the project setup info.
Open http://localhost:5173/

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```