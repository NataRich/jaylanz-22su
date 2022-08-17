package com.jaylanz.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jaylanz.domain.dto.UserDTO;
import com.jaylanz.domain.dto.repeository.UserRepository;
import com.jaylanz.domain.po.UserPO;
import com.jaylanz.domain.po.mapper.UserMapper;
import com.jaylanz.service.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO get(Long userId) {
        Optional<UserDTO> user = userRepository.findById(userId);
        return user.orElseGet(() -> fetch(userId));
    }

    @Override
    public UserDTO get(String username) {
        Optional<UserDTO> user = userRepository.findByUsername(username);
        return user.orElseGet(() -> fetch(username));
    }

    @Override
    public UserDTO update(Long userId, String newUsername, String newPassword, String newEmailAddress) {
        UserPO po = userMapper.selectById(userId);
        if (po != null) {
            if (newUsername != null)
                po.setUsername(newUsername);
            if (newPassword != null)
                po.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            if (newEmailAddress != null)
                po.setEmailAddress(newEmailAddress);
            if (userMapper.updateById(po) > 0) {
                UserDTO dto = new UserDTO(po.getId(), po.getUsername(), po.getPassword(), po.getEmailAddress());
                userRepository.save(dto);
                return dto;
            }
        }
        return null;
    }

    private UserDTO fetch(Long userId) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userId);
        UserPO po = userMapper.selectOne(wrapper);
        if (po != null) {
            UserDTO dto = new UserDTO(po.getId(), po.getUsername(), po.getPassword(), po.getEmailAddress());
            userRepository.save(dto);
            return dto;
        }
        return null;
    }

    private UserDTO fetch(String username) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        UserPO po = userMapper.selectOne(wrapper);
        if (po != null) {
            UserDTO dto = new UserDTO(po.getId(), po.getUsername(), po.getPassword(), po.getEmailAddress());
            userRepository.save(dto);
            return dto;
        }
        return null;
    }
}
