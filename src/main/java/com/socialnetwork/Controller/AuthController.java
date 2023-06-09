package com.socialnetwork.Controller;


import com.socialnetwork.Infrastucture.Dto.UserDto;
import com.socialnetwork.Infrastucture.Request.User.LoginRequest;
import com.socialnetwork.Infrastucture.Request.User.SignupRequest;
import com.socialnetwork.Infrastucture.Response.jwtResponse.JwtResponse;
import com.socialnetwork.Infrastucture.Response.jwtResponse.MessageResponse;
import com.socialnetwork.Infrastucture.Response.UserResponse;
import com.socialnetwork.Security.Jwt.JwtUtils;
import com.socialnetwork.Security.Services.UserDetailsImpl;
import com.socialnetwork.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),
                userDetails.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            UserDto userDto = UserDto.fromRequest(signUpRequest);
            userService.register(userDto);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (
                DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error occurred while registrering the user");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.userId(id);
        return ResponseEntity.ok(UserDto.fromResponse(userDto));
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserDto> userDtoList = userService.getAll();
        return ResponseEntity.ok( userDtoList.stream().map( UserDto::fromResponse ).collect(Collectors.toList()));
    }

}