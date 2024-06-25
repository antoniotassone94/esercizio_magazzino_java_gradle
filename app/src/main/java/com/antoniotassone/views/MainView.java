package com.antoniotassone.views;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.controllers.ItemsForm;
import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Items;
import com.antoniotassone.warehouse.Commands;
import com.antoniotassone.warehouse.Engine;
import com.antoniotassone.warehouse.EngineImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainView extends GeneralView implements Views,Initializable{
    private Engine engine;
    private LogicControllers controller;
    @FXML
    private Label lblInformation;
    @FXML
    private Label lblError;
    @FXML
    private TableView<WarehouseTableRows> tableWarehouse;
    @FXML
    private TableColumn<WarehouseTableRows,String> columnName;
    @FXML
    private TableColumn<WarehouseTableRows,String> columnDescription;
    @FXML
    private TableColumn<WarehouseTableRows,Double> columnPrice;
    @FXML
    private TableColumn<WarehouseTableRows,Long> columnQuantity;
    @FXML
    private TableColumn<WarehouseTableRows,Void> columnActions;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button cmdCreateItem;

    public MainView(){}

    @FXML
    @Override
    public void initialize(URL location,ResourceBundle resources){
        try{
            engine = new EngineImpl(this);
        }catch(ArchiveAlreadyLoadedException | ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            displayError(exception.getMessage());
            engine = null;
        }
        if(engine != null){
            controller = engine.getController();
            Map<Items,Long> warehouse;
            try{
                warehouse = controller.getFullWarehouse().getItems();
            }catch(ArchiveNotLoadedException exception){
                exception.printStackTrace();
                displayError(exception.getMessage());
                warehouse = null;
            }
            columnName.setCellValueFactory(cellData -> cellData.getValue().itemProperty().get().nameProperty());
            columnDescription.setCellValueFactory(cellData -> cellData.getValue().itemProperty().get().descriptionProperty());
            columnPrice.setCellValueFactory(cellData -> cellData.getValue().itemProperty().get().priceProperty().asObject());
            columnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
            addButtonsToTable();
            List<WarehouseTableRows> list = new LinkedList<>();
            if(warehouse != null){
                for(Items item:warehouse.keySet()){
                    list.add(new WarehouseTableRows(item,warehouse.get(item)));
                }
            }
            ObservableList<WarehouseTableRows> rows = FXCollections.observableList(list);
            tableWarehouse.getItems().clear();
            tableWarehouse.setItems(rows);
            tableWarehouse.getSelectionModel().setCellSelectionEnabled(false);
        }
    }

    @Override
    public void displayInfo(String message){
        lblInformation.setText(Objects.requireNonNullElse(message,""));
        lblError.setText("");
    }

    @Override
    public void displayError(String message){
        lblError.setText(Objects.requireNonNullElse(message,""));
        lblInformation.setText("");
    }

    @Override
    public String getInputString(String message){
        lblInformation.setText("");
        lblError.setText("");
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Insert the input requested");
        if(message != null){
            dialog.setHeaderText(message);
        }
        dialog.setContentText("");
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
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
    public boolean addRow(Items item,long quantity){
        return tableWarehouse.getItems().add(new WarehouseTableRows(item,quantity));
    }

    @Override
    public boolean deleteRow(Items item){
        ObservableList<WarehouseTableRows> rows = tableWarehouse.getItems();
        int index = 0;
        while(index < rows.size() && !rows.get(index).getItemId().equals(item.getItemId())){
            index++;
        }
        if(index >= rows.size()){
            return false;
        }
        WarehouseTableRows row = tableWarehouse.getItems().get(index);
        return tableWarehouse.getItems().remove(row);
    }

    public void handleCreateItem(ActionEvent actionEvent){
        if(engine != null){
            printEventLog(actionEvent,cmdCreateItem);
            engine.executeCommand(Commands.CREATE_ITEM);
            resetForm();
        }else{
            displayError("The creation of new item failed, because the archive doesn't exist.");
        }
    }

    private void addButtonsToTable(){
        columnActions.setCellFactory(new ActionsTableCell(this,controller));
    }

    private void resetForm(){
        txtName.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
    }
}