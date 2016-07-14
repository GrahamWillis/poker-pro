package play.deal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.events.Event;
import play.play.Hand;
import play.play.PlayerHand;

import java.util.stream.Stream;

/**
 * Created by graham on 12/07/16.
 */
public class DealPlayers extends Deal {
    protected static final Logger logger =
            LoggerFactory.getLogger(DealPlayers.class);

    private Stream<PlayerHand> pr() {
        return this.hand.getPlayerHands().entrySet().stream().map(p -> p.getValue());
    }

    public DealPlayers(Hand hand) {
        super(hand);
    }

    @Override
    public Event act() {
        for(int i = 0; i < 2; i++) {
            pr().forEach(p -> p.receiveCard(this.hand.getDeck().dealOne()));
        }
        return this;
    }

    @Override
    public boolean lastAction() {
        return true;
    }

    @Override
    public void logStatus() {
        logger.info("Dealer: " + this.hand.getCurrentPlayerHand().getPlayer().getName());
        pr().forEach(p -> p.logStatus());
    }
}
