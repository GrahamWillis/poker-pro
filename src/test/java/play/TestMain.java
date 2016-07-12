package play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by graham on 12/07/16.
 */
public class TestMain {
    private static final Logger logger =
            LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        int i = 0;
        Game g = new Game(10);
        g.add(new Player[] {new Player("Matthew"), new Player("Mark"), new Player("Luke"), new Player("John")});
        for (Round r = g.nextRound(); r != null; r = g.nextRound()) {
            logger.info(r.show());
            for (Action a = r.nextAction(); a != null; a = r.nextAction()) {
                logger.info(a.act().show());
                i++;
                if (i == 2) {break;}
            }
        }
    }
}
