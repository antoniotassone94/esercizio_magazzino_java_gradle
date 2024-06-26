package com.antoniotassone.controllers;

import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Models;
import com.antoniotassone.models.Variations;
import com.antoniotassone.parser.Parser;
import com.antoniotassone.utilities.FilesManagement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CRUDVariationsController implements CRUDControllers<Variations>{
    public CRUDVariationsController(){}

    @Override
    public Optional<Variations> createElement(Form<Variations> data){
        if(data == null){
            return Optional.empty();
        }
        Optional<Variations> variation = data.readForm();
        if(variation.isPresent()){
            if(data.checkForm()){
                if(FilesManagement.writeObjectToFile(variation.get())){
                    return variation;
                }
                return Optional.empty();
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean updateElement(Variations data){
        return false;
    }

    @Override
    public boolean removeElement(Models<Variations> object){
        if(object == null){
            return false;
        }
        return FilesManagement.deleteObject(object);
    }

    @Override
    public List<Variations> findAll(Parser<Variations> parser) throws ItemNotValidException{
        if(parser == null){
            return new LinkedList<>();
        }
        List<String> files = FilesManagement.readListFiles();
        List<Variations> variations = new LinkedList<>();
        for(String file:files){
            if(file.contains("entrances_") || file.contains("exits_")){
                List<String> rows = FilesManagement.readFile(FilesManagement.getUserDirectory() + "/" + file);
                StringBuilder json = new StringBuilder();
                for(String row:rows){
                    json.append(row);
                }
                Variations variation = parser.parse(json.toString());
                variations.add(variation);
            }
        }
        return variations;
    }
}