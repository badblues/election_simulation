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
import javafx.scene.control.Slider;


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
    @FXML
    Slider voteRadiusSlider;

    ElectionState state = ElectionState.getInstance();

    Label[] labels;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modeCB.getItems().addAll(state.getModes());
        modeCB.setValue(state.getModes()[0]);
        labels = new Label[] {votesLabel1, votesLabel2, votesLabel3, votesLabel4, votesLabel5};
        state.createElectors();
    }

    public void createRandomElectors() {
        state.createElectors();
        state.countVotes();
        redraw();
    }

    public void spawnCandidate(MouseEvent event) {
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
        for (int i = 0; i < 5; i++)
            labels[i].setVisible(false);    
        
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
            Circle circle = new Circle(10, c.getColor());
            double x = c.getX() * fieldPane.getWidth() / state.SCALE_SIZE;
            double y = c.getY() * fieldPane.getHeight() / state.SCALE_SIZE;
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setStroke(Color.BLACK);
            fieldPane.getChildren().addAll(circle);
            Label lb = labels[i++];
            lb.setText("" + state.getVotes(c));
            lb.setTextFill(c.getColor());
            lb.setVisible(true);
            if (state.getMode() == "Vote for any") {
                Circle radiusCircle = new Circle(state.getVoteRadius() * fieldPane.getWidth() / state.SCALE_SIZE);
                radiusCircle.setCenterX(x);
                radiusCircle.setCenterY(y);
                radiusCircle.setFill(Color.color(0,0,0,0));
                radiusCircle.setStroke(c.getColor());
                fieldPane.getChildren().addAll(radiusCircle);
            }
        }

        winnerLabel.setTextFill(state.getWinnerColor());

    }

    public void clearCandidates() {
        state.clearCandidates();
        state.countVotes();
        redraw(); 
    }

    public void changeMode() {
        String mode = modeCB.getValue();
        if (mode == "Vote for any")
            voteRadiusSlider.setVisible(true);
        else
            voteRadiusSlider.setVisible(false);
        state.setMode(mode);
        state.countVotes();
        redraw(); 
    }

    public void sliderChange() {
        state.setVoteRadius((int)voteRadiusSlider.getValue());
        state.countVotes();
        redraw();
    }

}