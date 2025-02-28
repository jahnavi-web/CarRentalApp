package com.training.CarRentalApp.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.training.CarRentalApp.Models.Cars;


public interface CarsRepository extends JpaRepository<Cars,Long>
{

    @Query(value = "select * from cars where car_id = ?1", nativeQuery = true)
    List<Map<String, String>> getCarById(Long id);

    @Query(value = "select * from cars where price <= ?1", nativeQuery = true)
    List<Map<String, String>> getCarByPrice(double price);

    @Query(value = "select * from Cars", nativeQuery = true)
    List<Map<String, String>> getAllCars();

    @Query(value = "SELECT * FROM Cars c WHERE NOT EXISTS " +
            "(SELECT 1 FROM Bookings b WHERE b.car_id = c.car_id " +
            "AND CURRENT_DATE BETWEEN b.from_date AND b.to_date)", nativeQuery = true)
    List<Map<String, String>> getAvailableCars();


}
