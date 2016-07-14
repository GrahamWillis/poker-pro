package play.deal;

import play.events.Event;
import play.play.Hand;

/**
 * Created by graham on 12/07/16.
 */
public abstract class Deal extends Event {
    public Deal(Hand hand) {
        super(hand);
    }
}
