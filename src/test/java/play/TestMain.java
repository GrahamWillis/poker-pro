package play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.events.Event;
import play.play.Game;
import play.play.Hand;
import play.play.Player;
import play.play.Round;

/**
 * Created by graham on 12/07/16.
 */
public class TestMain {
    private static final Logger logger =
            LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        int i = 0;
        Game game = new Game(1, 10);
        //g.add(new Player[] {new Player("Matthew"), new Player("Mark"), new Player("Luke"), new Player("John")});
        game.add(new Player[] {new Player("One"), new Player("Two"), new Player("Three"), new Player("Four"), new Player("Five"), new Player("Six")});
        for (Hand hand = game.nextHand(); hand != null; hand = game.nextHand()) {
            hand.logStatus();
            for (Round round = hand.nextRound(); round != null; round = hand.nextRound()) {
                round.logStatus();
                for (Event event = round.nextEvent(); event != null; event = round.nextEvent()) {
                    event.act();
                    event.logStatus();
                }
            }
        }
    }
}
