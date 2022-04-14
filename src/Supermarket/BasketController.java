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

public class BasketController {

    public ListView<Product> lvBasket;
    public Label lblTotalPrice;
    public Button btnTenOrLess;
    public Button btnCashOnly;
    public Button btnGeneral;

      private ObservableList<Product> basket;

    private BasketUtils bu = new BasketUtils();

    @FXML
    private void initialize() {
    }

    public void passBasket(ObservableList<Product> basket) {
        this.basket = basket;
        lvBasket.setItems(this.basket);
        double totalPrice = getTotalPrice();
        System.out.println("Total Price :");
        System.out.println(bu.getMoney(totalPrice));
        lblTotalPrice.setText("Total Price: " + bu.getMoney(totalPrice));
    }


    public double getTotalPrice(){
        double total = 0;
        for(Product pr : basket){
            total += pr.getPrice();
        }
        return total;
    }

    public void addProduct(ActionEvent event) throws IOException {
        Parent windowProduct = FXMLLoader.load(getClass().getResource("productView.fxml"));
        Scene ProductScene = new Scene(windowProduct);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }

    public void removeProduct(ActionEvent actionEvent) {
        int selectionNumber = lvBasket.getSelectionModel().getSelectedIndex();
        if(selectionNumber == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to remove", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Product pr = lvBasket.getItems().get(selectionNumber);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + pr.toString() + "?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.YES){
            basket.remove(selectionNumber);
            getTotalPrice();
            NumberFormat gbp = NumberFormat.getCurrencyInstance(Locale.UK);
            lblTotalPrice.setText("Total Price : " + gbp.format(getTotalPrice()));
        }
    }

    public void returnToProductView(ActionEvent event) throws IOException {
        Parent windowProduct = FXMLLoader.load(getClass().getResource("productView.fxml"));
        Scene ProductScene = new Scene(windowProduct);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show();
    }

    public void openCashOnly(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cashonly.fxml"));
        Parent parent = loader.load();
        //Get controller of scene2 from the FXMLLoader created above
        CashOnlyController cashOnlyController = loader.getController();
        //Pass whatever data you want. You can have multiple method calls here
        cashOnlyController.passCashOnly(basket);


        //Now to change the screen from 1 to 2
        //Get the current stage from any control, in this case the button that was clicked.
        Stage stage = (Stage) btnCashOnly.getScene().getWindow();
        //Set the scene with the new root (created from the loaded.load() above)
        stage.setScene(new Scene(parent));
        stage.setTitle("Cash Only Checkout");

    }

    public void openGeneral(ActionEvent event) throws IOException {
        //Load second scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("general.fxml"));
        Parent parent = loader.load();
        //Get controller of scene2 from the FXMLLoader created above
        GeneralController generalController = loader.getController();
        //Pass whatever data you want. You can have multiple method calls here
        generalController.passGeneral(basket);


        //Now to change the screen from 1 to 2
        //Get the current stage from any control, in this case the button that was clicked.
        Stage stage = (Stage) btnTenOrLess.getScene().getWindow();
        //Set the scene with the new root (created from the loaded.load() above)
        stage.setScene(new Scene(parent));
        stage.setTitle("General Checkout");

    }

    public void openTenOrLess (ActionEvent event) throws IOException {

        if(basket.size() <10) {

            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tenorless.fxml"));
            Parent parent = loader.load();
            //Get controller of scene2 from the FXMLLoader created above
            TenOrLessController tenOrLessController = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            tenOrLessController.passTenOrLess(basket);


            //Now to change the screen from 1 to 2
            //Get the current stage from any control, in this case the button that was clicked.
            Stage stage = (Stage) btnTenOrLess.getScene().getWindow();
            //Set the scene with the new root (created from the loaded.load() above)
            stage.setScene(new Scene(parent));
            stage.setTitle("Ten or Less");

        }else{
            Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
            programAlert.setTitle("Ten Products or Less");
            programAlert.setContentText("You currently have more than ten products please try the general checkout instead");
            programAlert.setHeaderText("Current basket contains greater than ten items");
            programAlert.showAndWait();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        try {
            FileOutputStream out = new FileOutputStream("addedproducts.dat");
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(new ArrayList<Product>(basket));
            oOut.flush();
            oOut.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Failed to Save the Data", ButtonType.OK);
            alert.showAndWait();
            System.err.println("Error : " + e);
        }
    }

    @FXML
    public void load(ActionEvent actionEvent) {
        try {
            FileInputStream in = new FileInputStream("addedproducts.dat");
            ObjectInputStream oIn = new ObjectInputStream(in);
            List<Product> list = (List<Product>)oIn.readObject();
            System.out.println(list);
            basket = FXCollections.observableArrayList(list);
            lvBasket.setItems(basket);
            oIn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public void aboutProgram(ActionEvent actionEvent) {
        Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
        programAlert.setTitle("About Program");
        programAlert.setContentText("Supermarket Program © 2021");
        programAlert.setHeaderText("Program Information");
        programAlert.showAndWait();
    }

}
