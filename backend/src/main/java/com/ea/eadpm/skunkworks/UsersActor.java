package com.ea.eadpm.skunkworks;

import cloud.orbit.actors.Actor;
import com.ea.eadpm.skunkworks.dto.UserDto;

import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Shiv on 7/22/2016.
 */
public class UsersActor extends AbstractActor implements Users {

    @Override
    public Task<Map<String, UserDto>> getUsers()
    {
        return Task.fromValue(UserActorList.getUserActors());
    }

    @Override
    public Task deleteUsers() {

        Object[] userArray = UserActorList.getUserActors().keySet().toArray();
        for (Object s: userArray) {
            Actor.getReference(User.class, s.toString()).deleteUser().join();
        }

        UserActorList.clearUsers();

        return Task.done();
    }
}
