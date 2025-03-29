<script>
import axios from "axios";
import { ref, onMounted, computed } from "vue";
import DataTable from "primevue/datatable";
import Column from "primevue/column";

export default {
  components: {
    DataTable,
    Column
  },
  setup() {
    const flights = ref([]);
    const filters = ref({
      destination: "",
      departureTime: "",
      arrivalTime: "",
      price: ""
    });

    const getFlights = async () => {
      try {
        const response = await axios("http://localhost:8080/api/flights/search");
        flights.value = response.data;
      } catch (e) {
        console.error("Error fetching flights:", e);
      }
    };

    onMounted(getFlights);

    const filteredFlights = computed(() => {
      return flights.value.filter(flight => {
        return (
          (!filters.value.destination || flight.destination.toLowerCase().includes(filters.value.destination.toLowerCase())) &&
          (!filters.value.departureTime || flight.departureTime.toLowerCase().includes(filters.value.departureTime.toLowerCase())) &&
          (!filters.value.arrivalTime || flight.arrivalTime.toLowerCase().includes(filters.value.arrivalTime.toLowerCase())) &&
          (!filters.value.price || flight.price.toString().includes(filters.value.price))
        );
      });
    });

    return {
      flights,
      filters,
      filteredFlights
    };
  }
};
</script>

<template>
  <h1>Flight Booking App</h1>

  <div id="search">
    <input v-model="filters.destination" type="text" placeholder="Destination">
    <input v-model="filters.departureTime" type="text" placeholder="Departure Time">
    <input v-model="filters.arrivalTime" type="text" placeholder="Arrival Time">
    <input v-model="filters.price" type="text" placeholder="Price">
  </div>

  <DataTable :value="filteredFlights" tableStyle="min-width: 50rem" @row-click="(event) => $router.push({ path: '/seating', query: { seats: event.data.availableSeats } })">
    <Column field="origin" header="Origin" sortable style="width: 16%"></Column>
    <Column field="destination" header="Destination" sortable style="width: 16%"></Column>
    <Column field="departureTime" header="Departure" sortable style="width: 16%"></Column>
    <Column field="arrivalTime" header="Arrival" sortable style="width: 16%"></Column>
    <Column field="price" header="Price" sortable style="width: 16%"></Column>
    <Column field="availableSeats" header="Seats Available" sortable style="width: 16%"></Column>
  </DataTable>
</template>

<style>
body {
  background: white;
  color: #181818;
}

#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #181818;
  background: white;
}

#search {
  padding: 20px;
  display: flex;
  gap: 10px;
}
</style>
