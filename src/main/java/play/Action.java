package play;

/**
 * Created by graham on 12/07/16.
 */
public abstract class Action {
    protected final Round round;

    public Action(Round round) {
        this.round = round;
    }
    public abstract String show();
    public abstract Action act();
}
