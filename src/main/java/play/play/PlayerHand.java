package play.play;

import base.Deck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.LogStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by graham on 12/07/16.
 */
public class PlayerHand implements LogStatus {
    private static final Logger logger =
            LoggerFactory.getLogger(PlayerHand.class);

    private Player player;
    private Hand hand;
    private boolean in = true;
    private List<Deck.Card> holeCards = new ArrayList<>(2);

    private int paidThisHand = 0;
    private int oweThisHand = 0;
    private int paidThisRound = 0;
    private int oweThisRound = 0;

    public PlayerHand(Player player, Hand hand) {
        this.player = player;
        this.hand = hand;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public void receiveCard(Deck.Card card) {
        holeCards.add(card);
    }

    public void clearCards() {
        holeCards = new ArrayList<>(2);
    }

    public void makePayment(int amount) {
        paidThisHand += amount;
        paidThisRound += amount;
        oweThisHand -= amount;
        oweThisRound -= amount;
        hand.addToPot(amount);
    }

    public void resetRound() {
        paidThisRound = 0;
        oweThisRound = 0;
    }

    public void resetHand() {
        paidThisRound = 0;
        oweThisRound = 0;
        paidThisHand = 0;
        paidThisRound = 0;
    }

    public int getOweThisRound() {
        return oweThisRound;
    }

    public void setOweThisRound(int oweThisRound) {
        this.oweThisRound = oweThisRound;
    }

    @Override
    public void logStatus() {
        player.logStatus();
        logger.info("Hole cards: " + holeCards);
        logger.info(in ? "In" : "Out");
        logger.info("Paid (Hand): " + paidThisHand);
        logger.info("Owe (Hand): " + oweThisHand);
        logger.info("Paid (Round): " + paidThisRound);
        logger.info("Owe (Round): " + oweThisRound);
    }
}
