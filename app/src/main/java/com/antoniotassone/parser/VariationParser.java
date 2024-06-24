package com.antoniotassone.parser;

import com.antoniotassone.models.Entrances;
import com.antoniotassone.models.Exits;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import com.antoniotassone.utilities.DateManagement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.Optional;

public class VariationParser implements Parser<Variations>{
    private final Parser<Items> itemParser;

    public VariationParser(){
        itemParser = new ItemParser();
    }

    @Override
    public Optional<Variations> parse(String json){
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
        if(!jsonObj.keySet().contains("variationId") ||
                !jsonObj.keySet().contains("item") ||
                !jsonObj.keySet().contains("date") ||
                !jsonObj.keySet().contains("quantity") ||
                !jsonObj.keySet().contains("type")){

            return Optional.empty();
        }
        String variationId = jsonObj.getString("variationId");
        JSONObject itemJson = jsonObj.getJSONObject("item");
        JSONObject date = jsonObj.getJSONObject("date");
        long quantity = jsonObj.getLong("quantity");
        String type = jsonObj.getString("type");
        Calendar dateTime = DateManagement.createTimestamp(date);
        Optional<Items> item = itemParser.parse(itemJson.toString());
        if(item.isEmpty()){
            return Optional.empty();
        }
        if(type.equals("entrance")){
            return Optional.of(new Entrances(variationId,item.get(),dateTime,quantity));
        }else if(type.equals("exit")){
            return Optional.of(new Exits(variationId,item.get(),dateTime,quantity));
        }
        return Optional.empty();
    }
}