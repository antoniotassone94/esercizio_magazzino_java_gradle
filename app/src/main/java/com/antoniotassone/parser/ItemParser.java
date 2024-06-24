package com.antoniotassone.parser;

import com.antoniotassone.models.Items;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Optional;

public class ItemParser implements Parser<Items>{
    public ItemParser(){}

    @Override
    public Optional<Items> parse(String json){
        if(json == null){
            return Optional.empty();
        }
        if(json.isEmpty()){
            return Optional.empty();
        }
        JSONObject jsonObj;
        try{
            jsonObj = new JSONObject(json);
        }catch(JSONException exception){
            exception.printStackTrace();
            return Optional.empty();
        }
        if(!jsonObj.keySet().contains("itemId") ||
                !jsonObj.keySet().contains("name") ||
                !jsonObj.keySet().contains("description") ||
                !jsonObj.keySet().contains("price")){

            return Optional.empty();
        }
        String itemId = jsonObj.getString("itemId");
        String name = jsonObj.getString("name");
        String description = jsonObj.getString("description");
        double price = jsonObj.getDouble("price");
        return Optional.of(new Items(itemId,name,description,price));
    }
}