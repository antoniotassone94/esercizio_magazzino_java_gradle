package com.antoniotassone.views;

import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import com.antoniotassone.warehouse.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Calendar;

public class VariationsView extends GeneralView{
    private Engine engine;
    private Views view;
    private Items item;
    @FXML
    private Button cmdGoToMenu;
    @FXML
    private Label lblName;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblPrice;
    @FXML
    private TableView<Variations> tableWarehouse;
    @FXML
    private TableColumn<Variations,Calendar> columnDate;
    @FXML
    private TableColumn<Variations,Long> columnQuantity;
    @FXML
    private Button cmdIncreaseQuantity;
    @FXML
    private Button cmdDecreaseQuantity;

    public VariationsView(){}

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public void setView(Views view){
        this.view = view;
    }

    public void setItem(Items item){
        this.item = item;
    }

    @Override
    public void initializeComponents(){
        if(item != null){
            lblName.setText(item.getName());
            lblDescription.setText(item.getDescription());
            lblPrice.setText(String.valueOf(item.getPrice()));
            //
        }
    }

    public void handleIncreaseQuantity(ActionEvent actionEvent){
        printEventLog(actionEvent,cmdIncreaseQuantity);
        if(engine != null && view != null && item != null){
            if(engine.increaseQuantity(item)){
                view.displayInfo("The item has been increased.");
            }else{
                view.displayError("The item hasn't been increased.");
            }
        }
    }

    public void handleDecreaseQuantity(ActionEvent actionEvent){
        printEventLog(actionEvent,cmdDecreaseQuantity);
        if(engine != null && view != null && item != null){
            if(engine.decreaseQuantity(item)){
                view.displayInfo("The item has been decreased.");
            }else{
                view.displayError("The item hasn't been decreased.");
            }
        }
    }

    public void handleGoToMenu(ActionEvent actionEvent){
        printEventLog(actionEvent,cmdGoToMenu);


        //implementare il cambio di scena verso il menù principale


    }
}