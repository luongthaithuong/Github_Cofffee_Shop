package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.TableJDBC;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCardTableController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label tableNameText;
    @FXML
    private AnchorPane tableView;

    TableCoffee tableCoffee = new TableCoffee();


    public void setData (TableCoffee tableCoffee)
    {
        this.tableCoffee = tableCoffee;
        this.tableNameText.setText(tableCoffee.getTableName());
    }

    @FXML
    void initialize() {
    }
    TableJDBC tableJDBC = new TableJDBC();
    CreateTableCoffeeController createTableCoffeeController = new CreateTableCoffeeController();
    public void setController(CreateTableCoffeeController view)
    {
        createTableCoffeeController = view;
    }
    @FXML
    void onClickModel(MouseEvent event) {
        Dialog<String> dialogDelete = new Dialog<>();
        dialogDelete.setTitle("Rubi");
        ButtonType delTable = new ButtonType("Xóa Bàn", ButtonBar.ButtonData.OK_DONE);
        ButtonType editTable = new ButtonType("Chỉnh Sửa", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogDelete.getDialogPane().getButtonTypes().addAll(delTable,editTable);
        TextField tableTypename = new TextField();
        tableTypename.setPromptText(tableCoffee.getTableName());
        Node okDoneButton = dialogDelete.getDialogPane().lookupButton(editTable);
        okDoneButton.setDisable(true);
        tableTypename.textProperty().addListener((observableValue, oldValue, newValue) ->{
            okDoneButton.setDisable(newValue.trim().isEmpty());
        });
        dialogDelete.setResultConverter(dialogButton -> {
            if (dialogButton == editTable)
            {
                String newNameTable = tableTypename.getText();
                int idTable = tableCoffee.getId();
                tableJDBC.editTable(newNameTable,idTable);
            }
            if (dialogButton == delTable){
                System.out.println(tableCoffee.getId());
                tableJDBC.deleteTableCoffee(tableCoffee.getId());
            }
            return null;
        });
        dialogDelete.getDialogPane().setContent(tableTypename);
        dialogDelete.showAndWait();
    }
}
