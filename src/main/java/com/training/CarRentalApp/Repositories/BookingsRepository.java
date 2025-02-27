package com.training.CarRentalApp.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.training.CarRentalApp.Models.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long>
{
    @Query(value = "SELECT b.booking_id, u.username, c.car_name, b.from_date, b.to_date, b.total_amount FROM Bookings b JOIN Users u ON b.user_id = u.user_id JOIN Cars c ON b.car_id = c.car_id", nativeQuery = true)
    List<Map<String, String>> getAllBookings();
}