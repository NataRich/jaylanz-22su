package com.jaylanz.web.controller;

import com.jaylanz.common.validation.constraint.EmailAddress;
import com.jaylanz.common.validation.constraint.Username;
import com.jaylanz.domain.dto.UserDTO;
import com.jaylanz.domain.vo.BaseResponse;
import com.jaylanz.domain.vo.UserVO;
import com.jaylanz.service.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserVO>> getMe() {
        try {
            UserDTO dto = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserVO vo = new UserVO(dto.getId(), dto.getUsername(), dto.getEmailAddress());
            return new ResponseEntity<>(BaseResponse.OK(vo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/me")
    public ResponseEntity<BaseResponse<UserVO>> updateMe(@Valid @RequestBody UserParam param) {
        try {
            System.out.println(param);
            UserDTO dto = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            dto = userService.update(dto.getId(), param.newUsername, param.newPassword, param.newEmailAddress);
            if (dto != null) {
                UserVO vo = new UserVO(dto.getId(), dto.getUsername(), dto.getEmailAddress());
                return new ResponseEntity<>(BaseResponse.OK(vo), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private static class UserParam {
        @Username
        private String newUsername;
        private String newPassword;
        @EmailAddress
        private String newEmailAddress;

        public String getNewUsername() {
            return newUsername;
        }

        public void setNewUsername(String newUsername) {
            this.newUsername = newUsername;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getNewEmailAddress() {
            return newEmailAddress;
        }

        public void setNewEmailAddress(String newEmailAddress) {
            this.newEmailAddress = newEmailAddress;
        }

        @Override
        public String toString() {
            return "UserParam{" +
                    "newUsername='" + newUsername + '\'' +
                    ", newPassword='" + newPassword + '\'' +
                    ", newEmailAddress='" + newEmailAddress + '\'' +
                    '}';
        }
    }
}
