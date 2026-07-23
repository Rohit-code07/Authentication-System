package com.AuthSystem.system.Services;

import com.AuthSystem.system.DTO.UserDTO;
import com.AuthSystem.system.Entity.User;
import com.AuthSystem.system.Repositries.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepo userRepo;
    public ModelMapper modelMapper;

    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public UserDTO Save(UserDTO userdto){
        userRepo.save(modelMapper.map(userdto,User.class));
        return userdto;
    }
    public User Update (UserDTO userdto){
       return userRepo.save(modelMapper.map(userdto,User.class));
    }
    public List<UserDTO> findAll(){

             List<User> users = userRepo.findAll();

             return users.stream()
                     .map(user -> modelMapper.map(user, UserDTO.class))
                     .toList();

     }

}
