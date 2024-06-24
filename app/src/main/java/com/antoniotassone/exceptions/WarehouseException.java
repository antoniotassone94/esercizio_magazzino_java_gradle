package com.antoniotassone.exceptions;

import java.io.Serial;

public abstract class WarehouseException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    public WarehouseException(String message){
        super(message);
    }

    @Override
    public String toString(){
        return "Error during the execution: " + this.getMessage();
    }
}