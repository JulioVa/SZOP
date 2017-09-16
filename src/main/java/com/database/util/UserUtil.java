package com.database.util;

import com.database.dto.UserDto;
import com.database.model.User;

public class UserUtil {

    public static User addUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setMail(userDto.getMail());
        return user;
    }

    public static User updateUser(User user, UserDto userUpdate) {
        user.setName(NotNullUtil.setNotNull(user.getName(), userUpdate.getName()));
        user.setPassword(NotNullUtil.setNotNull(user.getPassword(), userUpdate.getPassword()));
        user.setMail(NotNullUtil.setNotNull(user.getMail(), userUpdate.getMail()));
        return user;
    }

    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setMail(user.getMail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
