package com.insomnihack.utils;

/**
 * Keeps track of the current game score. A game ends when the app is reset. Hence, this state is
 * kept only in memory.
 *
 * The collection of all games is stored in an SQLite database (@see DataManager)
 */
public class LocalGameState {

    private int localScore;
    private int streak;
    private static LocalGameState singleton = null;

    public static LocalGameState getInstance () {

        if (singleton == null) {
            singleton = new LocalGameState ();
        }

        return singleton;
    }

    private LocalGameState() {

        localScore = 0;
        streak = 0;
    }

    /* ================== */
    public void matched () {
        localScore += 1;
        JniThingies.getInstance().newMatch();
    }

    public int getLocalScore() {
        return localScore;
    }

    public int getStreak() {
        return streak;
    }

    public void addStreak () {
        streak += 1;
    }

    public void resetStreak () {
        streak = 0;
    }

    public void resetScore () {
        if (JniThingies.getInstance().resetScore() == 0) {
            localScore = 0;
        }
        streak = 0;
    }

    public void add (int amount) {

        localScore += amount;
        JniThingies.getInstance().add(amount);
    }
}
