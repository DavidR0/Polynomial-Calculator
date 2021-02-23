package org.int32_t;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/view.fxml"));
        primaryStage.setTitle("Polynomial Calculator");
        primaryStage.setScene(new Scene(root, 610, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


