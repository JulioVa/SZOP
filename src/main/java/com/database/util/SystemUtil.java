package com.database.util;

import com.database.dto.SystemDto;
import com.database.model.System;
import com.database.model.User;
import com.database.service.UserService;

public class SystemUtil {

    public static System addSystem(SystemDto systemDto) {
        User user = UserService.findUserById(systemDto.getUserId());
        if (user != null) {
            System system = new System();
            system.setUserId(user);
            system.setName(systemDto.getName());
            return system;
        } else {
            return null;
        }
    }

    public static System updateSystem(System system, SystemDto systemUpdate) {
        system.setName(NotNullUtil.setNotNull(system.getName(), systemUpdate.getName()));
        return system;
    }
}
