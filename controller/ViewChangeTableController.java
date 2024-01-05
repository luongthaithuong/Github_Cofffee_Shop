package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.OrderJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableTypeJDBC;
import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.model.order.OrderDatabase;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import com.example.coffee_rubi_ver.model.table.TableType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewChangeTableController {
    @FXML

    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TabPane tapPaneViewTable;

    @FXML
    private ImageView exitBtn;

    @FXML
    void exitClick(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void initialize() {
        initByTableType();
        onClickTableHandler();
        File file1 = new File("C:\\Users\\toiba\\Desktop\\imagecoffee\\close-button.png");
        Image imageExitBtn = new Image(file1.toURI().toString());
        exitBtn.setImage(imageExitBtn);
    }



    private static final String TABLE_TYPE_DEFAULT = "Bàn Số";
    TableTypeJDBC tableTypeJDBC = new TableTypeJDBC();
    TableJDBC tableJDBC = new TableJDBC();
    ObservableList<TableCoffee> listCardTableChange = FXCollections.observableArrayList();

    OrderDatabase orderDatabase = new OrderDatabase();

    protected void initByTableType()
    {
        for (TableType tableType : tableTypeJDBC.listTableTypeDatabase())
        {
            Tab tapNew = new Tab();
            tapNew.setText(tableType.getTableTypeName());
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            if (TABLE_TYPE_DEFAULT.equals(tableType.getTableTypeName())) {
                listCardTableChange.clear();
                listCardTableChange.addAll(tableJDBC.getTableCoffeeByType(tableType.getId()));

                int row = 0;
                int column = 0;

                gridPane.getRowConstraints().clear();
                gridPane.getColumnConstraints().clear();

                for (int i = 0; i < listCardTableChange.size(); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardtableTemp.fxml"));
                        AnchorPane pane = fxmlLoader.load();
                        CardTableTempController cardTableTempController = fxmlLoader.getController();
                        cardTableTempController.setController(this);
                        cardTableTempController.setData(listCardTableChange.get(i));

                        if (column == 7) {
                            column = 0;
                            row += 1;
                        }

                        gridPane.add(pane, column++, row);
                        GridPane.setMargin(pane, new Insets(12));
                        scrollPane.setContent(gridPane);
                        tapNew.setContent(scrollPane);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            tapPaneViewTable.getTabs().add(tapNew);
            tapPaneViewTable.setStyle("-fx-font-size: 16pt;");
        }
    }
    protected void onClickTableHandler() {
        tapPaneViewTable.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            TableType tableType = tableTypeJDBC.getTableTypeByName(newTab.getText());
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            listCardTableChange.clear();
            listCardTableChange.addAll(tableJDBC.getTableCoffeeByType(tableType.getId()));

            int row = 0;
            int column = 0;

            gridPane.getRowConstraints().clear();
            gridPane.getColumnConstraints().clear();

            for (int i = 0; i < listCardTableChange.size(); i++)
            {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardtableTemp.fxml"));
                    AnchorPane pane = fxmlLoader.load();
                    CardTableTempController cardTableTempController = fxmlLoader.getController();
                    cardTableTempController.setController(this);
                    cardTableTempController.setData(listCardTableChange.get(i));

                    if (column == 4) {
                        column = 0;
                        row += 1;
                    }

                    gridPane.add(pane, column++, row);
                    GridPane.setMargin(pane,new Insets(12));
                    scrollPane.setContent(gridPane);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            newTab.setContent(scrollPane);
        });
    }
    TableCoffee tableCoffee = new TableCoffee();
    public void getTableCoffee(TableCoffee tableCoffee)
    {
        this.tableCoffee = tableCoffee;
        changeTableCoffee();
    }

    public void getOrderDatabase(OrderDatabase orderDatabase)
    {
        this.orderDatabase = orderDatabase;
    }

    OrderJDBC orderJDBC = new OrderJDBC();
    void changeTableCoffee()
    {
        int idTableCoffeeOld = orderDatabase.getTableId();
        tableJDBC.updateStatus0TableByIdTable(idTableCoffeeOld);
        orderJDBC.updateChangeTable(orderDatabase.getId(),tableCoffee.getId());
        tableJDBC.updateStatus1TableByIdTable(tableCoffee.getId());
    }
}
