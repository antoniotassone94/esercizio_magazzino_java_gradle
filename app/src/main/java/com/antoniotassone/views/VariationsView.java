package com.antoniotassone.views;

import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import com.antoniotassone.warehouse.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Calendar;
import java.util.List;

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
    private TableView<Variations> tableVariations;
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
            columnDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            columnDate.setCellFactory(column -> new DateFormatCell());
            columnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
            List<Variations> variations;
            try{
                variations = engine.getController().getFullWarehouse().getVariations(item);
            }catch(ItemNotValidException | ArchiveNotLoadedException exception){
                exception.printStackTrace();
                variations = null;
            }
            if(variations != null){
                ObservableList<Variations> rows = FXCollections.observableList(variations);
                tableVariations.getItems().clear();
                tableVariations.getItems().addAll(rows);
                tableVariations.getSelectionModel().setCellSelectionEnabled(false);
                tableVariations.setRowFactory(fullTable -> new RowsColoredForType());
            }
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
        if(getManager().showScene("mainView")){
            System.out.println("The main javafx scene has been opened.");
        }else{
            System.err.println("The main javafx scene hasn't been opened.");
        }
    }

    public boolean addRow(Variations newVariation){
        if(newVariation == null){
            return false;
        }
        if(tableVariations.getItems().contains(newVariation)){
            return false;
        }
        return tableVariations.getItems().add(newVariation.copy());
    }
}