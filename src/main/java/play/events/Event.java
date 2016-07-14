package play.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.LogStatus;
import play.play.Hand;
import play.play.PlayerHand;
import play.play.Round;

/**
 * Created by graham on 12/07/16.
 */
public abstract class Event implements LogStatus {
    protected final Hand hand;
    protected final Round round;

    private final PlayerHand currentPlayerHand;

    protected static final Logger logger =
            LoggerFactory.getLogger(Event.class);

    public abstract Event act();
    public abstract boolean lastAction();

    public enum Options {
        CHECK, FOLD, CALL, RAISE
    }

    public Event(Hand hand) {
        this.hand = hand;
        this.round = hand.getCurrentRound();
        currentPlayerHand = hand.getCurrentPlayerHand();
    }

    protected int makePayment(int amount) {
        int actualAmount = currentPlayerHand.getPlayer().spendStack(amount);
        currentPlayerHand.makePayment(actualAmount);
        return actualAmount;
    }

}
