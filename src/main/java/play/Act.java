package play;

/**
 * Created by graham on 12/07/16.
 */
public class Act extends Action {

    public Act(Round round) {
        super(round);
    }

    @Override
    public String show() {
        return null;
    }

    @Override
    public Action act() {
        return this;
    }

}
