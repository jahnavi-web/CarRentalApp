package com.training.CarRentalApp.Controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.CarRentalApp.Models.Bookings;
import com.training.CarRentalApp.Models.Cars;
import com.training.CarRentalApp.Repositories.BookingsRepository;
import com.training.CarRentalApp.Repositories.CarsRepository;

@RestController
public class BookingsController {
    
    @Autowired
    BookingsRepository bookingsRepo;
    
    @Autowired
    CarsRepository carsRepo;

    // Create Booking with Date-Based Availability
    @PostMapping("/createBooking")
    public ResponseEntity<Map<String, String>> createBooking(@RequestBody Bookings booking) {
        Optional<Cars> optionalCar = carsRepo.findById(booking.getCar_id());

        Map<String, String> res = new HashMap<>();
        if (!optionalCar.isPresent()) {
            res.put("car", "not available");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        Cars car = optionalCar.get();
        LocalDate fromDate = booking.getFrom_date();
        LocalDate toDate = booking.getTo_date();
        int days = (int) (1 + ChronoUnit.DAYS.between(fromDate, toDate));

        if (days <= 0) {
            res.put("Date", "Invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        // Check for overlapping dates
        if (isCarBooked(car, fromDate, toDate)) {
            res.put("car", "already booked for selected dates");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        // Calculate total price
        double totalAmount = days * car.getPrice_per_day();
        booking.setTotal_amount(totalAmount);

        // Save the booking
        bookingsRepo.save(booking);

        // Block the car for the booking dates
        car.addBooking(fromDate, days);
        carsRepo.save(car);

        res.put("Booking", "Success");
        res.put("Total amount", totalAmount + "/-");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    private boolean isCarBooked(Cars car, LocalDate fromDate, LocalDate toDate) {
        for (Map.Entry<LocalDate, Integer> entry : car.getAvailability().entrySet()) {
            LocalDate bookedStart = entry.getKey();
            LocalDate bookedEnd = bookedStart.plusDays(entry.getValue() - 1);

            // Check if new booking overlaps with existing one
            if (!(toDate.isBefore(bookedStart) || fromDate.isAfter(bookedEnd))) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/viewAllBooking")
    public ResponseEntity<List<Map<String, String>>> viewAllCars() {
        List<Map<String, String>> list = bookingsRepo.getAllBookings();
        return ResponseEntity.ok(list);
    }
}
