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
import java.util.Optional;

public class TenOrLessController {

    public ListView<Product> productList;
    public ListView<Product> lvTenOrLess;
    public Label lblTotalPrice;
    public Button cashBtn;
    public Button voucherBtn;

    private ObservableList<Product> tenorless;

    private BasketUtils bu = new BasketUtils();

    @FXML
    private void initialize() {
    }

    public void passTenOrLess(ObservableList<Product> tenorless) {
        this.tenorless = tenorless;
        lvTenOrLess.setItems(this.tenorless);
        double totalPrice = getTotalPrice();
        System.out.println("Total Price :");
        System.out.println(bu.getMoney(totalPrice));
        lblTotalPrice.setText("Total Price: " + bu.getMoney(totalPrice));
    }

    public double getTotalPrice(){
        double total = 0;
        for(Product pr : tenorless){
            total += pr.getPrice();
        }
        return total;
    }

    public void save(ActionEvent actionEvent) {
        try {
            FileOutputStream out = new FileOutputStream("productlist.dat");
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(new ArrayList<Product>(tenorless));
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
            FileInputStream in = new FileInputStream("addedProduct.dat");
            ObjectInputStream oIn = new ObjectInputStream(in);
            List<Product> list = (List<Product>)oIn.readObject();
            tenorless.setAll(list);
            productList.setItems(tenorless);
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

    public void previousPage(ActionEvent event) throws IOException {
        Parent windowProduct = FXMLLoader.load(getClass().getResource("basket.fxml"));
        Scene ProductScene = new Scene(windowProduct);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }

    public void aboutProgram(ActionEvent actionEvent) {
        Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
        programAlert.setTitle("About Program");
        programAlert.setContentText("Supermarket Program © 2021");
        programAlert.setHeaderText("Program Information");
        programAlert.showAndWait();
    }

    public void removeProduct(ActionEvent actionEvent) {
        int selectionNumber = lvTenOrLess.getSelectionModel().getSelectedIndex();
        if (selectionNumber == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to remove", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Product pr = lvTenOrLess.getItems().get(selectionNumber);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + pr.toString() + "?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.YES) {
            tenorless.remove(selectionNumber);
            getTotalPrice();
            NumberFormat gbp = NumberFormat.getCurrencyInstance(Locale.UK);
            lblTotalPrice.setText("Total Price : " + gbp.format(getTotalPrice()));
        }
    }

    public void returnToBasket(ActionEvent event) throws IOException {
        Parent windowTenOrLess = FXMLLoader.load(getClass().getResource("basket.fxml"));
        Scene ProductScene = new Scene(windowTenOrLess);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }

    public void openCash(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cash.fxml"));
            Parent parent = loader.load();
            CashController cashController = loader.getController();
            cashController.dataReceiver(this.tenorless);

            Stage stage = (Stage) cashBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void openCard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
        Parent parent = loader.load();
        Scene tableViewScene = new Scene(parent);
        CardController cardController = loader.getController();
        cardController.passTotal(lblTotalPrice.getText());

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        window.setTitle("Card Payment");
    }

    public void openVoucher(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("voucher.fxml"));
            Parent parent = loader.load();
            VoucherController voucherController = loader.getController();
            voucherController.dataReceiver(this.tenorless);

            Stage stage = (Stage) voucherBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
