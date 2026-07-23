package com.AuthSystem.system.playLoad;

import com.AuthSystem.system.Entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegisterRequest {
    private String email;
    private String password;
    private String confirmPassword;
    private String Name;
    private String phoneNo;
}
