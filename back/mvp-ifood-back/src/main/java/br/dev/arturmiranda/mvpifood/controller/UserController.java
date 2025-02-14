package br.dev.arturmiranda.mvpifood.controller;

import br.dev.arturmiranda.mvpifood.dto.user.UserDTO;
import br.dev.arturmiranda.mvpifood.dto.user.UserRegisterDTO;
import br.dev.arturmiranda.mvpifood.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User Controller for the application")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user in the application")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/user/{email}")
    @Operation(summary = "Get user by email", description = "Get user by email in the application")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
