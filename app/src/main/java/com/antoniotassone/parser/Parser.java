package com.antoniotassone.parser;

import com.antoniotassone.models.Models;
import java.util.Optional;

@FunctionalInterface
public interface Parser<T extends Models<T>>{
    Optional<T> parse(String json);
}