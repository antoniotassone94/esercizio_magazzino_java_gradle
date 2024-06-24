package com.antoniotassone.warehouse;

@FunctionalInterface
public interface Engine{
    void executeCommand(Commands command);
}