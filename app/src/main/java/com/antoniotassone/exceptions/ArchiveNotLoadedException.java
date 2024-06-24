package com.antoniotassone.exceptions;

import java.io.Serial;

public class ArchiveNotLoadedException extends WarehouseException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ArchiveNotLoadedException(){
        super("The archive of the application isn't loaded in the memory.");
    }
}