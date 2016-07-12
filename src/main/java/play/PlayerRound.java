package play;

import base.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by graham on 12/07/16.
 */
public class PlayerRound {
    private Player player;
    private Round round;
    private boolean in = true;
    private List<Deck.Card> holeCards = new ArrayList<>(2);

    public PlayerRound(Player player, Round round) {
        this.player = player;
        this.round = round;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public void receiveCard(Deck.Card card) {
        holeCards.add(card);
    }

    public void clearCards() {
        holeCards = new ArrayList<>(2);
    }

    public String show() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("----");
        sj.add(getPlayer().toString());
        sj.add("Hole cards: " + holeCards);
        return sj.toString();
    }
}
