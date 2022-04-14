package Supermarket;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CardController extends BasketController{

    public TextField accountHolderTF;
    public TextField cardNumberTF;
    public TextField expirydateTF;
    public TextField cvvTF;
    public Label lblTotal;
    private String total;

    private ObservableList<Product> basket;

    private BasketUtils bu = new BasketUtils();

    @FXML
    private void initialize() {
    }

    public void returnToBasket(ActionEvent event) throws IOException {
        Parent windowCard = FXMLLoader.load(getClass().getResource("basket.fxml"));
        Scene ProductScene = new Scene(windowCard);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }

    public void passTotal(String total){
        this.total = total;
        lblTotalPrice.setText("" + total);
    }

    public void pay(ActionEvent actionEvent) {

        if (accountHolderTF.getText().equals("") || cardNumberTF.getText().equals("") || expirydateTF.getText().equals("") || cvvTF.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please do not leave field empty", ButtonType.OK);
            alert.showAndWait();
        }

        if (cardNumberTF.getText().length() != 16) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Card number must consist of 16 digits", ButtonType.OK);
            alert.showAndWait();

        } else {
            Alert programAlert = new Alert(Alert.AlertType.CONFIRMATION);
            programAlert.setTitle("Card Payment");
            programAlert.setContentText("Pay" + " " + lblTotalPrice.getText() + "?");
            programAlert.setHeaderText("");
            programAlert.showAndWait();

            Alert programWarning = new Alert(Alert.AlertType.CONFIRMATION);
            programWarning.setTitle("Card Payment");
            programWarning.setContentText("Your payment has been successful");
            programWarning.setHeaderText("Thank you for shopping");
            programWarning.showAndWait();
        }
    }


    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void aboutProgram(ActionEvent actionEvent) {
        Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
        programAlert.setTitle("About Program");
        programAlert.setContentText("Supermarket Program © 2021");
        programAlert.setHeaderText("Program Information");
        programAlert.showAndWait();
    }

}
