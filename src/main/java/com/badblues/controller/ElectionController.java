package com.badblues.controller;

import com.badblues.model.*;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import java.util.Set;
import java.net.URL;
import java.util.*;
import java.util.LinkedHashSet;
import java.util.Set;


public class ElectionController implements Initializable {

    @FXML
    Pane fieldPane;
    @FXML
    Pane menuPane;
    @FXML
    Button creButton;
    @FXML
    Button clearButton;
    @FXML
    ComboBox<String> modeCB;
    @FXML
    Label winnerLabel;
    @FXML
    Label votesLabel1;
    @FXML
    Label votesLabel2;
    @FXML
    Label votesLabel3;
    @FXML
    Label votesLabel4;
    @FXML
    Label votesLabel5;

    ElectionState state = ElectionState.getInstance();

    Label[] labels;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(state.getModes()[0]);
        modeCB.getItems().addAll(state.getModes());
        modeCB.setValue(state.getModes()[0]);
        labels = new Label[] {votesLabel1, votesLabel2, votesLabel3, votesLabel4, votesLabel5};

    }

    public void createRandomElectors() {
        state.createElectors();
        redraw();
    }

    public void spawnCandidate(MouseEvent event) {
        System.out.println("x = " + event.getX() + " y = " + event.getY());
        int x = (int) event.getX() * state.SCALE_SIZE / (int) fieldPane.getWidth();
        int y = (int) event.getY() * state.SCALE_SIZE / (int) fieldPane.getHeight();

        Person candidate = state.createCandidate(x, y);
        if (candidate == null)
            return;
        Circle circle = new Circle(8, candidate.getColor());
        circle.setCenterX(candidate.getX() * fieldPane.getWidth() / state.SCALE_SIZE);
        circle.setCenterY(candidate.getY() * fieldPane.getHeight() / state.SCALE_SIZE);
        fieldPane.getChildren().addAll(circle);

        state.countVotes();
        redraw();
    }

    public void redraw() {
        fieldPane.getChildren().clear();
        
        Line hline = new Line(0, fieldPane.getHeight()/2, fieldPane.getWidth(), fieldPane.getHeight()/2);
        Line vline = new Line(fieldPane.getWidth()/2, 0, fieldPane.getWidth()/2, fieldPane.getHeight());
        fieldPane.getChildren().addAll(hline, vline);

        for (Person e : state.getElectors()) {
            Circle circle = new Circle(4, e.getColor());
            circle.setCenterX(e.getX() * fieldPane.getWidth() / state.SCALE_SIZE);
            circle.setCenterY(e.getY() * fieldPane.getHeight() / state.SCALE_SIZE);
            fieldPane.getChildren().addAll(circle);
        }
        int i = 0;
        for (Person c : state.getCandidates()) {
            Circle circle = new Circle(8, c.getColor());
            circle.setCenterX(c.getX() * fieldPane.getWidth() / state.SCALE_SIZE);
            circle.setCenterY(c.getY() * fieldPane.getHeight() / state.SCALE_SIZE);
            fieldPane.getChildren().addAll(circle);
            System.out.println("labels:\n" + labels);
            Label lb = labels[i++];
            lb.setText("" + state.getVotes(c));
            lb.setTextFill(c.getColor());
            lb.setVisible(true);
        }

        winnerLabel.setTextFill(state.getWinnerColor());

    }

    public void clearCandidates() {
        state.clearCandidates();
        state.countVotes();
        redraw(); 
    }

    public void changeMode() {
        state.setMode(modeCB.getValue());
    }
}