package com.antoniotassone.views;

import com.antoniotassone.models.Variations;
import com.antoniotassone.models.VariationsType;
import javafx.scene.control.TableRow;

public class RowsColoredForType extends TableRow<Variations>{
    public RowsColoredForType(){
        super();
    }

    @Override
    protected void updateItem(Variations item,boolean empty){
        super.updateItem(item,empty);
        if(item == null || empty){
            setStyle("");
        }else{
            if(item.getType().equals(VariationsType.ENTRANCE)){
                setStyle("-fx-background-color:lightgreen");
            }else if(item.getType().equals(VariationsType.EXIT)){
                setStyle("-fx-background-color:orange");
            }
        }
    }
}