package play.play;

import base.Deck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.LogStatus;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by graham on 12/07/16.
 */
public class Hand implements LogStatus {
    private long handNumber = 0;
    private Map<Integer, PlayerHand> playerHands = null;
    private int buttonPosition;
    private Hand previousHand = null;
    private Deck deck = new Deck();
    private int actorPosition;
    private PlayerHand currentPlayerHand = null;
    private int currentBet = 0;
    private int pot = 0;
    private int smallBlind;
    private Round round;

    private Round previousRound = null;
    private Round currentRound = null;

    private static final Logger logger =
            LoggerFactory.getLogger(Hand.class);

    public Hand(long handNumber, Map<Integer, Player> playerPosition, Hand previousHand) {
        this.handNumber = handNumber;
        playerHands = playerPosition.entrySet().stream().collect(Collectors.toMap(p->p.getKey(), p->new PlayerHand(p.getValue(), this)));
        this.previousHand = previousHand;
        if (previousHand != null) {
            this.buttonPosition = (previousHand.buttonPosition + 1) % (playerHands.size());
        } else {
            buttonPosition = 0;
        }
        actorPosition = buttonPosition;
        this.currentPlayerHand = playerHands.get(this.actorPosition);
    }

    public Round nextRound() {
        playerHands.entrySet().stream().map(p->p.getValue()).forEach(p->p.resetRound());
        previousRound = currentRound;
        if (previousRound == null) {
            currentRound = new FirstRound(this);
        } else if (previousRound instanceof FirstRound) {
            currentRound = new Flop(this);
        } else if (previousRound instanceof Flop) {
            currentRound = new Turn(this);
        } else if (previousRound instanceof Turn) {
            currentRound = new River(this);
        } else if (previousRound instanceof River) {
            return null;
        }
        return currentRound;
    }

    void movePlayer() {
        for(actorPosition = (actorPosition + 1) % playerHands.size(); !getPlayerHands().get(actorPosition).isIn(); actorPosition = (actorPosition + 1) % playerHands.size());
        currentPlayerHand = playerHands.get(actorPosition);
    }

    public void logStatus() {
        logger.info("Hand: " + handNumber);
        playerHands.entrySet().stream().forEach(p -> logger.info("Players: " + p.getValue().getPlayer().getName() + ((p.getKey() == buttonPosition) ? "(*)" : "")));
    }

    public void addToPot(int bet) {
        pot += bet;
    }

    public long getHandNumber() {
        return handNumber;
    }

    public void setHandNumber(long handNumber) {
        this.handNumber = handNumber;
    }

    public Map<Integer, PlayerHand> getPlayerHands() {
        return playerHands;
    }

    public void setPlayerHands(Map<Integer, PlayerHand> playerHands) {
        this.playerHands = playerHands;
    }

    public int getButtonPosition() {
        return buttonPosition;
    }

    public void setButtonPosition(int buttonPosition) {
        this.buttonPosition = buttonPosition;
    }

    public Hand getPreviousHand() {
        return previousHand;
    }

    public void setPreviousHand(Hand previousHand) {
        this.previousHand = previousHand;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getActorPosition() {
        return actorPosition;
    }

    public void setActorPosition(int actorPosition) {
        this.actorPosition = actorPosition;
    }

    public PlayerHand getCurrentPlayerHand() {
        return currentPlayerHand;
    }

    public void setCurrentPlayerHand(PlayerHand currentPlayerHand) {
        this.currentPlayerHand = currentPlayerHand;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Round getPreviousRound() {
        return previousRound;
    }

    public void setPreviousRound(Round previousRound) {
        this.previousRound = previousRound;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }
}
