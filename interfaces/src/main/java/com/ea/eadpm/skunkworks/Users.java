package com.ea.eadpm.skunkworks;

import com.ea.eadpm.skunkworks.dto.UserDto;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;


import java.util.Map;

/**
 * Created by Shiv on 7/22/2016.
 */
public interface Users extends Actor {

    Task<Map<String, UserDto>> getUsers();

    Task deleteUsers();

}
