package play;

import base.Deck;

import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * Created by graham on 12/07/16.
 */
public class DealPlayers extends Deal {
    private Stream<PlayerRound> pr() {
        return this.round.getPlayerRounds().entrySet().stream().map(p -> p.getValue());
    }

    public DealPlayers(Round round) {
        super(round);
    }

    @Override
    public String show() {
        StringJoiner sj = new StringJoiner("\n");
        pr().forEach(p -> sj.add(p.show()));
        return sj.toString();
    }

    @Override
    public Action act() {
        for(int i = 0; i < 2; i++) {
            pr().forEach(p -> p.receiveCard(this.round.getDeck().dealOne()));
        }
        return this;
    }
}
