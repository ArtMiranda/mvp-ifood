package br.dev.arturmiranda.mvpifood.controller;

import br.dev.arturmiranda.mvpifood.dto.auth.AuthenticationResponse;
import br.dev.arturmiranda.mvpifood.dto.auth.LoginDTO;
import br.dev.arturmiranda.mvpifood.dto.user.UserDTO;
import br.dev.arturmiranda.mvpifood.service.UserService;
import br.dev.arturmiranda.mvpifood.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication Controller for the application")
public class AuthController {

   private final AuthenticationManager authenticationManager;
   private final JWTUtil jwtTokenProvider;
   private final UserService userService;

   @PostMapping("/login")
   @Operation(summary = "Login", description = "Login in the application - returns a JWT token")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDTO loginDTO) {
         try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
             UserDTO authenticatedUser = userService.findByEmail(loginDTO.getEmail())
                     .orElseThrow(() -> new UsernameNotFoundException("User not found"));
             String token = jwtTokenProvider.generateToken(authenticatedUser);
             return ResponseEntity.ok(new AuthenticationResponse(token));
         } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
         }
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate token", description = "Validate a JWT token")
    public ResponseEntity<Boolean> validateToken(@RequestBody String token) {
        return ResponseEntity.ok(jwtTokenProvider.validateToken(token));
    }

}
