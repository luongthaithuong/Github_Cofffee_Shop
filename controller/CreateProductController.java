package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.CategoryJDBC;
import com.example.coffee_rubi_ver.JDBCutils.OrderJDBC;
import com.example.coffee_rubi_ver.JDBCutils.ProductJDBC;
import com.example.coffee_rubi_ver.JDBCutils.TableJDBC;
import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.model.order.OrderDatabase;
import com.example.coffee_rubi_ver.model.order.OrderTemp;
import com.example.coffee_rubi_ver.model.product.Category;
import com.example.coffee_rubi_ver.model.product.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateProductController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private Label goBackToViewTableCoffee;

    @FXML
    private TextField newProductNameText;

    @FXML
    private TextField newProductPriceText;

    @FXML
    private TabPane tapPaneViewProduct;
    @FXML
    private Button creatProductBtn;




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

    @FXML
    void onCreatProductBtnClick(ActionEvent event) {
        String productName = newProductNameText.getText().trim();
        int productPrice = Integer.parseInt(newProductPriceText.getText().trim());
        String categoryName = tapPaneViewProduct.getSelectionModel().getSelectedItem().getText();
        Category category = categoryJDBC.getCategoryByName(categoryName);

        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setCategory(category.getId());
        productJDBC.insertProduct(product);
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
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editcardproduct.fxml"));
                        AnchorPane pane = fxmlLoader.load();
                        EditCardProductController editCardProductController = fxmlLoader.getController();
                        editCardProductController.setController(this);
                        editCardProductController.setData(cardListData.get(i));

                        if (column == 3) {
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
            tapPaneViewProduct.getTabs().add(tapNew);
            tapPaneViewProduct.setStyle("-fx-font-size: 20pt;");
            tapNew.setOnCloseRequest(event -> {
                Dialog<String> dialogDelete = new Dialog<>();
                dialogDelete.setTitle("Rubi");
                dialogDelete.setHeaderText("Xóa hết Món trước khi xóa loại món \nXóa "+tapNew.getText()+" Khỏi Danh Sách ?");
                ButtonType okDone = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
                dialogDelete.getDialogPane().getButtonTypes().addAll(okDone, ButtonType.CANCEL);
                dialogDelete.setResultConverter(dialogButton -> {
                    if (dialogButton == okDone)
                    {
                        String categoryName = tapNew.getText();
                        categoryJDBC.deleteCategoryName(categoryName);
                    }
                    return null;
                });
                dialogDelete.showAndWait();
            });
        }
        tapPaneViewProduct.getTabs().add(newTabButton(tapPaneViewProduct));
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
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editcardproduct.fxml"));
                    AnchorPane pane = fxmlLoader.load();
                    EditCardProductController editCardProductController = fxmlLoader.getController();
                    editCardProductController.setController(this);
                    editCardProductController.setData(cardListData.get(i));

                    if (column == 3) {
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

    private OrderDatabase orderDatabase = new OrderDatabase();
    private Set<OrderTemp> orderTempSet = new HashSet<>();;

    private OrderJDBC orderJDBC = new OrderJDBC();
    private TableJDBC tableJDBC = new TableJDBC();




    private Tab newTabButton(TabPane tabPane) {
        Tab addTab = new Tab("+");
        addTab.setClosable(false);
        addTab.setStyle("-fx-font-size: 20pt;");
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(newTab == addTab) {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Rubi");
                dialog.setHeaderText("Tên Loại Đồ Uống : ");
                ButtonType okDone = new ButtonType("Tạo Mới", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(okDone, ButtonType.CANCEL);
                TextField categoryText = new TextField();
                categoryText.setPromptText("Tên Loại Đồ Uống");
                Node okDoneButton = dialog.getDialogPane().lookupButton(okDone);
                okDoneButton.setDisable(true);
                categoryText.textProperty().addListener((observableValue, oldValue, newValue) ->{
                    okDoneButton.setDisable(newValue.trim().isEmpty());
                });
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == okDone)
                    {
                        tabPane.getTabs().add(tabPane.getTabs().size() - 1, new Tab(categoryText.getText()));
                        String categoryName = categoryText.getText().trim();
                        Category category = new Category();
                        category.setCategoryName(categoryName);
                        categoryJDBC.insertNewCategory(category);
                    }
                    return null;
                });
                dialog.getDialogPane().setContent(categoryText);
                dialog.showAndWait();
                tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
            }
        });
        return addTab;
    }

    void showCreateBtn()
    {
        creatProductBtn.setDisable(true);
        newProductNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            creatProductBtn.setDisable(newValue.trim().isEmpty());
        });
    }

    @FXML
    void initialize() {
        initByCategory();
        onClickProductHandler();
        showCreateBtn();
    }
}

