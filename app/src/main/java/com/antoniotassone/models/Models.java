package com.antoniotassone.models;

public interface Models<T>{
    T copy();

    String getId();
}