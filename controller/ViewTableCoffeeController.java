package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.TableJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableTypeJDBC;
import com.example.coffee_rubi_ver.Main;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewTableCoffeeController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboBoxCreateModel;

    @FXML
    private TableColumn<?, ?> columnNameProduct;

    @FXML
    private TableColumn<?, ?> columnPrice;

    @FXML
    private TableColumn<?, ?> columnSL;

    @FXML
    private Label nameTableText;

    @FXML
    private Button payBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TableView<?> tableViewProductOfTable;

    @FXML
    private TabPane tapPaneViewTable;

    @FXML
    private Label totalPriceText;

    @FXML
    private Button closeBtn;

    @FXML
    private Label labelNotification;

    @FXML
    void onCloseBtnClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void initialize() {
        onClickComboBox();
        initByTableType();
        onClickTableHandler();
        showMenu();
    }

    private final String CREATE_TABLE = "Chỉnh Sửa Bàn";
    private final String CREATE_PRODUCT = "Chỉnh Sửa Món";
    private final String BAO_CAO_DOANH_THU = "Báo Cáo Doanh Thu";
    ObservableList<String> comboBoxCreateModelList = FXCollections.observableArrayList(CREATE_TABLE,CREATE_PRODUCT,BAO_CAO_DOANH_THU);


    public void onClickComboBox()
    {
        comboBoxCreateModel.setItems(comboBoxCreateModelList);
    }

    // prioritize load First TableType, Don't load all
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
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            if (TABLE_TYPE_DEFAULT.equals(tableType.getTableTypeName())) {
                cardListData.clear();
                cardListData.addAll(tableJDBC.getTableCoffeeByType(tableType.getId()));

                int row = 0;
                int column = 0;

                gridPane.getRowConstraints().clear();
                gridPane.getColumnConstraints().clear();

                for (int i = 0; i < cardListData.size(); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardtable.fxml"));
                        AnchorPane pane = fxmlLoader.load();
                        CardTableController cardProductController = fxmlLoader.getController();
                        cardProductController.setData(cardListData.get(i));

                        if (column == 4) {
                            column = 0;
                            row += 1;
                        }

                        gridPane.add(pane, column++, row);
                        GridPane.setMargin(pane, new Insets(10));
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

    // get value first click
    protected void onClickTableHandler() {
        tapPaneViewTable.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
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
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardtable.fxml"));
                    AnchorPane pane = fxmlLoader.load();
                    CardTableController cardProductController = fxmlLoader.getController();
                    cardProductController.setData(cardListData.get(i));

                    if (column == 4) {
                        column = 0;
                        row += 1;
                    }

                    gridPane.add(pane, column++, row);
                    GridPane.setMargin(pane,new Insets(10));
                    scrollPane.setContent(gridPane);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            newTab.setContent(scrollPane);
        });
    }

    public void showMenu()
    {
    comboBoxCreateModel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
    {
        public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                if (newValue.equals(CREATE_TABLE))
                {
                    changeScene("createtablecoffee.fxml");
                }
                if (newValue.equals(CREATE_PRODUCT))
                {
                    changeScene("createProduct.fxml");
                }
                if (newValue.equals(BAO_CAO_DOANH_THU))
                {
                    changeScene("baocaodoanhthu.fxml");
                }
            }
        });
    }

    void changeScene(String fxml)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene scene = null;
        try {
            Stage stage = (Stage) comboBoxCreateModel.getScene().getWindow();
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
