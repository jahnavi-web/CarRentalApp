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
	
}