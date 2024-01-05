package com.example.coffee_rubi_ver.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.coffee_rubi_ver.JDBCutils.TableJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableTypeJDBC;
import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import com.example.coffee_rubi_ver.model.table.TableType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CreateTableCoffeeController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;


    @FXML
    private TabPane tapPaneViewTable;
    @FXML
    private Label comeBackTableView;
    @FXML
    private TextField newTableNameCreate;
    @FXML
    private Button newTableNameCreateBtn;


    @FXML
    void exitClick(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
        Scene scene = null;
        try {
            Stage stage = (Stage) comeBackTableView.getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setMaxWidth(1100);
            stage.setMaxHeight(600);
            stage.setMinWidth(1100);
            stage.setMinHeight(600);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String TABLE_TYPE_DEFAULT = "Bàn Số";
    TableTypeJDBC tableTypeJDBC = new TableTypeJDBC();
    ObservableList<TableCoffee> cardListData = FXCollections.observableArrayList();
    TableJDBC tableJDBC = new TableJDBC();


    protected void initByTableType()
    {
        for (TableType tableType : tableTypeJDBC.listTableTypeDatabase())
        {
            Tab tapNew = new Tab();
            tapNew.setText(tableType.getTableTypeName());
            tapNew.setContent(addTableCoffee());
            tapPaneViewTable.getTabs().add(tapNew);
            tapPaneViewTable.setStyle("-fx-font-size: 20pt;");
            deleteTabPlus(tapNew);
        }
        tapPaneViewTable.getTabs().add(newTabButton(tapPaneViewTable));
    }

    public ScrollPane addTableCoffee()
    {
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        for (TableType tableType : tableTypeJDBC.listTableTypeDatabase()) {
            if (TABLE_TYPE_DEFAULT.equals(tableType.getTableTypeName())) {
                cardListData.clear();
                cardListData.addAll(tableJDBC.getTableCoffeeByType(tableType.getId()));

                int row = 0;
                int column = 0;

                gridPane.getRowConstraints().clear();
                gridPane.getColumnConstraints().clear();

                for (int i = 0; i < cardListData.size(); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editcardtable.fxml"));
                        AnchorPane pane = fxmlLoader.load();
                        EditCardTableController editCardTableController = fxmlLoader.getController();
                        editCardTableController.setData(cardListData.get(i));

                        if (column == 4) {
                            column = 0;
                            row += 1;
                        }

                        gridPane.add(pane, column++, row);
                        GridPane.setMargin(pane, new Insets(15));
                        scrollPane.setContent(gridPane);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return scrollPane;
    }

    public void deleteTabPlus(Tab tapNew)
    {
        tapNew.setOnCloseRequest(event -> {
            Dialog<String> dialogDelete = new Dialog<>();
            dialogDelete.setTitle("Rubi");
            dialogDelete.setHeaderText("Xóa hết bàn trước khi xóa loại bàn \nXóa "+tapNew.getText()+" Khỏi Danh Sách ?");
            ButtonType okDone = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
            dialogDelete.getDialogPane().getButtonTypes().addAll(okDone, ButtonType.CANCEL);
            dialogDelete.setResultConverter(dialogButton -> {
                if (dialogButton == okDone)
                {
                    String tableTypeName = tapNew.getText();
                    tableTypeJDBC.deleteTableTypeName(tableTypeName);
                }
                return null;
            });
            dialogDelete.showAndWait();
        });
    }
    protected void onClickTableHandler() {
        tapPaneViewTable.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (newTab == null)
            {
                return;
            }
            TableType tableType = tableTypeJDBC.getTableTypeByName(newTab.getText());
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            cardListData.clear();
            cardListData.addAll(tableJDBC.getTableCoffeeByType(tableType.getId()));

            int row = 0;
            int column = 0;

            gridPane.getRowConstraints().clear();
            gridPane.getColumnConstraints().clear();

            for (int i = 0; i < cardListData.size(); i++)
            {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editcardtable.fxml"));
                    AnchorPane pane = fxmlLoader.load();
                    EditCardTableController editCardTableController = fxmlLoader.getController();
                    editCardTableController.setData(cardListData.get(i));

                    if (column == 4) {
                        column = 0;
                        row += 1;
                    }

                    gridPane.add(pane, column++, row);
                    GridPane.setMargin(pane,new Insets(15));
                    scrollPane.setContent(gridPane);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            newTab.setContent(scrollPane);
        });
    }
    private Tab newTabButton(TabPane tabPane) {
        Tab addTab = new Tab("+");
        addTab.setClosable(false);
        addTab.setStyle("-fx-font-size: 20pt;");
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(newTab == addTab) {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Rubi");
                dialog.setHeaderText("Tên Loại Bàn : ");
                ButtonType okDone = new ButtonType("Tạo Mới", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(okDone, ButtonType.CANCEL);
                TextField tableTypename = new TextField();
                tableTypename.setPromptText("Tên Loại Bàn");
                Node okDoneButton = dialog.getDialogPane().lookupButton(okDone);
                okDoneButton.setDisable(true);
                tableTypename.textProperty().addListener((observableValue, oldValue, newValue) ->{
                    okDoneButton.setDisable(newValue.trim().isEmpty());
                });
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == okDone)
                    {
                        tabPane.getTabs().add(tabPane.getTabs().size() - 1, new Tab(tableTypename.getText()));
                        String typeName = tableTypename.getText().trim();
                        TableType tableType = new TableType();
                        tableType.setTableTypeName(typeName);
                        tableTypeJDBC.insertNewTableType(tableType);
                    }
                    return null;
                });
                dialog.getDialogPane().setContent(tableTypename);
                dialog.showAndWait();
                tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
            }
        });
        return addTab;
    }
    public void callBackDataTableCoffee()
    {

    }

    void showCreateBtn()
    {
        newTableNameCreateBtn.setDisable(true);
        newTableNameCreate.textProperty().addListener((observable, oldValue, newValue) -> {
            newTableNameCreateBtn.setDisable(newValue.trim().isEmpty());
        });
    }


    @FXML
    void onNewTableNameCreateBtnClick(ActionEvent event) {
        String tableName = newTableNameCreate.getText().trim();
        String tableTypename = tapPaneViewTable.getSelectionModel().getSelectedItem().getText();
        TableType tableType = tableTypeJDBC.getTableTypeByName(tableTypename);

        TableCoffee tableCoffee = new TableCoffee();
        tableCoffee.setTableName(tableName);
        tableCoffee.setStatus(0);
        tableCoffee.setTableType(tableType.getId());
        tableJDBC.insertTableName(tableCoffee);

        tapPaneViewTable.getTabs().clear();
        initByTableType();
    }

    @FXML
    void initialize() {
        initByTableType();
        onClickTableHandler();
        showCreateBtn();
    }

}
