package com.training.CarRentalApp.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.training.CarRentalApp.Models.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long>
{
	@Query(value = "SELECT b.booking_id, u.user_id, c.car_id, c.car_name, c.fuel_type, c.num_seats, c.price_per_day, c.car_image, b.from_date, b.to_date, b.total_amount, b.status " +
            "FROM Bookings AS b " +
            "JOIN Users AS u ON b.user_id = u.user_id " +
            "JOIN Cars AS c ON b.car_id = c.car_id ", nativeQuery = true)
    List<Map<String, String>> getAllBookings();

    @Query("SELECT COUNT(b) > 0 FROM Bookings b WHERE b.car_id = :carId AND b.status = 'Active' AND ((b.from_date <= :toDate AND b.to_date >= :fromDate))")
    boolean existsByCarIdAndDateRange(@Param("carId") Long carId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("SELECT COUNT(b) > 0 FROM Bookings b WHERE b.user_id = :userId AND b.status = 'Active' AND ((b.from_date <= :toDate AND b.to_date >= :fromDate))")
    boolean existsByUserIdAndDateRange(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
    
    @Query(value = "SELECT b.booking_id, u.user_id, c.car_id, c.car_name, c.fuel_type, c.num_seats, c.price_per_day, c.car_image, b.from_date, b.to_date, b.total_amount, b.status " +
            "FROM Bookings AS b " +
            "JOIN Users AS u ON b.user_id = u.user_id " +
            "JOIN Cars AS c ON b.car_id = c.car_id " +
            "WHERE u.user_id = ?1", nativeQuery = true)
    List<Map<String, String>> getMyBookings(Long user_id);

}
