package com.insomnihack.utils;

public class LocalGameState {

    private int localScore;
    private static LocalGameState singleton = null;

    public static LocalGameState getInstance () {

        if (singleton == null) {
            singleton = new LocalGameState ();
        }

        return singleton;
    }

    private LocalGameState() {

        localScore = 0;
    }

    /* ================== */
    public void matched () {
        localScore += 1;
        JniThingies.getInstance().newMatch();
    }

    public int getLocalScore() {
        return localScore;
    }

    public void resetScore () {
        if (JniThingies.getInstance().resetScore() == 0) {
            localScore = 0;
        }
    }

    public void add (int amount) {

        localScore += amount;
        JniThingies.getInstance().add(amount);
    }
}
