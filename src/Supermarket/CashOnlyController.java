package Supermarket;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CashOnlyController extends BasketController {

    public ListView lvCashOnly;
    public ListView<Product> productList;
    public ListView<Product> lvTenOrLess;
    public Label lblTotalPrice;
    public Button cashBtn;

    private ObservableList<Product> cashonly = FXCollections.observableArrayList();
    private ObservableList<Product> cash = FXCollections.observableArrayList();
    private BasketUtils bu = new BasketUtils();

    @FXML
    private void initialize() {
    }

    public void passCashOnly(ObservableList<Product> cashonly) {
        this.cashonly = cashonly;
        lvCashOnly.setItems(this.cashonly);
        double totalPrice = getTotalPrice();
        System.out.println("Total Price :");
        System.out.println(bu.getMoney(totalPrice));
        lblTotalPrice.setText("Total Price: " + bu.getMoney(totalPrice));
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product pr : cashonly) {
            total += pr.getPrice();
        }
        return total;
    }

    public void aboutProgram(ActionEvent actionEvent) {
        Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
        programAlert.setTitle("About Program");
        programAlert.setContentText("Supermarket Program © 2021");
        programAlert.setHeaderText("Program Information");
        programAlert.showAndWait();
    }


    public void save(ActionEvent actionEvent) {
        try {
            FileOutputStream out = new FileOutputStream("productlist.dat");
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(new ArrayList<Product>(cashonly));
            oOut.flush();
            oOut.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Failed to save data", ButtonType.OK);
            alert.showAndWait();
            System.err.println("Error : " + e);
        }
    }

    public void load(ActionEvent actionEvent) {
        try {
            FileInputStream in = new FileInputStream("addedproducts.dat");
            ObjectInputStream oIn = new ObjectInputStream(in);
            List<Product> list = (List<Product>) oIn.readObject();
            cashonly.setAll(list);
            productList.setItems(cashonly);
            oIn.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Failed to load data", ButtonType.OK);
            alert.showAndWait();
            System.out.println("Error : " + e);
        }
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void returnToBasket(ActionEvent event) throws IOException {
        Parent windowCashonly = FXMLLoader.load(getClass().getResource("basket.fxml"));
        Scene ProductScene = new Scene(windowCashonly);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }

    public void openCash(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("cash.fxml"));
            Parent parent = loader.load();
            CashController cashController = loader.getController();
            cashController.dataReceiver(this.cashonly);

            Stage stage = (Stage) cashBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }

    }

}