package com.ea.eadpm.skunkworks.dto;

/**
 * Created by Shiv on 6/13/2016.
 */
public class AddTransactionDto {

    private String username;
    private String transactionType;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public String getUsername() {
        return username;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
