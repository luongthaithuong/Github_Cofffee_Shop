package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardTableController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Ellipse statusLight;

    @FXML
    private Label tableNameText;
    @FXML
    private AnchorPane tableView;

    TableCoffee tableCoffee = new TableCoffee();


    public void setData (TableCoffee tableCoffee)
    {
        this.tableCoffee = tableCoffee;
        this.tableNameText.setText(tableCoffee.getTableName());
        if (tableCoffee.getStatus() == 1)
        {
            statusLight.setFill(Color.RED);
        }
    }

    @FXML
    void initialize() {
    }
    @FXML
    void onClickModel(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("vieworderoftablecoffee.fxml"));
        Scene scene = null;
        try {
            Stage stage = (Stage) tableNameText.getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            ViewOrderOfTableCoffeeController viewOrderOfTableCoffeeController = fxmlLoader.getController();
            viewOrderOfTableCoffeeController.setNameTable(tableCoffee);
            viewOrderOfTableCoffeeController.setSelectedTableId(tableCoffee.getId());
            viewOrderOfTableCoffeeController.statusTable(tableCoffee.getStatus());
            viewOrderOfTableCoffeeController.changeDataOrderDatabase(tableCoffee.getId());
            viewOrderOfTableCoffeeController.setVisibleChangTableBtn(tableCoffee.getStatus());
            stage.setMaxWidth(1100);
            stage.setMaxHeight(600);
            stage.setMinWidth(1100);
            stage.setMinHeight(600);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
