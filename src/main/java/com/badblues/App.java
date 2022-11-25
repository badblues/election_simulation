package com.badblues;

import javafx.application.*;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class App extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
        	Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setOnCloseRequest(windowEvent ->  {
				Platform.exit();
				System.exit(0);
			});
			stage.show();
			this.stage = stage;
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        launch();
    }

}