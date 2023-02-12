package com.badblues;

import javafx.application.*;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

//TODO: fix spawning candidates before electors
//TODO: debug work of Vote for one
//TODO: made Rate all, Vote for any
//TODO: make shit pretty a lil bit
//TODO: tie data to view without controller


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