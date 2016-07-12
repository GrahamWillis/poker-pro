package play;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by graham on 12/07/16.
 */
public class Game {
    private Map<Integer, Player> playerPosition = null;
    private Round previousRound = null;
    private Round currentRound = null;
    private long roundsToPlay = 0;
    private long roundsPlayed = 0;

    public Game(long roundsToPlay) {
        playerPosition = new HashMap<>();
        this.roundsToPlay = roundsToPlay;
    }

    public void add(Player player) {
        playerPosition.put(playerPosition.size(), player);
    }
    public void add(Player[] players) {
        for (Player player : players) {
            playerPosition.put(playerPosition.size(), player);
        }
    }

    public Round nextRound() {
        if (roundsPlayed < roundsToPlay) {
            roundsPlayed++;
            previousRound = currentRound;
            currentRound = new Round(roundsPlayed, playerPosition, previousRound);
            return currentRound;
        } else {
            return null;
        }
    }
}
