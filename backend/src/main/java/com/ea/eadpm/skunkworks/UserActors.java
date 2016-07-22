package com.ea.eadpm.skunkworks;

import com.ea.eadpm.skunkworks.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shiv on 7/22/2016.
 */
public final class UserActors {
    private static final Map<String, UserDto> userActors = new HashMap<>();

    public static Map<String, UserDto> getUserActors() {
        return userActors;
    }

}
