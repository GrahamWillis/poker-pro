package play.play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.LogStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by graham on 12/07/16.
 */
public class Game implements LogStatus {
    private static final Logger logger =
            LoggerFactory.getLogger(Game.class);

    private final int smallBlind;
    private Map<Integer, Player> playerPosition = null;
    private Hand previousHand = null;
    private Hand currentHand = null;
    private long roundsToPlay = 0;
    private long roundsPlayed = 0;

    public Game(long roundsToPlay, int smallBlind) {
        playerPosition = new HashMap<>();
        this.roundsToPlay = roundsToPlay;
        this.smallBlind = smallBlind;
    }

    public void add(Player player) {
        playerPosition.put(playerPosition.size(), player);
    }
    public void add(Player[] players) {
        for (Player player : players) {
            playerPosition.put(playerPosition.size(), player);
        }
    }

    public Hand nextHand() {
        if (roundsPlayed < roundsToPlay) {
            roundsPlayed++;
            previousHand = currentHand;
            currentHand = new Hand(roundsPlayed, playerPosition, previousHand);
            currentHand.setSmallBlind(smallBlind);
            return currentHand;
        } else {
            return null;
        }
    }

    @Override
    public void logStatus() {
        logger.info("Game: rounds remaining" + roundsToPlay);
    }
}
