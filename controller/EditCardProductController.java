package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.ProductJDBC;
import com.example.coffee_rubi_ver.model.product.Product;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditCardProductController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label productNameText;

    @FXML
    private Label productPriceText;
    @FXML
    private AnchorPane productView;

    Product product = new Product();
    Locale locale = new Locale("vi", "VN");
    NumberFormat vn = NumberFormat.getInstance(locale);

    CreateProductController createProductController = null;

    public void setData (Product product)
    {
        this.product = product;
        this.productNameText.setText(product.getProductName());
        this.productPriceText.setText(String.valueOf(vn.format(product.getProductPrice()))+" Vnđ");
    }

    public void setController(CreateProductController view)
    {
        createProductController = view;
    }

    ProductJDBC productJDBC = new ProductJDBC();
    @FXML
    void onClickProduct(MouseEvent event) {
        Dialog<Pair<String,String>> dialogDelete = new Dialog<>();
        dialogDelete.setTitle("Rubi");
        ButtonType delProduct = new ButtonType("Xóa Món", ButtonBar.ButtonData.OK_DONE);
        ButtonType editProduct = new ButtonType("Chỉnh Sửa", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogDelete.getDialogPane().getButtonTypes().addAll(delProduct,editProduct);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20,150,10,10));

        TextField productName = new TextField();
        TextField productPrice = new TextField();

        gridPane.add(productName, 1,0);
        gridPane.add(productPrice, 1, 1);
        productName.setPromptText(product.getProductName());
        productPrice.setPromptText(String.valueOf(product.getProductPrice()));

        Node okDoneButton = dialogDelete.getDialogPane().lookupButton(editProduct);
        okDoneButton.setDisable(true);
        productName.textProperty().addListener((observableValue, oldValue, newValue) ->{
            okDoneButton.setDisable(newValue.trim().isEmpty());
        });
        dialogDelete.setResultConverter(dialogButton -> {
            if (dialogButton == editProduct)
            {
                String newNameProduct = productName.getText();
                int newPriceProduct = Integer.parseInt(productPrice.getText());
                int idTable = product.getId();
                productJDBC.editProduct(newNameProduct,newPriceProduct,idTable);
            }
            if (dialogButton == delProduct){
                productJDBC.deleteProduct(product.getId());
            }
            return null;
        });
        dialogDelete.getDialogPane().setContent(gridPane);
        dialogDelete.showAndWait();
    }

    @FXML
    void initialize() {

    }
}
