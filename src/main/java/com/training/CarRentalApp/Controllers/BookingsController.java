package com.training.CarRentalApp.Controllers;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class BookingsController 
{
	@Autowired
	BookingsRepository bookingsRepo;
	
	@Autowired
    CarsRepository carsRepo;
	

	
	
	@PostMapping("/createBooking")
	public ResponseEntity<Map<String, String>> createBooking(@RequestBody Bookings booking) {
	    List<Map<String, String>> c = carsRepo.getCarById(booking.getCar_id());

	    Map<String, String> res = new HashMap<>();
	    if (c.isEmpty()) {
	        res.put("car", "not available");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	    }

	    double pricePerDay;
	    try {
	        pricePerDay = Double.parseDouble(String.valueOf(c.get(0).get("price_per_day")));
	    } catch (NumberFormatException e) {
	        res.put("error", "Invalid price format");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	    }

	    long days = 1 + ChronoUnit.DAYS.between(booking.getFrom_date(), booking.getTo_date());
	    if (days <= 0) {
	        res.put("Date", "Invalid");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	    }
	    
	    double totalAmount = (days) * pricePerDay;
	    booking.setTotal_amount(totalAmount);

	    Bookings savedBooking = bookingsRepo.save(booking);
	    res.put("Booking", "Success");
	    res.put("Total amount", totalAmount + "/-");
	    return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}



	@GetMapping("/viewAllBooking")
	public ResponseEntity<List<Map<String,String>>> viewAllCars()
	{
		List<Map<String,String>> list = bookingsRepo.getAllBookings();
		return ResponseEntity.ok(list);
	}
	
}

