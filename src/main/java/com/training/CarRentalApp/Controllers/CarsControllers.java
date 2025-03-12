package com.training.CarRentalApp.Controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.CarRentalApp.Models.Bookings;
import com.training.CarRentalApp.Models.Cars;
import com.training.CarRentalApp.Repositories.CarsRepository;

@RestController
@CrossOrigin
public class CarsControllers 
{
    @Autowired
    CarsRepository carsRepo;
    
    
    
    @PostMapping("/addCarsAll")
    public ResponseEntity<List<Cars>> addCarsAll(@RequestBody List<Cars> cars) {
        System.out.println(cars.toString());
        List<Cars> savedCars = carsRepo.saveAll(cars);
        return ResponseEntity.ok(savedCars);
    }
    
    
    

    @PostMapping("/addCar")
    public ResponseEntity<Cars> addCars(@RequestBody Cars car) {
        System.out.println(car.toString());
    	Cars carObj  = carsRepo.save(car);
        return ResponseEntity.ok(carObj);
    }
    
    
    
    @PostMapping("/viewCarById")
    public ResponseEntity<List<Map<String,String>>> viewCarById(@RequestBody Cars car)
    {
        List<Map<String,String>> list = carsRepo.getCarById(car.getCar_id());
        return ResponseEntity.ok(list);
    }
// View Cars by price range
    @PostMapping("/viewCarLessThanPrice")
    public ResponseEntity<List<Map<String,String>>> viewCarByPrice(@RequestBody Cars car)
    {
        List<Map<String,String>> list = carsRepo.getCarByPrice(car.getPrice_per_day());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/viewAllCars")
    public ResponseEntity<List<Map<String,String>>> viewAllCars()
    {
        List<Map<String,String>> list = carsRepo.getAllCars();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/viewAvailableCars")
    public ResponseEntity<List<Map<String, String>>> viewAvailableCars(
            @RequestBody Bookings booking) {

        List<Map<String, String>> list = carsRepo.getAvailableCars(booking.getFrom_date(), booking.getTo_date());
        return ResponseEntity.ok(list);
    }

}
