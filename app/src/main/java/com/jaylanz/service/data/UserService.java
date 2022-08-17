package com.jaylanz.service.data;

import com.jaylanz.domain.dto.UserDTO;

public interface UserService {
    UserDTO get(Long userId);
    UserDTO get(String username);
    UserDTO update(Long userId, String newUsername, String newPassword, String newEmailAddress);
}
