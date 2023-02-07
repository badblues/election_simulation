package com.badblues.model;

import com.badblues.person.*;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Random;
import javafx.scene.paint.Color;


public class ElectionState {


    Set<Elector> electors = new HashSet<Elector>();
    Set<Candidate> candidates = new HashSet<Candidate>();
    Random random = new Random();
    private static ElectionState instance;

    public static final int SCALE_SIZE = 1000;
    private int candidatesCounter = 0;
    Map<Integer, Color> colors = new HashMap<Integer, Color>(){{
        put(0, Color.RED);
        put(1, Color.BLUE);
        put(2, Color.GREEN);
        put(3, Color.YELLOW);
        put(4, Color.PURPLE);
    }};
    

    public static synchronized ElectionState getInstance() {
		if(instance == null)
			instance = new ElectionState();
		return instance;
	}

    public Elector createElector() {
        int x = random.nextInt(SCALE_SIZE + 1);
        int y = random.nextInt(SCALE_SIZE + 1);
        Elector elector = new Elector(x,y);
        electors.add(elector);
        return elector;
    }

    public Elector createElector(int x, int y) {
        Elector elector = new Elector(x,y);
        electors.add(elector);
        return elector;
    }

    public Candidate createCandidate() {
        if (candidatesCounter == 5)
            return null;
        int x = random.nextInt(SCALE_SIZE + 1);
        int y = random.nextInt(SCALE_SIZE + 1);
        Candidate candidate = new Candidate(x, y, colors.get(candidatesCounter));
        candidates.add(candidate);
        candidatesCounter++;
        return candidate;
    }

    public Candidate createCandidate(int x, int y) {
        if (candidatesCounter == 5)
            return null;
        Candidate candidate = new Candidate(x, y, colors.get(candidatesCounter));
        candidates.add(candidate);
        candidatesCounter++;
        return candidate;
    }


}