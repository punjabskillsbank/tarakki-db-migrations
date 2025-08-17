package com.tarakki.workspace.controller;

import com.tarakki.workspace.entity.User;
import com.tarakki.workspace.entity.UserRole;
import com.tarakki.workspace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

  
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(required = false, defaultValue = "false") boolean activeOnly) {
        List<User> users = activeOnly ? userService.getAllActiveUsers() : userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

   
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

  
    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

   
    @GetMapping("/by-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(
            @PathVariable UserRole role,
            @RequestParam(required = false, defaultValue = "false") boolean activeOnly) {
        List<User> users = activeOnly ? 
                userService.getActiveUsersByRole(role) : 
                userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

   
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name) {
        List<User> users = userService.searchActiveUsersByName(name);
        return ResponseEntity.ok(users);
    }

   
    @GetMapping("/stats")
    public ResponseEntity<UserStats> getUserStats() {
        UserStats stats = new UserStats();
        stats.setTotalUsers(userService.getUserCount());
        stats.setActiveUsers(userService.getActiveUserCount());
        stats.setInactiveUsers(stats.getTotalUsers() - stats.getActiveUsers());
        return ResponseEntity.ok(stats);
    }

    
    @GetMapping("/{userId}/is-active")
    public ResponseEntity<Boolean> isUserActive(@PathVariable UUID userId) {
        boolean isActive = userService.isUserActiveById(userId);
        return ResponseEntity.ok(isActive);
    }

    public static class UserStats {
        private long totalUsers;
        private long activeUsers;
        private long inactiveUsers;

       
        public long getTotalUsers() {
            return totalUsers;
        }

        public void setTotalUsers(long totalUsers) {
            this.totalUsers = totalUsers;
        }

        public long getActiveUsers() {
            return activeUsers;
        }

        public void setActiveUsers(long activeUsers) {
            this.activeUsers = activeUsers;
        }

        public long getInactiveUsers() {
            return inactiveUsers;
        }

        public void setInactiveUsers(long inactiveUsers) {
            this.inactiveUsers = inactiveUsers;
        }
    }
}
