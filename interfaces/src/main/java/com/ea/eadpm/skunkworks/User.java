package com.ea.eadpm.skunkworks;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;

import com.ea.eadpm.skunkworks.dto.UserDto;


/**
 * Created by Shiv on 6/1/2016.
 */
public interface User extends Actor {

    Task<UserDto> addUser(String username, double balance);

    Task<UserDto> deleteUser();

    Task<Boolean> exists();

    Task<UserDto> getUser();

    Task<String> giveOrTakeItem(String item, String command);

    Task<UserDto> makeTransaction(String transactionType, double amount);

}
