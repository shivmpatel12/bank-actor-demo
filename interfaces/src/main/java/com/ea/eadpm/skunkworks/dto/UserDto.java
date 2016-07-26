package com.ea.eadpm.skunkworks.dto;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shiv on 6/1/2016.
 */

@Immutable
@XmlRootElement
public class UserDto implements Serializable {

    private double balance;
    private String username;
    private List<TransactionDto> transactionsForUser;
    private List<String> items;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TransactionDto> getTransactionsForUser() {
        return transactionsForUser;
    }

    public void setTransactionsForUser(List<TransactionDto> transactionsForUser) {
        this.transactionsForUser = transactionsForUser;
    }

    public List<String> getItems()
    {
        return items;
    }

    public void setItems(final List<String> items)
    {
        this.items = items;
    }
}
