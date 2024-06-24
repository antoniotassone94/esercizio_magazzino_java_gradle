package com.antoniotassone.exceptions;

import java.io.Serial;

public class ArchiveAlreadyLoadedException extends WarehouseException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ArchiveAlreadyLoadedException(){
        super("The archive of the application already exists.");
    }
}