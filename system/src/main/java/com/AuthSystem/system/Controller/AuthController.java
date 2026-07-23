package com.AuthSystem.system.Controller;

import com.AuthSystem.system.DTO.UserDTO;
import com.AuthSystem.system.Entity.Role;
import com.AuthSystem.system.Security.JwtService;
import com.AuthSystem.system.Services.UserService;
import com.AuthSystem.system.playLoad.ApiResponse;
import com.AuthSystem.system.playLoad.JwtResponse;
import com.AuthSystem.system.playLoad.LoginRequest;
import com.AuthSystem.system.playLoad.UserRegisterRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;
        private final UserService userService;
        private final ModelMapper modelMapper;
        private final UserDetailsService userDetailsService;
        private final JwtService jwtService;

        public AuthController(
                AuthenticationManager authenticationManager,
                PasswordEncoder passwordEncoder,
                UserService userService,
                ModelMapper modelMapper,
                UserDetailsService userDetailsService,
                JwtService jwtService) {

            this.authenticationManager = authenticationManager;
            this.passwordEncoder = passwordEncoder;
            this.userService = userService;
            this.modelMapper = modelMapper;
            this.userDetailsService = userDetailsService;
            this.jwtService = jwtService;
        }



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRegisterData) {

        if (!userRegisterData.getPassword().equals(userRegisterData.getConfirmPassword())) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("confirm password does not match")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        //
        UserDTO userDto = new UserDTO();
        userDto.setName(userRegisterData.getName());
        userDto.setEmail(userRegisterData.getEmail());
        userDto.setPassword(passwordEncoder.encode(userRegisterData.getPassword()));
        userDto.setRole(Role.ROLE_USER);
        userDto.setEnable(true);
        UserDTO createdUser = userService.Save(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);
        String jwtToken = jwtService.generateToken(userDTO.getEmail());
        JwtResponse reponse= JwtResponse.builder()
                .accessToken(jwtToken)
                .user(userDTO)
                .build();
        return ResponseEntity.ok(reponse);
    }
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> allUser(){
        return ResponseEntity.ok(userService.findAll());
    }
}
