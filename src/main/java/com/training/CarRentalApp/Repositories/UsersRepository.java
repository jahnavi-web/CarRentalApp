package com.training.CarRentalApp.Repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.CarRentalApp.Models.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // Securely query users by email
    @Query(value = "SELECT * FROM Users WHERE email = :email", nativeQuery = true)
    List<Users> userLogin(@Param("email") String email, String password);

    // Fetch all users without exposing passwords
    @Query(value = "SELECT user_id, name, email, phone FROM Users", nativeQuery = true)
    List<Map<String, String>> viewAllUsers();
    
    @Query(value = "SELECT user_id, name, email, phone FROM Users where user_id = ?1", nativeQuery = true)
    List<Object[]> usersDetails(Long user_id);
    

    
    Optional<Users> findByEmail(String email);
    
}
