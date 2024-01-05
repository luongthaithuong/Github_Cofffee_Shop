package com.example.coffee_rubi_ver.controller;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

import com.example.coffee_rubi_ver.JDBCutils.ProductJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableTypeJDBC;
import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.JDBCutils.OrderJDBC;
import com.example.coffee_rubi_ver.model.order.OrderDatabase;
import com.example.coffee_rubi_ver.model.order.OrderTemp;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import com.example.coffee_rubi_ver.model.table.TableType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BaoCaoDoanhThuController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboboxChoiceView;

    @FXML
    private Button quayLaiBtn;

    @FXML
    private ListView<OrderDatabase> listviewBCDT;

    @FXML
    private Label tongTienText;

    @FXML
    void onQuayLaiBtnClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
        Scene scene = null;
        try {
//            int width = (int) Screen.getPrimary().getBounds().getWidth();
//            int height = (int) Screen.getPrimary().getBounds().getHeight();
            Stage stage = (Stage) quayLaiBtn.getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setMaxWidth(1100);
            stage.setMaxHeight(600);
            stage.setMinWidth(1100);
            stage.setMinHeight(600);
//            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    OrderJDBC orderJDBC = new OrderJDBC();

    private ObservableList<OrderDatabase> listBCDTToday;
    private ObservableList<OrderDatabase> listBCDTMouth;
    private String today = "Hôm Nay";
    private String aMouth = "Tháng Này";
    ObservableList<String> listCombobox = FXCollections.observableArrayList(today,aMouth);

    public void showBCDTCurren()
    {
        listBCDTToday = FXCollections.observableArrayList(prepareDataBCDTToday());
        listBCDTMouth = FXCollections.observableArrayList(prepareDataBCDTMouth());
        listviewBCDT.getItems().clear();
        listviewBCDT.getItems().addAll(listBCDTToday);
        listviewBCDT.setStyle("-fx-font-size: 13pt;");
        showTongTienToday();
        comboboxChoiceView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        if (newValue.equals(today))
                        {
                            listviewBCDT.getItems().clear();
                            listviewBCDT.getItems().addAll(listBCDTToday);
                            listviewBCDT.setStyle("-fx-font-size: 13pt;");
                            showTongTienToday();
                        }
                        if (newValue.equals(aMouth))
                        {
                            listviewBCDT.getItems().clear();
                            listviewBCDT.getItems().addAll(listBCDTMouth);
                            listviewBCDT.setStyle("-fx-font-size: 13pt;");
                            showTongTienMouth();
                        }
                    }
                });
    }

    public void showTongTienToday()
    {
        int result = 0;
        for (OrderDatabase orderDatabase : orderJDBC.showTodayBCDT())
        {
            result += orderDatabase.getTotalPrice();
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        tongTienText.setText(String.valueOf(vn.format(result)));
    }
    public void showTongTienMouth()
    {
        int result = 0;
        for (OrderDatabase orderDatabase : orderJDBC.showMouthBCDT())
        {
            result += orderDatabase.getTotalPrice();
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        tongTienText.setText(String.valueOf(vn.format(result)));
    }

    @FXML
    void initialize() {
        showBCDTCurren();
        comboboxChoiceView.setItems(listCombobox);
    }
    ProductJDBC productJDBC = new ProductJDBC();
    private Set<OrderTemp> orderTempSet = new HashSet<>();
    TableJDBC tableJDBC = new TableJDBC();

    TableTypeJDBC tableTypeJDBC = new TableTypeJDBC();
    public List<OrderDatabase> prepareDataBCDTToday() {
        List<OrderDatabase> observableListToday = FXCollections.observableArrayList();
        for (OrderDatabase orderDatabase : orderJDBC.showTodayBCDT()) {
            StringBuilder stringBuilder = new StringBuilder();

            int idTable = orderDatabase.getTableId();
            TableCoffee tableCoffee = tableJDBC.getTableCoffeeById(idTable);
            TableType tableType = tableTypeJDBC.getTableTypeByID(tableCoffee.getTableType());

            String dataOrder = orderDatabase.getProductOnOrder();
            String[] productList = dataOrder.split(",");
            for (int i = 0; i < productList.length; i+=3) {
                String id;
                String name;
                int soLuong;
                id = productList[i];
                name = String.valueOf(productJDBC.getProductByProductId(Integer.parseInt(id)));
                soLuong = Integer.parseInt(productList[i + 1]);
                stringBuilder.append(name);
                stringBuilder.append(" ");
                stringBuilder.append("\tSL : ").append(soLuong).append("\t");
                stringBuilder.append(" ");
                orderDatabase.setProductOnOrder(String.valueOf(stringBuilder));
                orderDatabase.setTableName(tableCoffee.getTableName());
                orderDatabase.setTableTypeName(tableType.getTableTypeName());
            }
            observableListToday.add(orderDatabase);
        }
        return observableListToday;
    }
    public List<OrderDatabase> prepareDataBCDTMouth() {
        List<OrderDatabase> observableListMouth = FXCollections.observableArrayList();
        for (OrderDatabase orderDatabase : orderJDBC.showMouthBCDT()) {
            StringBuilder stringBuilder = new StringBuilder();

            int idTable = orderDatabase.getTableId();
            TableCoffee tableCoffee = tableJDBC.getTableCoffeeById(idTable);
            TableType tableType = tableTypeJDBC.getTableTypeByID(tableCoffee.getTableType());

            String dataOrder = orderDatabase.getProductOnOrder();
            String[] productList = dataOrder.split(",");
            for (int i = 0; i < productList.length; i+=3) {
                String id;
                String name;
                int soLuong;
                id = productList[i];
                name = String.valueOf(productJDBC.getProductByProductId(Integer.parseInt(id)));
                soLuong = Integer.parseInt(productList[i + 1]);
                stringBuilder.append(name);
                stringBuilder.append(" ");
                stringBuilder.append("\tSL : ").append(soLuong).append("\t");
                stringBuilder.append(" ");
                orderDatabase.setProductOnOrder(String.valueOf(stringBuilder));
                orderDatabase.setTableName(tableCoffee.getTableName());
                orderDatabase.setTableTypeName(tableType.getTableTypeName());
            }
            observableListMouth.add(orderDatabase);
        }
        return observableListMouth;
    }

}
