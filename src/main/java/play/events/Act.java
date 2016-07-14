package play.events;

import play.play.PlayerHand;
import play.play.Hand;
import play.play.Round;

import java.util.Map;

/**
 * Created by graham on 12/07/16.
 */
public class Act extends Event {

    private final PlayerHand currentPlayerHand;
    private final Map<Integer, PlayerHand> playerRounds;
    private final int actorPosition;
    private final int buttonPosition;
    private final Round currentRound;
    private Event.Options actionOption = null;

    public Act(Hand hand) {
        super(hand);
        currentPlayerHand = this.hand.getCurrentPlayerHand();
        playerRounds = this.hand.getPlayerHands();
        actorPosition = this.hand.getActorPosition();
        buttonPosition = this.hand.getButtonPosition();
        currentRound = this.hand.getCurrentRound();
    }

    @Override
    public void logStatus() {
        logger.info("Action on: " + currentPlayerHand.getPlayer().getName());
        currentPlayerHand.logStatus();
    }

    @Override
    public Event act() {
        actionOption = Options.CALL;
        currentPlayerHand.setOweThisRound(currentPlayerHand.getOweThisRound() + hand.getCurrentBet());
        int actualPayment = makePayment(currentPlayerHand.getOweThisRound());
        currentPlayerHand.setOweThisRound(currentPlayerHand.getOweThisRound() - actualPayment);
        return this;
    }

    public boolean lastAction() {
        for(Map.Entry<Integer, PlayerHand> es : playerRounds.entrySet()) {
            if (es.getValue().isIn() && es.getValue().getOweThisRound() < 0) {
                return false;
            }
        }
        return true;
    }
}
