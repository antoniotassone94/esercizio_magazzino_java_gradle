package com.antoniotassone.exceptions;

import java.io.Serial;

public class ItemNotValidException extends WarehouseException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ItemNotValidException(String message){
        super(message);
    }
}