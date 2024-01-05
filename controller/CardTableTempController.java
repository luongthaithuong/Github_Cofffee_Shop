package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.OrderJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableJDBC;
import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.model.order.OrderDatabase;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardTableTempController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Ellipse statusLight;


    @FXML
    private AnchorPane tableView;

    @FXML
    private Label tableNameText;

    TableCoffee tableCoffee = new TableCoffee();


    public void setData(TableCoffee tableCoffee)
    {
        this.tableCoffee = tableCoffee;
        this.tableNameText.setText(tableCoffee.getTableName());
        if (tableCoffee.getStatus() == 1)
        {
            statusLight.setFill(Color.RED);
        }
    }

    ViewChangeTableController viewChangeTableController = new ViewChangeTableController();
    public void setController(ViewChangeTableController view)
    {
        viewChangeTableController = view;
    }

    @FXML
    void initialize() {
    }

    @FXML
    void onClickModel(MouseEvent event) {
        if (tableCoffee.getStatus() == 0)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
            Scene scene = null;
            try {
                Stage stage = (Stage) tableView.getScene().getWindow();
                viewChangeTableController.getTableCoffee(tableCoffee);
                scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
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
}
