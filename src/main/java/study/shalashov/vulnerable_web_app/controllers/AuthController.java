package study.shalashov.vulnerable_web_app.controllers;

//import .orgspringframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import study.shalashov.vulnerable_web_app.dtos.JwtResponse;
import study.shalashov.vulnerable_web_app.dtos.LoginRequest;
import study.shalashov.vulnerable_web_app.dtos.SignupRequest;
import study.shalashov.vulnerable_web_app.misc.JwtUtils;
import study.shalashov.vulnerable_web_app.models.User;
import study.shalashov.vulnerable_web_app.repositories.UserRepository;
import study.shalashov.vulnerable_web_app.services.MyUserDetailsService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final MyUserDetailsService myUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(JwtResponse.builder().token(jwt).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (myUserDetailsService.userExistsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        User user = User.builder()
                        .username(signupRequest.getUsername())
                        .password(encoder.encode(signupRequest.getPassword()))
                        .build();
        myUserDetailsService.createUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
