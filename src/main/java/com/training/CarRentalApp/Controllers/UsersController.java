package com.training.CarRentalApp.Controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.CarRentalApp.Models.Users;
import com.training.CarRentalApp.Repositories.UsersRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RestController
@CrossOrigin
public class UsersController {

    @Autowired
    UsersRepository userRepo;

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private static final String SECRET_KEY = "SecretKey";  // Change this in production

    // Generate JWT Token
    private String generateToken(Users user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getUser_id())
                .withClaim("userName", user.getName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiry
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody Users user) {
        try {
            String phonePattern = "^[6789]\\d{9}$";
            String emailPattern = "^[a-zA-Z][a-zA-Z0-9._%+-]{0,63}@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            if (user.getPhone() == null || !String.valueOf(user.getPhone()).matches(phonePattern)) {
                return ResponseEntity.badRequest().body("Phone number must be exactly 10 digits and start with 6, 7, 8, or 9");
            }

            if (user.getEmail() == null || !user.getEmail().matches(emailPattern)) {
                return ResponseEntity.badRequest().body("Invalid email format");
            }

            if (userRepo.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered! Please use a different email.");
            }

            Users savedUser = userRepo.save(user);
            return ResponseEntity.ok(savedUser);

        } catch (DataIntegrityViolationException e) {
            logger.error("Duplicate email detected: ", e);
            return ResponseEntity.badRequest().body("Email already exists!");
        } catch (Exception e) {
            logger.error("Error registering user: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed");
        }
    }

    // User Login with JWT
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody Users user) {
        try {
            Optional<Users> foundUser = userRepo.findByEmail(user.getEmail());

            if (foundUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            Users existingUser = foundUser.get();

            // Direct string comparison (Not Secure)
            if (!user.getPassword().equals(existingUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }

            // Generate Token
            String token = generateToken(existingUser);

            // Create response object
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("user_id", existingUser.getUser_id());
            response.put("name", existingUser.getName());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during login: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }
    
    @PostMapping("/userdashboard")
    public ResponseEntity<?> getUserDetails(@RequestParam Long user_id) {
        List<Object[]> userDetails = userRepo.usersDetails(user_id);

        if (userDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Object[] user = userDetails.get(0);
        Map<String, Object> response = new HashMap<>();
        response.put("user_id", user[0]);
        response.put("name", user[1]);
        response.put("email", user[2]);
     
        response.put("phone", user[3]);
  

        return ResponseEntity.ok(response);
    }


    // Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}
