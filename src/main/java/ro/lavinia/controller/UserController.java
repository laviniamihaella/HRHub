package ro.lavinia.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.UserDto;
import ro.lavinia.service.UserServiceImpl;
import ro.lavinia.swagger.UserSwagger;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController implements UserSwagger {

    private final UserServiceImpl userServiceImpl;
    
    @Operation(summary = "Save a new user")
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto ){
        return userServiceImpl.save(userDto);
    }

    @Operation(summary = "Get a user by Id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?>  getById(@PathVariable("id") Integer existingId) {
        return userServiceImpl.getAnUserById(existingId);
    }

    @Operation(summary = "Get all user.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }


    @Operation(summary = "Update user with patch.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateUserWithPatch(
            @RequestBody Map<String, Object> updatedUser,
            @PathVariable("id") Integer existingId) {
        return userServiceImpl.updateUserWithPatch(existingId, updatedUser);
    }

    @Operation(summary = "Update user with put.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateUserWithPut(
            @RequestBody UserDto updatedUser ,
            @PathVariable("id") Integer existingId) {
        return userServiceImpl.updateUserWithPut(existingId, updatedUser);
    }

    @Operation(summary = "Delete the user by id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return userServiceImpl.deleteById(id);
    }


}
