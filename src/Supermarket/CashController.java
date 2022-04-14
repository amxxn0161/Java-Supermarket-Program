package Supermarket;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public class CashController {

    public TextField amountToPayTF;
    public ListView<Product>lvCashOnly;
    public Label lblTotal;
    private double total;
    private ObservableList<Product> cashonly;

    private BasketUtils bu = new BasketUtils();

    @FXML
    private void initialize() {
    }

    public void aboutProgram(ActionEvent actionEvent) {
        Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
        programAlert.setTitle("About Program");
        programAlert.setContentText("Supermarket Program © 2021");
        programAlert.setHeaderText("Program Information");
        programAlert.showAndWait();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product pr : cashonly) {
            total += pr.getPrice();
        }
        return total;
    }

    public void dataReceiver(ObservableList<Product> cashonly){
        this.cashonly = cashonly;
        lblTotal.setText("Amount to Pay:" + " " + bu.getMoney(getTotalPrice()));
    }


    public void pay(ActionEvent actionEvent) {
        try {
            double paymentAmount = Double.parseDouble(amountToPayTF.getText());
            System.out.println(bu.getMoney(getTotalPrice()));
            if(paymentAmount >= getTotalPrice()){
                double change = paymentAmount - getTotalPrice();
                Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
                programAlert.setTitle("Payment Complete");
                programAlert.setContentText("Change Due:" + " " + bu.getMoney(change));
                programAlert.setHeaderText("Thank you for your payment");
                programAlert.showAndWait();
            }else{
                double outstanding = getTotalPrice() - paymentAmount;
                Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
                programAlert.setTitle("Payment Incomplete");
                programAlert.setContentText("Outstanding Amount:" + " " + bu.getMoney(outstanding));
                programAlert.setHeaderText("Please increase your payment");
                programAlert.showAndWait();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    public void returnToBasket(ActionEvent event) throws IOException {
        Parent windowBasket = FXMLLoader.load(getClass().getResource("basket.fxml"));
        Scene ProductScene = new Scene(windowBasket);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void cashEnteredTF(ActionEvent actionEvent) {
        System.out.println(amountToPayTF.getText());
    }

    public void removeProduct(ActionEvent event){
        int selectionNumber = lvCashOnly.getSelectionModel().getSelectedIndex();
        if (selectionNumber == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to remove", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Product pr = lvCashOnly.getItems().get(selectionNumber);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + pr.toString() + "?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.YES) {
            cashonly.remove(selectionNumber);
            getTotalPrice();
            NumberFormat gbp = NumberFormat.getCurrencyInstance(Locale.UK);
            lblTotal.setText("Total Price : " + gbp.format(getTotalPrice()));
        }
    }
}
