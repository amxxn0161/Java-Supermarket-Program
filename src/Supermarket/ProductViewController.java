package Supermarket;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ProductViewController {

    public ListView<Product> productList;
    public Button btnAddToBasket;
    public TextField productCodeTF;
    private ProductList listOfProducts;
    private String voucherCode = "TENOFF";
    private int activeProduct = -1;

    private ObservableList<Product> basket = FXCollections.observableArrayList();

    @FXML
    private void initialize()
    {
        listOfProducts = new ProductList();
        productList.setItems(listOfProducts);
    }

    public void addProduct(ActionEvent actionEvent) {
        TextInputDialog td = new TextInputDialog("Enter product name");
        td.setHeaderText("Please provide a product name");
        td.showAndWait();
        String newName = td.getEditor().getText();

        td = new TextInputDialog("Enter product description");
        td.setHeaderText("Please provide a product description");
        td.showAndWait();
        String newDescription = td.getEditor().getText();

        td = new TextInputDialog("Enter product code");
        td.setHeaderText("Please provide a product code");
        td.showAndWait();
        String newCode = td.getEditor().getText();

        td = new TextInputDialog("Enter product price");
        td.setHeaderText("Please provide a product price");
        td.showAndWait();
        String newPrice = td.getEditor().getText();
        double price = Double.parseDouble(newPrice);

        if (newName.equals("") || newDescription.equals("") || newCode.equals("") || newPrice.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Full Product Details", ButtonType.OK);
            alert.showAndWait();
        } else {
            listOfProducts.addProduct(newName, newDescription, newCode, price);
        }

    }

        public void removeProduct(ActionEvent actionEvent) {

        int selectionNumber = productList.getSelectionModel().getSelectedIndex();

            if (selectionNumber == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Select a product to remove", ButtonType.OK);
                alert.showAndWait();
            } else {
                Product pr = productList.getItems().get(selectionNumber);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + pr + " ?",
                        ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    listOfProducts.removeProduct(pr.getBrand(), pr.getDescription(),
                            pr.getCode(), pr.getPrice());
                    if (selectionNumber == activeProduct){
                        activeProduct = 1;
                    }
                }
            }
        }

        public void viewBasket (ActionEvent event) throws IOException {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("basket.fxml"));
            Parent parent = loader.load();
            //Get controller of scene2 from the FXMLLoader created above
            BasketController basketcontroller = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            basketcontroller.passBasket(basket);


            //Now to change the screen from 1 to 2
            //Get the current stage from any control, in this case the button that was clicked.
            Stage stage = (Stage) btnAddToBasket.getScene().getWindow();
            //Set the scene with the new root (created from the loaded.load() above)
            stage.setScene(new Scene(parent));
            stage.setTitle("Basket");
        }

        //Listener for exit button
    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    //Listener for when about button is clicked
    public void aboutProgram(ActionEvent actionEvent) {
        Alert programAlert = new Alert(Alert.AlertType.INFORMATION);
        programAlert.setTitle("About Program");
        programAlert.setContentText("Supermarket Program © 2021");
        programAlert.setHeaderText("Program Information");
        programAlert.showAndWait();
    }

    @FXML
    private void save(){
        try {
            FileOutputStream out = new FileOutputStream("productlist.dat");
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(new ArrayList<Product>(listOfProducts));
            oOut.flush();
            oOut.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Failed to save data", ButtonType.OK);
            alert.showAndWait();
            System.err.println("Error : " + e);
        }
    }

    @FXML
    private void load(){
        try {
            FileInputStream in = new FileInputStream("productlist.dat");
            ObjectInputStream oIn = new ObjectInputStream(in);
            List<Product> list = (List<Product>)oIn.readObject();
            listOfProducts.setAll(list);
            productList.setItems(listOfProducts);
            oIn.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Failed to load data", ButtonType.OK);
            alert.showAndWait();
            System.out.println("Error : " + e);
        }
    }
    
    public void sortProducts(){
        Collections.sort(listOfProducts);
    }

    public void addToBasket(ActionEvent actionEvent) {

        //Step 1 get selected product from the product list view
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        System.out.println(selectedProduct);
        if (selectedProduct != null) {
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to add product to basket " + " ?",
             ButtonType.YES, ButtonType.NO);
             alert.showAndWait();

             if (alert.getResult() == ButtonType.YES) {
                 basket.add(selectedProduct);
                 System.out.println("Your Basket :");
                 System.out.println(basket);
             }
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a product !");
            alert.showAndWait();
        }

    }

    @FXML
    public void scanProductPressed(ActionEvent actionEvent) {
        Product searched;
        String findProductCode = productCodeTF.getText();

        if (findProductCode.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter Product Code", ButtonType.OK);
            alert.showAndWait();
        }else{
            searched = listOfProducts.findProductByCode(findProductCode);

            if(searched == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Product could not be found", ButtonType.OK);
                alert.showAndWait();
            }else{
                productList.getSelectionModel().select(searched);
                basket.add(searched);

            }
        }
    }
}