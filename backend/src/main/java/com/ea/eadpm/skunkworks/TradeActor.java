package com.ea.eadpm.skunkworks;

import com.ea.eadpm.skunkworks.dto.UserDto;

import cloud.orbit.actors.Actor;
import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;

import java.util.HashMap;
import java.util.Map;

import static com.ea.async.Async.await;

/**
 * Created by Shiv on 7/15/2016.
 */
public class TradeActor extends AbstractActor<TradeActor.State> implements Trade
{
    static class State {
        public String tradeId;
    }

    @Override
    public Task<Map<String, UserDto>> makeTrade(final String tradeId, final String username1, final String username2, final String requestTypeForUser1,
                               final String dataForUser1, final String requestTypeForUser2,
                               final String dataForUser2) {

        boolean user1Verified = false;
        boolean user2Verified = false;

        final User user1 = Actor.getReference(User.class, username1);
        final User user2 = Actor.getReference(User.class, username2);

        if (!user1.exists().join()) {

            throw new IllegalArgumentException("User1 does not exist");
        }

        if (!user2.exists().join()) {

            throw new IllegalArgumentException("User2 does not exist");
        }

        if (username1.equals(username2)) {
            throw new IllegalArgumentException("A User cannot trade with himself");
        }

        UserDto userDto1 = user1.getUser().join();
        UserDto userDto2 = user2.getUser().join();


        if (requestTypeForUser1.equals("currency")) {
            Double amount = Double.parseDouble(dataForUser1);
            if (userDto1.getBalance() >= amount) {
                user1Verified = true;
            }
        } else if (requestTypeForUser1.equals("item")) {
            if (userDto1.getItems().contains(dataForUser1)) {
                user1Verified = true;
            }
        } else {

            throw new IllegalArgumentException("Request Type for User1 invalid");
        }


        if (requestTypeForUser2.equals("currency")) {
            Double amount = Double.parseDouble(dataForUser2);
            if (userDto2.getBalance() >= amount) {
                user2Verified = true;
            }
        } else if (requestTypeForUser2.equals("item")) {
            if (userDto2.getItems().contains(dataForUser2)) {
                user2Verified = true;
            }
        } else {

            throw new IllegalArgumentException("Request Type for User2 invalid");
        }


        if (user1Verified && user2Verified) {

            if (requestTypeForUser1.equals("currency")) {
                Double amount = Double.parseDouble(dataForUser1);
                user1.makeTransaction("withdraw", amount);
            } else if (requestTypeForUser1.equals("item")) {
                user1.giveOrTakeItem(dataForUser1, "take");
            } else {
                throw new IllegalArgumentException("Request Type for User1 invalid");
            }


            if (requestTypeForUser2.equals("currency")) {
                Double amount = Double.parseDouble(dataForUser2);
                user2.makeTransaction("withdraw", amount);
            }
            else if (requestTypeForUser2.equals("item")) {
                user2.giveOrTakeItem(dataForUser2, "take");
            }
            else {
                throw new IllegalArgumentException("Request Type for User2 invalid");
            }


            if (requestTypeForUser1.equals("currency")) {
                Double amount = Double.parseDouble(dataForUser1);
                userDto2 = user2.makeTransaction("deposit", amount).join();
            }
            else if (requestTypeForUser1.equals("item")) {
                user2.giveOrTakeItem(dataForUser1, "give");
            }
            else {
                throw new IllegalArgumentException("Request Type for User1 invalid");
            }


            if (requestTypeForUser2.equals("currency")) {
                Double amount = Double.parseDouble(dataForUser2);
                userDto1 = user1.makeTransaction("deposit", amount).join();
            }
            else if (requestTypeForUser2.equals("item")) {
                user1.giveOrTakeItem(dataForUser2, "give");
            }
            else {
                throw new IllegalArgumentException("Request Type for User2 invalid");
            }

            state().tradeId = tradeId;
            await(writeState());

            Map<String, UserDto> userDtoMap = new HashMap<>();
            userDtoMap.put(username1, userDto1);
            userDtoMap.put(username2, userDto2);

            return Task.fromValue(userDtoMap);

        } else {

            throw new IllegalArgumentException("One or both of the Users did not have required items or balance");
        }
    }

}
