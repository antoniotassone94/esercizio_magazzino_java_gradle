package com.antoniotassone.views;

import com.antoniotassone.models.Variations;
import javafx.scene.control.TableCell;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatCell extends TableCell<Variations,Calendar>{
    private final SimpleDateFormat dateFormat;

    public DateFormatCell(){
        super();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Override
    protected void updateItem(Calendar item,boolean empty){
        super.updateItem(item,empty);
        if(empty || item == null){
            setText(null);
        }else{
            String dateFormatted = dateFormat.format(item.getTime());
            setText(dateFormatted);
        }
    }
}