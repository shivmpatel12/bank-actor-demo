package com.ea.eadpm.skunkworks.dto;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.io.Serializable;

/**
 * Created by Shiv on 6/1/2016.
 */

@Immutable
public class TransactionDto implements Serializable {

    private String transactionType;
    private double amount;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
