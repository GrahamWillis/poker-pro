package play.play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.deal.DealPlayers;
import play.events.Act;
import play.events.Event;
import play.events.LargeBlind;
import play.events.SmallBlind;

/**
 * Created by graham on 14/07/16.
 */
public class FirstRound extends Round {
    private static final Logger logger =
            LoggerFactory.getLogger(FirstRound.class);

    public FirstRound(Hand hand) {
        super(hand);
    }

    @Override
    public void logStatus() {
        logger.info("First Round");
    }

    public Event nextEvent() {
        previousEvent = currentEvent;
        if (previousEvent == null) {
            currentEvent = new DealPlayers(hand);
        } else if (previousEvent instanceof DealPlayers) {
            hand.movePlayer();
            currentEvent = new SmallBlind(hand);
        } else if (previousEvent instanceof SmallBlind) {
            hand.movePlayer();
            currentEvent = new LargeBlind(hand);
        } else if (previousEvent instanceof LargeBlind) {
            hand.movePlayer();
            currentEvent = new Act(hand);
        } else if (previousEvent instanceof Act && !previousEvent.lastAction()) {
            hand.movePlayer();
            currentEvent = new Act(hand);
        } else {
            currentEvent = null;
        }
        return currentEvent;
    }
}
