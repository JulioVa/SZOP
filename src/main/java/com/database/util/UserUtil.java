package com.database.util;

import com.database.dto.UserDto;
import com.database.model.User;

public class UserUtil {

    public static User addUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setProfilePic(userDto.getProfilePic());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static User updateUser(User user, UserDto userUpdate) {
        user.setName(NotNullUtil.setNotNull(user.getName(), userUpdate.getName()));
        user.setProfilePic(NotNullUtil.setNotNull(user.getProfilePic(), userUpdate.getProfilePic()));
        user.setEmail(NotNullUtil.setNotNull(user.getEmail(), userUpdate.getEmail()));
        return user;
    }

    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setProfilePic(user.getProfilePic());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
