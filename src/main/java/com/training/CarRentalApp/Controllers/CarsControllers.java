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

import com.training.CarRentalApp.Models.Cars;
import com.training.CarRentalApp.Repositories.CarsRepository;

@RestController
public class CarsControllers
{
    @Autowired
    CarsRepository carsRepo;

    @PostMapping("/addCar")
    public ResponseEntity<Cars> addCar(@RequestBody Cars car)
    {
        Cars cObj = carsRepo.save(car);
        return ResponseEntity.ok(cObj);

    }

    @PostMapping("/viewCarById")
    public ResponseEntity<List<Map<String,String>>> viewCarById(@RequestBody Cars car)
    {
        List<Map<String,String>> list = carsRepo.getCarById(car.getCar_id());
        return ResponseEntity.ok(list);
    }

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

    @GetMapping("/viewAvailableCars")
    public ResponseEntity<List<Map<String,String>>> viewAvailableCars()
    {
        List<Map<String,String>> list = carsRepo.getAvailableCars();
        return ResponseEntity.ok(list);
    }
}
