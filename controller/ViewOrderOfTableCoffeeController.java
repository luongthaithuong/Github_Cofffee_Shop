package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.*;
import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.model.order.OrderDatabase;
import com.example.coffee_rubi_ver.model.order.OrderTemp;
import com.example.coffee_rubi_ver.model.product.Category;
import com.example.coffee_rubi_ver.model.product.Product;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import com.example.coffee_rubi_ver.model.table.TableType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;

public class ViewOrderOfTableCoffeeController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<OrderTemp, String> columnNameProduct;

    @FXML
    private TableColumn<OrderTemp, Integer> columnPrice;

    @FXML
    private TableColumn<OrderTemp, Integer> columnSL;

    @FXML
    private Label goBackToViewTableCoffee;

    @FXML
    private Label nameTableText;

    @FXML
    private Button payBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TableView<OrderTemp> tableViewProductOfTable;

    @FXML
    private TabPane tapPaneViewProduct;

    @FXML
    private Label totalPriceText;
    @FXML
    private ImageView exit;

    @FXML
    void exitClick(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void onGoBackToViewTableCoffeeClick(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
        Scene scene = null;
        try {
//            int width = (int) Screen.getPrimary().getBounds().getWidth();
//            int height = (int) Screen.getPrimary().getBounds().getHeight();
            Stage stage = (Stage) goBackToViewTableCoffee.getScene().getWindow();
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

    public void setNameTable(TableCoffee tableCoffee) {
        nameTableText.setText(tableCoffee.getTableName());
    }
    private int selectedTableId;

    public void setSelectedTableId(int tableId) {
        selectedTableId = tableId;
    }

    private int statusTable;

    public void statusTable(int status) {
        this.statusTable = status;
    }

    private static final String PRODUCT_TYPE_DEFAULT = "Cà Phê";
    CategoryJDBC categoryJDBC = new CategoryJDBC();
    ObservableList<Product> cardListData = FXCollections.observableArrayList();
    ProductJDBC productJDBC = new ProductJDBC();
    protected void initByCategory()
    {
        for (Category category : categoryJDBC.listCategoryDatabase())
        {
            Tab tapNew = new Tab();
            tapNew.setText(category.getCategoryName());
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            if (PRODUCT_TYPE_DEFAULT.equals(category.getCategoryName())) {
                cardListData.clear();
                cardListData.addAll(productJDBC.getProductByCatID(category.getId()));

                int row = 0;
                int column = 0;

                gridPane.getRowConstraints().clear();
                gridPane.getColumnConstraints().clear();

                for (int i = 0; i < cardListData.size(); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardproduct.fxml"));
                        AnchorPane pane = fxmlLoader.load();
                        CardProductController cardProductController = fxmlLoader.getController();
                        cardProductController.setController(this);
                        cardProductController.setData(cardListData.get(i));

                        if (column == 3) {
                            column = 0;
                            row += 1;
                        }

                        gridPane.add(pane, column++, row);
                        GridPane.setMargin(pane, new Insets(6));
                        scrollPane.setContent(gridPane);
                        tapNew.setContent(scrollPane);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            tapPaneViewProduct.getTabs().add(tapNew);
            tapPaneViewProduct.setStyle("-fx-font-size: 20pt;");
        }
    }
    protected void onClickProductHandler() {
        tapPaneViewProduct.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            Category category = categoryJDBC.getCategoryByName(newTab.getText());
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            cardListData.clear();
            cardListData.addAll(productJDBC.getProductByCatID(category.getId()));

            int row = 0;
            int column = 0;

            gridPane.getRowConstraints().clear();
            gridPane.getColumnConstraints().clear();

            for (int i = 0; i < cardListData.size(); i++)
            {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardproduct.fxml"));
                    AnchorPane pane = fxmlLoader.load();
                    CardProductController cardProductController = fxmlLoader.getController();
                    cardProductController.setController(this);
                    cardProductController.setData(cardListData.get(i));

                    if (column == 3) {
                        column = 0;
                        row += 1;
                    }

                    gridPane.add(pane, column++, row);
                    GridPane.setMargin(pane,new Insets(6));
                    scrollPane.setContent(gridPane);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            newTab.setContent(scrollPane);
        });
    }

    private OrderDatabase orderDatabase = new OrderDatabase();
    private Set<OrderTemp> orderTempSet = new HashSet<>();;
    @FXML
    private Button deleteBtn;


    private int id;
    public void addProduct(int id) {
        this.id = id;
        initViewTableProductOfTable();
    }

    public void initViewTableProductOfTable()
    {
        Product product = productJDBC.getProductByProductId(id);
        if (product != null) {
            prepareOrderTable(product);
        }
    }
    public void prepareOrderTable(Product product) {
        OrderTemp orderTemp = new OrderTemp();
        orderTemp.setProduct(product);
        orderTemp.setPriceTemp(product.getProductPrice());
        for (OrderTemp o : orderTempSet) {
            if (o.getProduct().getProductName().equalsIgnoreCase(product.getProductName())) {
                o.setQuantity(o.getQuantity() + 1);
                o.setPriceTemp(o.getQuantity() * o.getProduct().getProductPrice());
                break;
            }
        }
        orderTempSet.add(orderTemp);
        orderDatabase.setOrderTempSet(orderTempSet);
        tableViewProductOfTable.getItems().clear();
        tableViewProductOfTable.setItems(FXCollections.observableArrayList(orderTempSet));
        tableViewProductOfTable.setStyle("-fx-font-size: 14pt;");

        deleteBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                OrderTemp orderTemp = tableViewProductOfTable.getSelectionModel().getSelectedItem();
                if (orderTempSet.size() == 1)
                {
                    return;
                }
                tableViewProductOfTable.getItems().removeAll(orderTemp);
                orderTempSet.remove(orderTemp);
                int result = 0;
                for (OrderTemp orderTemp1 : orderTempSet) {
                    result += orderTemp1.getPriceTemp();
                }
                Locale locale = new Locale("vi", "VN");
                NumberFormat vn = NumberFormat.getInstance(locale);
                totalPriceText.setText(String.valueOf(vn.format(result)));
            }
        });

        int result = 0;

        for (OrderTemp orderTemp1 : orderTempSet) {
            result += orderTemp1.getPriceTemp();
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        totalPriceText.setText(String.valueOf(vn.format(result)));
    }
    public void prepareDataTable(OrderDatabase orderDatabase) {
        String dataOrder = orderDatabase.getProductOnOrder();
        String[] productList = dataOrder.split(",");
        int j = 0;
        for (int i = 0; i < productList.length / 3; i++) {
            String id;
            int soLuong;
            int price;
            String name;
            if (i == 0) {
                id = productList[i];
                name = String.valueOf(productJDBC.getProductByProductId(Integer.parseInt(id)));
                soLuong = Integer.parseInt(productList[i + 1]);
                price = Integer.parseInt(productList[i + 2]);
            } else {
                id = productList[i * 3];
                name = String.valueOf(productJDBC.getProductByProductId(Integer.parseInt(id)));
                soLuong = Integer.parseInt(productList[((i * 3) + 1)]);
                price = Integer.parseInt(productList[((i * 3) + 2)]);
            }

            Product product = new Product();
            product.setId(Integer.parseInt(id));
            product.setProductName(name);
            product.setProductPrice(price);
            for (int k = 0; k < soLuong; k++)
                prepareOrderTable(product);
        }
    }
    private OrderJDBC orderJDBC = new OrderJDBC();
    private TableJDBC tableJDBC = new TableJDBC();

    public void changeDataOrderDatabase(int tableId) {
        orderDatabase = orderJDBC.getOrderDatabaseById(tableId);
        if (orderDatabase != null) {
            prepareDataTable(orderDatabase);
        }
        else
            orderDatabase = new OrderDatabase();
    }

//    public void prepareOrderTable0(Product product) {
//        OrderTemp orderTemp = new OrderTemp();
//        orderTemp.setProduct(product);
//        orderTemp.setPriceTemp(product.getProductPrice());
//        for (OrderTemp o : orderTempSet) {
//            if (o.getProduct().getProductName().equalsIgnoreCase(product.getProductName())) {
//                o.setQuantity(o.getQuantity() + 1);
//                o.setPriceTemp(o.getQuantity() * o.getProduct().getProductPrice());
//                break;
//            }
//        }
//        orderTempSet.add(orderTemp);
//        orderDatabase.setOrderTempSet(orderTempSet);
//        tableViewProductOfTable.getItems().clear();
//        tableViewProductOfTable.setItems(FXCollections.observableArrayList(orderTempSet));
//        tableViewProductOfTable.setStyle("-fx-font-size: 14pt;");
//
//        deleteBtn.setOnAction(e -> {
//            OrderTemp selectedItem = tableViewProductOfTable.getSelectionModel().getSelectedItem();
//            tableViewProductOfTable.getItems().remove(selectedItem);
//        });
//        int result = 0;
//
//        for (OrderTemp orderTemp1 : orderTempSet) {
//            result += orderTemp1.getPriceTemp();
//        }
//        Locale locale = new Locale("vi", "VN");
//        NumberFormat vn = NumberFormat.getInstance(locale);
//        totalPriceText.setText(String.valueOf(vn.format(result)));
//    }

    @FXML
    void onPayBtnClick(ActionEvent event) {
        StringBuilder productStringOnOrderSet = new StringBuilder();
        orderDatabase.setTableId(selectedTableId);
        for (OrderTemp orderTemp : orderDatabase.getOrderTempSet()) {
            orderDatabase.setStatus(1);
            productStringOnOrderSet.append(orderTemp.getProduct().getId());
            productStringOnOrderSet.append(",");
            productStringOnOrderSet.append(orderTemp.getQuantity());
            productStringOnOrderSet.append(",");
            productStringOnOrderSet.append(orderTemp.getProduct().getProductPrice());
            productStringOnOrderSet.append(",");
            orderDatabase.setProductOnOrder(String.valueOf(productStringOnOrderSet));
        }
        int result = 0;
        for (OrderTemp orderTemp1 : orderTempSet) {
            result += orderTemp1.getPriceTemp();
        }
        orderDatabase.setTotalPrice(result);
        if (orderDatabase.getProductOnOrder() == null)
        {
            return;
        }
        orderDatabase.setStatus(2);
        if (statusTable == 0) {
            orderJDBC.insertNewOrder(orderDatabase);
        }
        if (statusTable == 1) {
            orderJDBC.updateOrder(orderDatabase.getId(),orderDatabase);
        }
        orderJDBC.updateStatus2Orderdatabase(orderDatabase.getId());
        tableJDBC.updateStatus0TableByIdTable(orderDatabase.getTableId());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
        Scene scene = null;
        try {
//            int width = (int) Screen.getPrimary().getBounds().getWidth();
//            int height = (int) Screen.getPrimary().getBounds().getHeight();
            Stage stage = (Stage) payBtn.getScene().getWindow();
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

    @FXML
    void onSaveBtnClick(ActionEvent event) {
        StringBuilder productStringOnOrderSet = new StringBuilder();
        orderDatabase.setTableId(selectedTableId);
        for (OrderTemp orderTemp : orderDatabase.getOrderTempSet()) {
            orderDatabase.setStatus(1);
            productStringOnOrderSet.append(orderTemp.getProduct().getId());
            productStringOnOrderSet.append(",");
            productStringOnOrderSet.append(orderTemp.getQuantity());
            productStringOnOrderSet.append(",");
            productStringOnOrderSet.append(orderTemp.getProduct().getProductPrice());
            productStringOnOrderSet.append(",");
            orderDatabase.setProductOnOrder(String.valueOf(productStringOnOrderSet));
        }
        int result = 0;
        for (OrderTemp orderTemp1 : orderTempSet) {
            result += orderTemp1.getPriceTemp();
        }
        orderDatabase.setTotalPrice(result);
        if (statusTable == 0) {
            if (orderDatabase.getProductOnOrder() == null)
            {
                return;
            }
            orderJDBC.insertNewOrder(orderDatabase);
            tableJDBC.updateStatus1TableByIdTable(orderDatabase.getTableId());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
            Scene scene = null;
            try {
//            int width = (int) Screen.getPrimary().getBounds().getWidth();
//            int height = (int) Screen.getPrimary().getBounds().getHeight();
                Stage stage = (Stage) saveBtn.getScene().getWindow();
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
        if (statusTable == 1) {
            if (orderDatabase.getProductOnOrder() == null)
            {
                return;
            }
            orderJDBC.updateOrder(orderDatabase.getId(), orderDatabase);
            tableJDBC.updateStatus1TableByIdTable(orderDatabase.getTableId());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
            Scene scene = null;
            try {
//            int width = (int) Screen.getPrimary().getBounds().getWidth();
//            int height = (int) Screen.getPrimary().getBounds().getHeight();
                Stage stage = (Stage) saveBtn.getScene().getWindow();
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
    }

    @FXML
    void initialize() {
        initByCategory();
        onClickProductHandler();
        editTableViewOrder();
        columnNameProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        columnSL.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("priceTemp"));
    }

    void editTableViewOrder()
    {
        tableViewProductOfTable.setEditable(true);

        columnSL.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnSL.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnSL.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<OrderTemp, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<OrderTemp, Integer> event) {
                OrderTemp orderTemp = event.getRowValue();
                orderTemp.setQuantity(event.getNewValue());
                for (OrderTemp o : orderTempSet) {
                    if (o.getProduct().getProductName().equalsIgnoreCase(orderTemp.getProduct().getProductName())) {
                        o.setPriceTemp(o.getQuantity() * o.getProduct().getProductPrice());
                        break;
                    }
                }
                orderTempSet.add(orderTemp);
                orderDatabase.setOrderTempSet(orderTempSet);
                tableViewProductOfTable.getItems().clear();
                tableViewProductOfTable.setItems(FXCollections.observableArrayList(orderTempSet));
            }
        });
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("priceTemp"));
    }

    @FXML
    private Button tableChangeBtn;
    TableTypeJDBC tableTypeJDBC = new TableTypeJDBC();
    private static final String TABLE_TYPE_DEFAULT = "Bàn Số";
    ObservableList<TableCoffee> listCardTableChange = FXCollections.observableArrayList();
    TabPane tabPaneChangeTable = new TabPane();


    public void setVisibleChangTableBtn(int tableId)
    {
        if (tableId == 0)
        {
            tableChangeBtn.setVisible(false);
        }
    }
    @FXML
    void ontableChangeBtnClcik(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewchangetable.fxml"));
        Scene scene = null;
        try {
            Stage stage = (Stage) tableChangeBtn.getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            ViewChangeTableController viewChangeTableController = fxmlLoader.getController();
            viewChangeTableController.getOrderDatabase(orderDatabase);
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
}
