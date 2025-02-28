package com.training.CarRentalApp.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.CarRentalApp.Models.Users;
import com.training.CarRentalApp.Repositories.UsersRepository;

@RestController
public class UsersController {
    
    @Autowired
    UsersRepository userRepo;
    
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    // Sign up
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody Users user) {
        try {
            if (user.getPhone() == null || !user.getPhone().toString().matches("\\d{10}")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number must be exactly 10 digits");
            }
            Users userObj = userRepo.save(user);
            return ResponseEntity.ok(userObj);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error registering user: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed");
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody Users user) {
        try {
            Map<String, String> response = new HashMap<>();
            List<Users> list = userRepo.userLogin(user.getEmail(), user.getPassword());
            
            if (list.isEmpty()) {
                response.put("Sign In", "Failed");
                response.put("User", "Not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Users foundUser = list.get(0);
            if (!user.getPassword().equals(foundUser.getPassword())) {
                response.put("Sign In", "Failed");
                response.put("User", "Invalid password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            response.put("Sign In", "Success");
            response.put("UserId", Long.toString(foundUser.getUser_id()));
            response.put("UserName", foundUser.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during login: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed");
        }
    }
    
    // Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}
