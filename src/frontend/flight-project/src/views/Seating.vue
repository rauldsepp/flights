<template>
  <div class="plane">
    <h2>Plane Seating Arrangement</h2>
    <input v-model.number="numSeatsToSelect" type="number" placeholder="No. of seats" :max="maxSeats" min="1">
    <button @click="selectSeats">Select</button>

    <!--creates a grid for the plane seats, for simplicity always the grid of 192 seats were created -->
    <!--however the middle columns 3 and 4 were created as 'aisle rows' meaning they are not shown on the grid-->
    <!--and the seats cannot be selected as available or taken -->
    <div class="seating-grid">
      <template v-for="(seat, index) in seats" :key="index">
        <div
          v-if="(index % 8) !== 3 && (index % 8) !== 4"
          :class="['seat', seat.taken ? 'taken' : (seat.selected ? 'selected' : 'available')]"
        >
          {{ Math.floor(index / 8) * 6 + (index % 8 < 3 ? index % 8 + 1 : index % 8 - 1) }}
        </div>
        <div v-else class="aisle"></div>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      seats: [],
      numSeatsToSelect: 1 //how many seat tickets are selected, default is 1 ticket
    };
  },
  computed: {
    seatsAvailable() {
      return parseInt(this.$route.query.seats) || 0;
    },
    maxSeats() {
      return this.seats.filter(seat => !seat.taken).length; // max selectable seats
    }
  },
  created() {
    this.seats = this.generateSeats();
  },
  methods: {
    generateSeats() {
      let seats = Array.from({ length: 192 }, () => ({ taken: false, selected: false }));
      let takenIndices = new Set();
      let takenSeatsCount = 192 - this.seatsAvailable;

      // selects (192 - available) number of seats as 'taken' the rest are the 'available' seats
      while (takenIndices.size < takenSeatsCount) {
        takenIndices.add(Math.floor(Math.random() * 192));
      }

      return seats.map((seat, index) => ({
        taken: takenIndices.has(index),
        selected: false
      }));
    },

    selectSeats() {
      this.seats.forEach(seat => seat.selected = false);
      let seatsToSelect = this.numSeatsToSelect;

      if (seatsToSelect > this.maxSeats) {
        alert("Not enough available seats");
        return;
      }

      let selectedSeats = 0;
      let row = 0;
      let col = 0;

      // seat recommendation, finds next available
      while (selectedSeats < seatsToSelect && row < 33) {
        for (col = 0; col < 6; col++) {
          let seatIdx = row * 8 + (col < 3 ? col : col + 2); // skipping aisle seats
          if (!this.seats[seatIdx]?.taken && !this.seats[seatIdx]?.selected) {
            this.seats[seatIdx].selected = true;
            selectedSeats++;
            if (selectedSeats === seatsToSelect) {
              return;
            }
          }
        }
        row++;
      }

      if (selectedSeats < seatsToSelect) {
        alert("Not enough available seats to select.");
      }
    },

    canSelectSeats(startIdx, count) {
      for (let i = 0; i < count; i++) {
        let seatIdx = startIdx + i;
        if ((seatIdx % 8 === 3 || seatIdx % 8 === 4) || this.seats[seatIdx]?.taken) {
          return false;
        }
      }
      return true;
    },

    markSeatsAsSelected(startIdx, count) {
      for (let i = 0; i < count; i++) {
        this.seats[startIdx + i].selected = true;
      }
    }
  }
};
</script>

<style scoped>
.plane {
  text-align: center;
  padding: 20px;
}

.seating-grid {
  display: grid;
  grid-template-columns: repeat(8, 50px);
  grid-template-rows: repeat(33, 50px);
  gap: 10px;
  justify-content: center;
  padding: 20px;
}

.aisle {
  width: 50px;
  height: 50px;
}

.seat {
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5px;
  font-weight: bold;
  color: white;
  cursor: pointer;
  transition: background 0.3s;
}

.taken {
  background-color: gray;
}

.available {
  background-color: blue;
}

.selected {
  background-color: yellow;
}
</style>
