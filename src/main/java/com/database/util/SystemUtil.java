package com.database.util;

import com.database.dto.SystemDto;
import com.database.model.System;
import com.database.model.User;
import com.database.service.UserService;

import java.util.ArrayList;
import java.util.List;

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

    public static SystemDto convertToDto(System system) {
        SystemDto systemDto = new SystemDto();
        systemDto.setName(system.getName());
        systemDto.setUserId(system.getUserId().getId());
        return systemDto;
    }

    public static List<SystemDto> convertToDtos(List<System> systems) {
        List<SystemDto> systemDtos = new ArrayList<>();
        for (System system : systems) {
            systemDtos.add(convertToDto(system));
        }
        return systemDtos;
    }
}
