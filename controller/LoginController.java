package com.example.coffee_rubi_ver.controller;

import com.example.coffee_rubi_ver.JDBCutils.AccountJDBC;
import com.example.coffee_rubi_ver.Main;
import com.example.coffee_rubi_ver.model.login.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TextField resignAccountText;

    @FXML
    private Button resignBtn;

    @FXML
    private TextField accountText;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordText;

    @FXML
    private PasswordField resignConfirmPassText;

    @FXML
    private PasswordField resignPassText;

    @FXML
    private Button closeBtn;
    AccountJDBC accountJDBC = new AccountJDBC();


    @FXML
    void onLoginBtnClick(ActionEvent event) {
        boolean checkLogin = accountJDBC.checkLogin(accountText.getText(), passwordText.getText());
        if (checkLogin)
        {
            goScene(loginBtn);
        }
        else
            System.out.println("sai roi");
    }


    @FXML
    void onResignBtnClick(ActionEvent event) {
        boolean checkResignPass = false;
        if (resignPassText.getText().length() < 6)
        {
            System.out.println("Mật khẩu phải lớn hơn 6 kí tự");
            checkResignPass = true;
        }
        boolean checkResignComfirm = false;
        if (!(resignPassText.getText().equals(resignConfirmPassText.getText())))
        {
            System.out.println("Mật Khẩu sai");
            checkResignComfirm = true;
        }
        boolean accIsExist = accountJDBC.checkNewAccount(resignAccountText.getText().trim());
        if (accIsExist)
        {
            System.out.println("Tài Khoản Đã Tồn Tại");
            return;
        }
        if (!(checkResignPass && checkResignComfirm))
        {
            Account account = new Account();
            account.setUsername(resignAccountText.getText().trim());
            account.setPassword(resignPassText.getText().trim());
            account.setRole(2);
            accountJDBC.insertNewAccount(account);
            System.out.println("Resign Success");
        }
    }

    @FXML
    void onCloseBtnClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void initialize() {
    }

    void goScene(Button button)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("viewtablecoffee.fxml"));
        Scene scene = null;
        try {
            Stage stage = (Stage) button.getScene().getWindow();
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

}
