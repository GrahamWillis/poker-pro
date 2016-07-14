package play.play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.LogStatus;

/**
 * Created by graham on 12/07/16.
 */
public class Player implements LogStatus {

    private static final Logger logger =
            LoggerFactory.getLogger(Player.class);

    private String name;
    private int stack = 1000;

    public Player(String name) {
        this.name = name;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public int spendStack(int amount) {
        int actualAmount = stack > amount ? amount : stack;
        stack -= actualAmount;
        return actualAmount;
    }

    @Override
    public void logStatus() {
        logger.info(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
