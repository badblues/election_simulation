package com.badblues.model;

import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.Map;
import java.util.Random;
import javafx.scene.paint.Color;
import java.lang.Math;


public class ElectionState {


    Set<Person> electors = new HashSet<Person>();
    Map<Person, Integer> candidates = new HashMap<Person, Integer>();
    Random random = new Random();
    private static ElectionState instance;
    public static final int SCALE_SIZE = 1000;
    public static final int ELECTORS_NUM = 1000;
    private int candidatesCounter = 0;
    Map<Integer, Color> colors = new HashMap<Integer, Color>(){{
        put(0, Color.RED);
        put(1, Color.BLUE);
        put(2, Color.GREEN);
        put(3, Color.YELLOW);
        put(4, Color.PURPLE);
    }};
    private Person winner = null;
    String[] modes = {"Vote for one", "Rank all", "Vote for any"};
    String mode = modes[0];
    

    public static synchronized ElectionState getInstance() {
		if(instance == null)
			instance = new ElectionState();
		return instance;
	}

    public Person createElector(int x, int y) {
        Person elector = new Person(x,y, Color.GREY);
        electors.add(elector);
        return elector;
    }

    public void createElectors() {
        electors.clear();
        for (int i = 0; i < ELECTORS_NUM; i++) {
            int x = random.nextInt(SCALE_SIZE + 1);
            int y = random.nextInt(SCALE_SIZE + 1);
            createElector(x, y);
        }
    }

    public Person createCandidate(int x, int y) {
        if (candidatesCounter == 5)
            return null;
        Person candidate = new Person(x, y, colors.get(candidatesCounter));
        candidates.put(candidate, 0);
        candidatesCounter++;
        return candidate;
    }

    public void countVotes() {
        if (candidates.isEmpty()) {
            for (Person e : electors)
                e.setColor(Color.GREY);
            winner = null;
        } else {
            for (Integer i : candidates.values())
                i = 0;
            switch(mode) {
                case "Vote for one":
                    voteForOne();
                    break;
                case "Rank all":
                    rankAll();
                    break;
                case "Vote for any":
                    voteForAny();
                    break;
            }
            winner = (Person)candidates.keySet().toArray()[0];
            for (Person candidate : candidates.keySet()) {
                if (candidates.get(candidate) > candidates.get(winner))
                    winner = candidate;
            }

        }
    }

    private void voteForOne() {
        for (Person e : electors) {
            Person firstCandidate = (Person) orderCandidates(e).values().toArray()[0];
            Integer v = candidates.get(firstCandidate);
            candidates.replace(firstCandidate, v + 1);
            Color color = firstCandidate.getColor();
            e.setColor(color);
        }
    }

    private void rankAll() {
        for (Person e : electors) {
            Person firstCandidate = (Person) orderCandidates(e).values().toArray()[0];
            Integer v = candidates.get(firstCandidate);
            candidates.replace(firstCandidate, v + 1);
            Color color = firstCandidate.getColor();
            e.setColor(color);
        }
    }

    private void voteForAny() {
        for (Person e : electors) {
            Person firstCandidate = (Person) orderCandidates(e).values().toArray()[0];
            Integer v = candidates.get(firstCandidate);
            candidates.replace(firstCandidate, v + 1);
            Color color = firstCandidate.getColor();
            e.setColor(color);
        }
    }

    public Color getWinnerColor() {
        if (winner == null)
            return Color.GREY;
        return winner.getColor();
    } 

    public Map<Double, Person> orderCandidates(Person elector) {
        Map<Double, Person> orderedCandidates = new TreeMap<Double, Person>(); 
        for (Person candidate : candidates.keySet()) {
            double len = Math.sqrt(Math.pow(elector.getX() - candidate.getX(),2) + Math.pow(elector.getY() - candidate.getY(),2));
            orderedCandidates.put(len, candidate);
        }
        return orderedCandidates;
    }

    public void clearCandidates() {
        candidates.clear();
        winner = null;
        candidatesCounter = 0;
    }

    public Set<Person> getElectors() {
        return electors;
    }

    public Set<Person> getCandidates() {
        return candidates.keySet();
    }

    public String[] getModes() {
        return modes;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}