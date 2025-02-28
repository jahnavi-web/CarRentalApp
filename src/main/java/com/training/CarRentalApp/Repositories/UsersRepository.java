package com.training.CarRentalApp.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.training.CarRentalApp.Models.Users;

public interface UsersRepository extends JpaRepository<Users,Long>
{
    @Query(value = "Select * from Users where email = ?1", nativeQuery = true)
    public List<Users> userLogin(String email, String password);

    @Query(value = "Select * from Users", nativeQuery = true)
    public List<Map<String, String>> viewAllUsers();
}