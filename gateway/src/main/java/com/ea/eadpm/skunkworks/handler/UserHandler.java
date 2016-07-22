package com.ea.eadpm.skunkworks.handler;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;

import com.ea.eadpm.skunkworks.User;
import com.ea.eadpm.skunkworks.Users;
import com.ea.eadpm.skunkworks.dto.AddItemDto;
import com.ea.eadpm.skunkworks.dto.AddTransactionDto;
import com.ea.eadpm.skunkworks.dto.AddUserDto;
import com.ea.eadpm.skunkworks.dto.TransactionDto;
import com.ea.eadpm.skunkworks.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.ea.async.Async.await;

/**
 * Created by Shiv on 6/1/2016.
 */

@Path("/users")
public class UserHandler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Task<Map<String, UserDto>> getUsers() {
        final Users users = Actor.getReference(Users.class, "UserActorRetriever");
        return users.getUsers();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Task<UserDto> addUser(final AddUserDto addUser) {
        final User user = Actor.getReference(User.class, addUser.getUsername());
        if (await(user.exists())) {
            throw new WebApplicationException("user already exists", Response.Status.CONFLICT);
        }
        return user.addUser(addUser.getUsername(), addUser.getBalance());
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<UserDto> getUser(@PathParam("username") final String username) {
        final User user = Actor.getReference(User.class, username);
        if (await(user.exists())) {
            return user.getUser();
        }
        throw new WebApplicationException("user does not exist", Response.Status.CONFLICT);
    }

    @DELETE
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<UserDto> deleteUser(@PathParam("username") final String username) {
        final User user = Actor.getReference(User.class, username);
        if (await(user.exists())) {
            return user.deleteUser();
        }
        throw new WebApplicationException("user does not exist", Response.Status.CONFLICT);
    }

    @POST
    @Path("/{username}/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<String> giveOrTakeItem(@PathParam("username") final String username, AddItemDto itemDto) {
        final User user = Actor.getReference(User.class, username);
        if (await(user.exists())) {
            return user.giveOrTakeItem(itemDto.getName(), itemDto.getCommand());
        }
        throw new WebApplicationException("user does not exist", Response.Status.CONFLICT);
    }

    @POST
    @Path("/{username}/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<TransactionDto> makeTransaction(@PathParam("username") final String username, AddTransactionDto transactionDto) {
        final User user = Actor.getReference(User.class, username);
        if (await(user.exists())) {
            return user.makeTransaction(transactionDto.getTransactionType(), transactionDto.getAmount());
        }
        throw new WebApplicationException("user does not exist", Response.Status.CONFLICT);
    }
}
