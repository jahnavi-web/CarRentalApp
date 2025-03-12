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
import org.springframework.web.bind.annotation.*;

import com.training.CarRentalApp.Models.Bookings;
import com.training.CarRentalApp.Models.Cars;
import com.training.CarRentalApp.Repositories.BookingsRepository;
import com.training.CarRentalApp.Repositories.CarsRepository;

@RestController
@CrossOrigin
public class BookingsController {
    
    @Autowired
    BookingsRepository bookingsRepo;
    
    @Autowired
    CarsRepository carsRepo;

    // Create a new booking
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

        // Check if the car is already booked for the selected dates (only Active bookings)
        boolean isCarBooked = bookingsRepo.existsByCarIdAndDateRange(booking.getCar_id(), fromDate, toDate);
        if (isCarBooked) {
            res.put("car", "already booked for selected dates");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        // Check if the user has already booked another car for the same dates (only Active bookings)
        boolean isUserBooked = bookingsRepo.existsByUserIdAndDateRange(booking.getUser_id(), fromDate, toDate);
        if (isUserBooked) {
            res.put("user", "already booked another car for selected dates");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        // Calculate total price
        double totalAmount = days * car.getPrice_per_day();
        booking.setTotal_amount(totalAmount);
        booking.setStatus("Active");

        // Save the booking
        bookingsRepo.save(booking);

        res.put("Booking", "Success");
        res.put("Total amount", totalAmount + "/-");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // Cancel a booking
    @PostMapping("/cancelBooking")
    public ResponseEntity<Map<String, String>> cancelBooking(@RequestBody Bookings bookingRequest) {
        Optional<Bookings> optionalBooking = bookingsRepo.findById(bookingRequest.getBooking_id());
        Map<String, String> res = new HashMap<>();

        if (!optionalBooking.isPresent()) {
            res.put("Booking", "Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        Bookings booking = optionalBooking.get();
        if (booking.getStatus().equals("Cancelled")) {
            res.put("Booking", "Already Cancelled");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        booking.setStatus("Cancelled");
        bookingsRepo.save(booking);

        res.put("Booking", "Cancelled Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getAllBookings")
    public ResponseEntity<List<Map<String, String>>> getAllBookings() {
        return ResponseEntity.ok(bookingsRepo.getAllBookings());
    }

  
    @PostMapping("/getMyBookings")
    public ResponseEntity<List<Map<String, String>>> getMyBookings(@RequestParam Long user_id) {
        return ResponseEntity.ok(bookingsRepo.getMyBookings(user_id));
    }
}
