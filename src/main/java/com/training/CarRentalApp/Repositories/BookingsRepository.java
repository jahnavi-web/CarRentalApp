package com.training.CarRentalApp.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.training.CarRentalApp.Models.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long>
{
    @Query(value = "Select b.booking_id, u.user_id, c.car_id, b.from_date, b.to_date, b.total_amount from Bookings as b join Users as u on b.user_id = u.user_id Join Cars as c on b.car_id =  c.car_id", nativeQuery = true)
    List<Map<String, String>> getAllBookings();

	@Query("SELECT COUNT(b) > 0 FROM Bookings b WHERE b.car_id = :carId AND ((b.from_date <= :toDate AND b.to_date >= :fromDate))")
	boolean existsByCarIdAndDateRange(@Param("carId") Long carId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	@Query("SELECT COUNT(b) > 0 FROM Bookings b WHERE b.user_id = :userId AND ((b.from_date <= :toDate AND b.to_date >= :fromDate))")
	boolean existsByUserIdAndDateRange(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

}



