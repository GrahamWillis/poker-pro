package play.events;

import play.play.PlayerHand;
import play.play.Hand;

/**
 * Created by graham on 12/07/16.
 */
public class SmallBlind extends Event implements Blind {

    private final int amount;
    private Event.Options actionOption = null;
    private final PlayerHand currentPlayerHand;

    public SmallBlind(Hand hand) {
        super(hand);
        currentPlayerHand = this.hand.getCurrentPlayerHand();
        this.amount = this.hand.getSmallBlind();
    }

    @Override
    public void logStatus() {
        logger.info("Small blind played by: " + this.hand.getCurrentPlayerHand().getPlayer().getName());
    }

    @Override
    public Event act() {
        actionOption = Event.Options.CALL;
        hand.setCurrentBet(amount);
        int actualPayment = makePayment(amount);
        currentPlayerHand.setOweThisRound(currentPlayerHand.getOweThisRound() - actualPayment);
        return this;
    }

    @Override
    public boolean lastAction() {
        return true;
    }

}