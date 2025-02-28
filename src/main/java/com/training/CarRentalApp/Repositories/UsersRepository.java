package com.training.CarRentalApp.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.CarRentalApp.Models.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // Securely query users by email
    @Query(value = "SELECT * FROM Users WHERE email = :email AND password = :password", nativeQuery = true)
    List<Users> userLogin(@Param("email") String email, @Param("password") String password);

    // Fetch all users without exposing passwords
    @Query(value = "SELECT user_id, name, email, aadhar, phone, profile_pic FROM Users", nativeQuery = true)
    List<Map<String, String>> viewAllUsers();
}
