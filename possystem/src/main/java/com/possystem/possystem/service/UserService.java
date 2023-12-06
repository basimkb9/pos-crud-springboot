package com.possystem.possystem.service;

import com.possystem.possystem.domain.User;
import com.possystem.possystem.dto.UserDTO;

public interface UserService {
    User toDomain(UserDTO userDTO);

    UserDTO toDto(User user);
}
