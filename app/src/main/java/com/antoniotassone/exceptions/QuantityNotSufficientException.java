package com.antoniotassone.exceptions;

import java.io.Serial;

public class QuantityNotSufficientException extends WarehouseException{
    @Serial
    private static final long serialVersionUID = 1L;

    public QuantityNotSufficientException(){
        super("The quantity present inside the warehouse isn't sufficient.");
    }
}