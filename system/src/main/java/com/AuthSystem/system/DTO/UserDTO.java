package com.AuthSystem.system.DTO;

import com.AuthSystem.system.Entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDTO {
    private String email;
    private String password;
    private String Name;
    private boolean isEnable;
    private Role Role;
    private String phoneNo;
}
