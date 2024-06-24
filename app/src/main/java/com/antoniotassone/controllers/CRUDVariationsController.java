package com.antoniotassone.controllers;

import com.antoniotassone.models.Models;
import com.antoniotassone.models.Variations;
import com.antoniotassone.parser.Parser;
import com.antoniotassone.utilities.FilesManagement;
import com.antoniotassone.views.Views;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CRUDVariationsController implements CRUDControllers<Variations>{
    private final Views view;

    public CRUDVariationsController(Views view){
        this.view = view;
    }

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
    public List<Variations> findAll(Parser<Variations> parser){
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
                Optional<Variations> variation = parser.parse(json.toString());
                variation.ifPresentOrElse(variations::add,() -> view.displayError("The variation " + file + " wasn't found."));
            }
        }
        return variations;
    }
}