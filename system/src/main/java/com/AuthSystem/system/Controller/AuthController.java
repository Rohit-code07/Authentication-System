package com.AuthSystem.system.Controller;

import com.AuthSystem.system.DTO.UserDTO;
import com.AuthSystem.system.Entity.Role;
import com.AuthSystem.system.Entity.User;
import com.AuthSystem.system.Security.CustomUserDetail;
import com.AuthSystem.system.Security.CustomUserDetailService;
import com.AuthSystem.system.Security.JwtService;
import com.AuthSystem.system.Services.UserService;
import com.AuthSystem.system.playLoad.*;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;
        private final UserService userService;
        private final CustomUserDetailService customuserDetailsService;
        private final JwtService jwtService;

        public AuthController(
                AuthenticationManager authenticationManager,
                PasswordEncoder passwordEncoder,
                UserService userService,
              CustomUserDetailService customuserDetailsService,
                JwtService jwtService) {

            this.authenticationManager = authenticationManager;
            this.passwordEncoder = passwordEncoder;
            this.userService = userService;
            this.jwtService = jwtService;
            this.customuserDetailsService = customuserDetailsService;
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
        UserDetails userDetails = customuserDetailsService.loadUserByUsername(loginRequest.getEmail());
        UserDTO userDTO = userDetailsToUserDTO(userDetails);
        String jwtToken = jwtService.generateToken(userDTO.getEmail(),true);
        String refreshtoken = jwtService.generateToken(userDTO.getEmail(),false);

        JwtResponse reponse= JwtResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshtoken)
                .user(userDTO)
                .build();
        return ResponseEntity.ok(reponse);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody refreshtokenRequest refreshtokenRequest1) {
            if(jwtService.validateToken(refreshtokenRequest1.getRefreshtoken())){

                String username=jwtService.getemail(refreshtokenRequest1.getRefreshtoken());
                String accesstoken=jwtService.generateToken(username,true);
                String newRefreshtoken=jwtService.generateToken(username,false);
                UserDetails userDetails = customuserDetailsService.loadUserByUsername(username);
                UserDTO userDTO = userDetailsToUserDTO(userDetails);
                JwtResponse response=JwtResponse.builder()
                        .accessToken(accesstoken)
                        .refreshToken(newRefreshtoken)
                        .user(userDTO)
                        .build();
                return ResponseEntity.ok(response);

            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

    }
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> allUser(){
            System.out.println("allUser");
        return ResponseEntity.ok(userService.findAll());
    }
    public UserDTO userDetailsToUserDTO(UserDetails userDetails) {

        CustomUserDetail custom = (CustomUserDetail) userDetails;

        User user = custom.getUser();

        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setEnable(user.isEnable());
        dto.setRole(user.getRole());
        dto.setPhoneNo(user.getPhoneNo());

        return dto;
    }
}
