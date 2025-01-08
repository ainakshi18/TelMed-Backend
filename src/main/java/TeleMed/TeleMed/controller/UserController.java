package TeleMed.TeleMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import TeleMed.TeleMed.Models.User;
import TeleMed.TeleMed.auth.JwtUtil;
import TeleMed.TeleMed.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            User user = userService.findById(id);  // Fetch user by ID from the service
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            // Extract the token without the "Bearer " prefix
            token = token.startsWith("Bearer ") ? token.substring(7) : token;

            // Extract userId from JWT token
            String userId = jwtUtil.extractUserId(token);

            // Fetch user details by ID
            User userDetails = userService.findById(userId);
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Set user details from the JWT data
            user.setId(userDetails.getId());
            user.setEmail(userDetails.getEmail());
            user.setName(userDetails.getName());
            user.setUserRole(userDetails.getUserRole());

            // Create the new user in the system
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            // Extract the token without the "Bearer " prefix
            token = token.startsWith("Bearer ") ? token.substring(7) : token;

            // Extract the userId from JWT token
            String userId = jwtUtil.extractUserId(token);

            // Fetch existing user by ID
            User existingUser = userService.findById(userId);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Update user fields (e.g., address, latitude, longitude)
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setUserRole(user.getUserRole());

            // Save the updated user
            User updatedUser = userService.createUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }
}
