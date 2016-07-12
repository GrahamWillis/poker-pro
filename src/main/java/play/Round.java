package play;

import base.Deck;

import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by graham on 12/07/16.
 */
public class Round {
    Action previousAction = null;
    Action currentAction = null;
    private long roundNumber = 0;
    private Map<Integer, PlayerRound> playerRounds = null;
    private int buttonPosition;
    private Round previousRound = null;
    private Deck deck = new Deck();

    public Round(long roundNumber, Map<Integer, Player> playerPosition, Round previousRound) {
        this.roundNumber = roundNumber;
        playerRounds = playerPosition.entrySet().stream().collect(Collectors.toMap(p->p.getKey(), p->new PlayerRound(p.getValue(), this)));
        this.previousRound = previousRound;
        if (previousRound != null) {
            this.buttonPosition = (previousRound.buttonPosition + 1) % (playerPosition.size());
        } else {
            buttonPosition = 0;
        }
    }

    public Action nextAction() {
        previousAction = currentAction;
        if (previousAction == null) {
            currentAction = new DealPlayers(this);
        } else {
            //currentAction = new Act(this);
            currentAction = null;
        }
        return currentAction;
    }

    public String show() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("Round number: " + roundNumber);
        playerRounds.entrySet().stream().forEach(p -> sj.add(p.getValue().getPlayer().toString() + ((p.getKey() == buttonPosition) ? "(*)" : "")));
        return sj.toString();
    }

    public Map<Integer, PlayerRound> getPlayerRounds() {
        return playerRounds;
    }

    public void setPlayerRounds(Map<Integer, PlayerRound> playerRounds) {
        this.playerRounds = playerRounds;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
