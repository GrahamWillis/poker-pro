package play.play;

import play.LogStatus;
import play.events.Event;

import java.util.Map;

/**
 * Created by graham on 14/07/16.
 */
public abstract class Round implements LogStatus {
    protected Hand hand = null;
    protected Map<Integer, PlayerHand> playerHands = null;
    protected Event previousEvent = null;
    protected Event currentEvent = null;
    protected int actorPosition = 0;

    public Round(Hand hand) {
        this.hand = hand;
        this.playerHands = hand.getPlayerHands();
        this.actorPosition = hand.getActorPosition();
    }

    public abstract Event nextEvent();
}
