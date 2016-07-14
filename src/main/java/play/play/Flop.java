package play.play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.events.Event;

/**
 * Created by graham on 14/07/16.
 */
public class Flop extends Round {
    private static final Logger logger =
            LoggerFactory.getLogger(Flop.class);

    public Flop(Hand hand) {
        super(hand);
    }

    @Override
    public void logStatus() {
        logger.info("Flop");
    }

    public Event nextEvent() {
        previousEvent = currentEvent;
        return currentEvent;
    }
}
