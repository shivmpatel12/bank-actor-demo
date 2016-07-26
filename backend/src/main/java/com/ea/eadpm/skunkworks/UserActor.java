package com.ea.eadpm.skunkworks;

import cloud.orbit.actors.runtime.AbstractActor;

import cloud.orbit.concurrent.Task;
import com.ea.eadpm.skunkworks.dto.TransactionDto;
import com.ea.eadpm.skunkworks.dto.UserDto;


import java.util.ArrayList;
import java.util.List;

import static com.ea.async.Async.await;


/**
 * Created by Shiv on 6/1/2016.
 */
public class UserActor extends AbstractActor<UserActor.State> implements User {

    static class State {
        public UserDto userDto;
        public boolean exists = false;
    }

    @Override
    public Task<UserDto> addUser(String username, double balance) {
        state().exists = true;

        UserDto userDto = new UserDto();
        userDto.setBalance(balance);
        userDto.setUsername(username);
        userDto.setTransactionsForUser(new ArrayList<>());
        userDto.setItems(new ArrayList<>());

        state().userDto = userDto;
        await(writeState());
        UserActorList.getUserActors().put(username, userDto);

        return Task.fromValue(state().userDto);
    }


    @Override
    public Task<Boolean> exists() {
        return Task.fromValue(state().exists);
    }

    @Override
    public Task<UserDto> getUser() {
        final UserDto userDto = state().userDto;
        return Task.fromValue(userDto);
    }

    @Override
    public Task activateAsync()
    {
        if (state().userDto != null) {
            System.out.println("activated");
        }
        return super.activateAsync();
    }

    @Override
    public Task deactivateAsync()
    {
        if (state().userDto != null) {
            System.out.println("deactivated");
        }
        return super.deactivateAsync();
    }

    @Override
    public Task<String> giveOrTakeItem(final String item, final String command)
    {
        final UserDto userDto = state().userDto;
        final List<String> items = userDto.getItems();
        if (command.equals("give")) {
            items.add(item);
            userDto.setItems(items);
            await(writeState());
            UserActorList.getUserActors().put(userDto.getUsername(), userDto);
            return Task.fromValue(item);
        } else if (command.equals("take")) {
            if (item.contains(item)) {
                items.remove(item);
                userDto.setItems(items);
                await(writeState());
                UserActorList.getUserActors().put(userDto.getUsername(), userDto);
                return Task.fromValue(item);
            } else {
                throw new IllegalArgumentException("user does not have that item");
            }
        }
        else {
            throw new IllegalArgumentException("command not recognized");
        }
    }

    @Override
    public Task<UserDto> makeTransaction(final String transactionType, final double amount)
    {
        final UserDto userDto = state().userDto;
        List<TransactionDto> transactions = userDto.getTransactionsForUser();
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(amount);
        transactionDto.setTransactionType(transactionType);
        if (transactionType.equals("deposit")) {
            userDto.setBalance(userDto.getBalance() + amount);
            transactions.add(transactionDto);
            userDto.setTransactionsForUser(transactions);
            await(writeState());
            UserActorList.getUserActors().put(userDto.getUsername(), userDto);
            return Task.fromValue(userDto);
        } else if (transactionType.equals("withdraw")) {
            if (userDto.getBalance() >=  amount) {
                userDto.setBalance(userDto.getBalance() - amount);
                transactions.add(transactionDto);
                userDto.setTransactionsForUser(transactions);
                await(writeState());
                UserActorList.getUserActors().put(userDto.getUsername(), userDto);
                return Task.fromValue(userDto);
            } else {
                throw new IllegalArgumentException("user has insufficient funds");
            }
        }
        throw new IllegalArgumentException("transactionType not recognized");
    }

    @Override
    public Task<UserDto> deleteUser()
    {
        final UserDto userDto = state().userDto;
        clearState();
        UserActorList.getUserActors().remove(userDto.getUsername());
        return Task.fromValue(userDto);
    }

}
