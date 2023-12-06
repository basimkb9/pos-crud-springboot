package com.possystem.possystem.service.implementation;

import com.possystem.possystem.domain.User;
import com.possystem.possystem.dto.UserDTO;
import com.possystem.possystem.repository.UserRepository;
import com.possystem.possystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public UserDTO toDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .role(user.getRole())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public User toDomain(UserDTO userDTO){
        if(userDTO.getId() == null){
            return User.builder()
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .role(userDTO.getRole())
                    .build();
        }
        else{
            return User.builder()
                    .id(userDTO.getId())
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .role(userDTO.getRole())
                    .build();
        }
    }

}
