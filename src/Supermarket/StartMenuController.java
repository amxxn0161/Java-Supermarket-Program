package Supermarket;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;


public class StartMenuController {

    public Button StartShoppingButton;

    public void startShopping(ActionEvent event) throws IOException {
        Parent tableViewProducts = FXMLLoader.load(getClass().getResource("productView.fxml"));
        Scene tableViewScene = new Scene(tableViewProducts);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

        int rand = new Random().nextInt(3) + 1;

        switch (rand) {
            case 1: //Rotation
                RotateTransition rot = new RotateTransition();
                rot.setDuration(Duration.millis(500));
                rot.setFromAngle(0.0);
                rot.setToAngle(360.0);
                rot.setNode(tableViewProducts);
                rot.play();
                break;

            case 2: //Fade
                FadeTransition fader = new FadeTransition();
                fader.setDuration(Duration.millis(2000));
                fader.setFromValue(0.1);
                fader.setNode(tableViewProducts);
                fader.play();
                break;

            case 3: //Scale
                ScaleTransition scaler = new ScaleTransition();
                scaler.setDuration(Duration.millis(500));
                scaler.setFromX(0.1);
                scaler.setToX(1.0);
                scaler.setFromY(0.1);
                scaler.setToY(1.0);
                scaler.setNode(tableViewProducts);
                scaler.play();
                break;
        }
    }
}
