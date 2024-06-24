package com.antoniotassone.exceptions;

import java.io.Serial;

public class VariationNotValidException extends WarehouseException{
    @Serial
    private static final long serialVersionUID = 1L;

    public VariationNotValidException(String message){
        super(message);
    }
}