package com.ea.eadpm.skunkworks;

import com.ea.eadpm.skunkworks.dto.UserDto;

import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;

import java.util.Map;

/**
 * Created by Shiv on 7/22/2016.
 */
public class UsersActor extends AbstractActor implements Users {

    @Override
    public Task<Map<String, UserDto>> getUsers()
    {
        return Task.fromValue(UserActors.getUserActors());
    }
}
