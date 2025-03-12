package com.training.CarRentalApp.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.CarRentalApp.Models.Cars;


public interface CarsRepository extends JpaRepository<Cars,Long>
{

    @Query(value = "select * from cars where car_id = ?1", nativeQuery = true)
    List<Map<String, String>> getCarById(Long id);

    @Query(value = "select * from cars where price_per_day <= ?1", nativeQuery = true)
    List<Map<String, String>> getCarByPrice(double price);

    @Query(value = "select * from cars", nativeQuery = true)
    List<Map<String, String>> getAllCars();

    @Query(value = "SELECT * FROM cars c WHERE NOT EXISTS " +
            "(SELECT * FROM Bookings b WHERE b.car_id = c.car_id " +
            "AND (:from_date BETWEEN b.from_date AND b.to_date " +
            "OR :to_date BETWEEN b.from_date AND b.to_date " +
            "OR b.from_date BETWEEN :from_date AND :to_date))", 
            nativeQuery = true)
    List<Map<String, String>> getAvailableCars(@Param("from_date") LocalDate fromDate, 
                                               @Param("to_date") LocalDate toDate);

    
  

}
