package com.badblues.person;

import javafx.scene.paint.Color;

public class Candidate extends Person {
    
    private Color color;
    
    public Candidate(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}