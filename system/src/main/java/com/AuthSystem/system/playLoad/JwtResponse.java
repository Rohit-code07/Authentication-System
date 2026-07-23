package com.AuthSystem.system.playLoad;

import com.AuthSystem.system.DTO.UserDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

    private  String accessToken;
    private UserDTO user;
}
