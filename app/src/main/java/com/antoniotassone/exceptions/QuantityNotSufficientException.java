package com.antoniotassone.exceptions;

public class QuantityNotSufficientException extends WarehouseException{
    public QuantityNotSufficientException(){
        super("The quantity present inside the warehouse isn't sufficient.");
    }
}