package com.ea.eadpm.skunkworks;

import com.ea.eadpm.skunkworks.dto.TradeDto;
import com.ea.eadpm.skunkworks.dto.UserDto;

import org.graphstream.graph.Node;

import cloud.orbit.actors.Actor;
import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;

import java.awt.*;
import java.util.concurrent.TimeUnit;

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
    public Task<TradeDto> makeTrade(final String tradeId, final String username1, final String username2, final String requestTypeForUser1,
                                    final String dataForUser1, final String requestTypeForUser2,
                                    final String dataForUser2) {

//        Node node = ActorGraph.getGraph().addNode(tradeId);
//        node.addAttribute("ui.label", "TradeActor");
//        node.addAttribute("ui.style", "fill-color: rgb(0,255,100);");

        boolean user1Verified = false;
        boolean user2Verified = false;

        final User user1 = Actor.getReference(User.class, username1);
        final User user2 = Actor.getReference(User.class, username2);

        if (!user1.exists().join()) {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (Exception e) {
//                System.out.println("waiting exception");
//            }
//            ActorGraph.getGraph().removeNode(tradeId);
            throw new IllegalArgumentException("User1 does not exist");
        }

        if (!user2.exists().join()) {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (Exception e) {
//                System.out.println("waiting exception");
//            }
//            ActorGraph.getGraph().removeNode(tradeId);
            throw new IllegalArgumentException("User2 does not exist");
        }

        if (username1.equals(username2)) {
            throw new IllegalArgumentException("A User cannot trade with himself");
        }

        final UserDto userDto1 = user1.getUser().join();
        final UserDto userDto2 = user2.getUser().join();


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
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (Exception e) {
//                System.out.println("waiting exception");
//            }
//            ActorGraph.getGraph().removeNode(tradeId);
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
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (Exception e) {
//                System.out.println("waiting exception");
//            }
//            ActorGraph.getGraph().removeNode(tradeId);
            throw new IllegalArgumentException("Request Type for User2 invalid");
        }


        if (user1Verified && user2Verified) {

            if (requestTypeForUser1.equals("currency")) {
                Double amount = Double.parseDouble(dataForUser1);
//                ActorGraph.getGraph().addEdge(tradeId + "take from user1", username1, tradeId);
                user1.makeTransaction("withdraw", amount);
            } else if (requestTypeForUser1.equals("item")) {
//                ActorGraph.getGraph().addEdge(tradeId + "take from user1", username1, tradeId);
                user1.giveOrTakeItem(dataForUser1, "take");
            } else {
                throw new IllegalArgumentException("Request Type for User1 invalid");
            }


            if (requestTypeForUser2.equals("currency")) {
                Double amount = Double.parseDouble(dataForUser2);
//                ActorGraph.getGraph().addEdge(tradeId + "take from user2", username2, tradeId);
                user2.makeTransaction("withdraw", amount);
            }
            else if (requestTypeForUser2.equals("item")) {
//                ActorGraph.getGraph().addEdge(tradeId + "take from user2", username2, tradeId);
                user2.giveOrTakeItem(dataForUser2, "take");
            }
            else {
                throw new IllegalArgumentException("Request Type for User2 invalid");
            }


            if (requestTypeForUser1.equals("currency")) {
//                ActorGraph.getGraph().addEdge(tradeId + "give to user1", tradeId, username1);
                Double amount = Double.parseDouble(dataForUser1);
                user2.makeTransaction("deposit", amount);
            }
            else if (requestTypeForUser1.equals("item")) {
//                ActorGraph.getGraph().addEdge(tradeId + "give to user1", tradeId, username1);
                user2.giveOrTakeItem(dataForUser1, "give");
            }
            else {
                throw new IllegalArgumentException("Request Type for User1 invalid");
            }


            if (requestTypeForUser2.equals("currency")) {
//                ActorGraph.getGraph().addEdge(tradeId + "give to user2", tradeId, username2);
                Double amount = Double.parseDouble(dataForUser2);
                user1.makeTransaction("deposit", amount);
            }
            else if (requestTypeForUser2.equals("item")) {
//                ActorGraph.getGraph().addEdge(tradeId + "give to user2", tradeId, username2);
                user1.giveOrTakeItem(dataForUser2, "give");
            }
            else {
                throw new IllegalArgumentException("Request Type for User2 invalid");
            }


            TradeDto tradeDto = new TradeDto();
            tradeDto.setTradeId(tradeId);
            tradeDto.setUsername1(username1);
            tradeDto.setUsername2(username2);
            tradeDto.setRequestTypeForUser1(requestTypeForUser1);
            tradeDto.setRequestTypeForUser2(requestTypeForUser2);
            tradeDto.setDataForUser1(dataForUser1);
            tradeDto.setDataForUser2(dataForUser2);

            state().tradeId = tradeId;
            await(writeState());

//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (Exception e) {
//                System.out.println("waiting exception");
//            }
//            ActorGraph.getGraph().removeNode(tradeId);

            return Task.fromValue(tradeDto);

        } else {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (Exception e) {
//                System.out.println("waiting exception");
//            }
//            ActorGraph.getGraph().removeNode(tradeId);
            throw new IllegalArgumentException("One or both of the Users did not have required items or balance");
        }
    }

}
