package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.model.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class CardProductController {
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

    ViewOrderOfTableCoffeeController viewOrderOfTableCoffeeController = null;

    public void setData (Product product)
    {
        this.product = product;
        this.productNameText.setText(product.getProductName());
        this.productPriceText.setText(String.valueOf(vn.format(product.getProductPrice()))+" Vnđ");
    }

    public void setController(ViewOrderOfTableCoffeeController view)
    {
        viewOrderOfTableCoffeeController = view;
    }

    @FXML
    void onClickProduct(MouseEvent event) {
        try {
            viewOrderOfTableCoffeeController.addProduct(product.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {

    }
}
