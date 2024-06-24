package com.antoniotassone.parser;

import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Entrances;
import com.antoniotassone.models.Exits;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import com.antoniotassone.utilities.DateManagement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;

public class VariationParser implements Parser<Variations>{
    private final Parser<Items> itemParser;

    public VariationParser(){
        itemParser = new ItemParser();
    }

    @Override
    public Variations parse(String json) throws ItemNotValidException{
        if(json == null){
            throw new ItemNotValidException("The JSON object is null.");
        }
        if(json.isEmpty()){
            throw new ItemNotValidException("The JSON object is empty.");
        }
        JSONObject jsonObj;
        try{
            jsonObj = new JSONObject(json);
        }catch(JSONException exception){
            exception.printStackTrace();
            throw new ItemNotValidException("The object is not a valid JSON object.");
        }
        if(!jsonObj.keySet().contains("variationId") ||
                !jsonObj.keySet().contains("item") ||
                !jsonObj.keySet().contains("date") ||
                !jsonObj.keySet().contains("quantity") ||
                !jsonObj.keySet().contains("type")){

            throw new ItemNotValidException("The JSON object doesn't contain all the necessary keys.");
        }
        String variationId;
        JSONObject itemJson;
        JSONObject date;
        long quantity;
        String type;
        try{
            variationId = jsonObj.getString("variationId");
            itemJson = jsonObj.getJSONObject("item");
            date = jsonObj.getJSONObject("date");
            quantity = jsonObj.getLong("quantity");
            type = jsonObj.getString("type");
        }catch(JSONException exception){
            exception.printStackTrace();
            throw new ItemNotValidException("The object is not a valid JSON object.");
        }
        if(!type.equals("entrance") && !type.equals("exit")){
            throw new ItemNotValidException("The type of the variation of warehouse isn't valid.");
        }
        Calendar dateTime = DateManagement.createTimestamp(date);
        Items item = itemParser.parse(itemJson.toString());
        if(type.equals("entrance")){
            return new Entrances(variationId,item,dateTime,quantity);
        }else{
            return new Exits(variationId,item,dateTime,quantity);
        }
    }
}