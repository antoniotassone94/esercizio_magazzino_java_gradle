package com.antoniotassone.views;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.controllers.ItemsForm;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Warehouses;
import com.antoniotassone.warehouse.Commands;
import com.antoniotassone.warehouse.Engine;
import com.antoniotassone.warehouse.EngineImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.Optional;

public class MainView implements Views{
    private final Engine engine;
    @FXML
    private TableView<String> tableWarehouse;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnDescription;
    @FXML
    private TableColumn columnPrice;
    @FXML
    private TableColumn columnQuantity;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button cmdCreateItem;

    public MainView(){
        engine = new EngineImpl(this);
    }

    public void handleCreateItem(ActionEvent actionEvent){
        this.printEventLog(actionEvent,cmdCreateItem);
        engine.executeCommand(Commands.CREATE_ITEM);
    }

    @Override
    public Optional<Items> readItemData(){
        String name = txtName.getText();
        String description = txtDescription.getText();
        double price;
        try{
            price = Double.parseDouble(txtPrice.getText());
        }catch(NumberFormatException exception){
            return Optional.empty();
        }
        Items item = new Items(name,description,price);
        return Optional.of(item);
    }

    @Override
    public void displayInfo(String message){
        System.out.println(message);
    }

    @Override
    public void displayError(String message){
        System.err.println(message);
    }

    @Override
    public String getInputString(String message){
        System.out.println(message);
        return "";
    }

    @Override
    public Form<Items> readNewItem(){
        String name = txtName.getText();
        String description = txtDescription.getText();
        double price;
        try{
            price = Double.parseDouble(txtPrice.getText());
        }catch(NumberFormatException exception){
            return null;
        }
        Items item = new Items(name,description,price);
        return new ItemsForm(item);
    }

    @Override
    public void displayWarehouse(Warehouses warehouse){
        System.out.println(warehouse);
    }

    public Callback<TableColumn<String,String>,TableCell<String,String>> createButtonCell(/*TableColumn<String,String> item*/){
        return null;
    }

    private void printEventLog(ActionEvent actionEvent,Button button){
        String className = actionEvent.getSource().getClass().getName();
        String[] details = className.split("[.]");
        String specific = details[details.length - 1];
        String console = specific + " with text \"" + button.getText() + "\" has launched an event.";
        System.out.println(console);
    }

    public void handleViewVariationsProduct(MouseEvent mouseEvent){
    }
}