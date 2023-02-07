package com.badblues.controller;

import com.badblues.model.*;
import com.badblues.person.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class ElectionController {

    @FXML
    Button creButton;
    @FXML
    Pane fieldPane;
    @FXML
    Pane menuPane;

    ElectionState state = ElectionState.getInstance();

    public void createRandomElectors() {
        for (int i = 0; i < 1000; i++) {
            Elector elector = state.createElector();
            Circle circle = new Circle(4, Color.GREY);
            circle.setCenterX(elector.getX() * fieldPane.getWidth() / state.SCALE_SIZE);
            circle.setCenterY(elector.getY() * fieldPane.getHeight() / state.SCALE_SIZE);
            fieldPane.getChildren().addAll(circle);
        }
        creButton.setDisable(true);
    }

    public void spawnCandidate(MouseEvent event) {
        System.out.println("x = " + event.getX() + " y = " + event.getY());
        int x = (int) event.getX() * state.SCALE_SIZE / (int) fieldPane.getWidth();
        int y = (int) event.getY() * state.SCALE_SIZE / (int) fieldPane.getHeight();

        Candidate candidate = state.createCandidate(x, y);
        if (candidate == null)
            return;
        Circle circle = new Circle(8, candidate.getColor());
        circle.setCenterX(candidate.getX() * fieldPane.getWidth() / state.SCALE_SIZE);
        circle.setCenterY(candidate.getY() * fieldPane.getHeight() / state.SCALE_SIZE);
        fieldPane.getChildren().addAll(circle);
    }
    
}