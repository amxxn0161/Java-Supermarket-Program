package Supermarket;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class VoucherController {
    public TextField voucherCodeTF;
    public Label lblTotal;
    public Button enterBtn;
    private ObservableList<Product> voucher;
    private double price;
    private String voucherCode = "TENOFF";

    private BasketUtils bu = new BasketUtils();


    public void close(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void about(ActionEvent actionEvent) {
        Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
        programAlert.setTitle("About Program");
        programAlert.setContentText("Supermarket Program © 2021");
        programAlert.setHeaderText("Program Information");
        programAlert.showAndWait();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product pr : voucher) {
            total += pr.getPrice();
        }
        return total;
    }

    public void dataReceiver(ObservableList<Product> voucher){
        this.voucher = voucher;
        lblTotal.setText("Amount to Pay:" + " " + bu.getMoney(getTotalPrice()));
    }

    public void voucherCode(){
        if(voucherCode.equals(voucherCodeTF.getText())){
            price = getTotalPrice() * 0.9;
            NumberFormat gbp = NumberFormat.getCurrencyInstance(Locale.UK);
            lblTotal.setText("Amount to Pay :" + gbp.format(price));
        }
    }

    public void enter(ActionEvent actionEvent) {
        voucherCode();
    }

    public void returnToBasket(ActionEvent event) throws IOException {
        Parent windowVoucher = FXMLLoader.load(getClass().getResource("basket.fxml"));
        Scene ProductScene = new Scene(windowVoucher);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }
}
