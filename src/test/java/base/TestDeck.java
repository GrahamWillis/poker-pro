package base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by graham on 08/07/16.
 */
public class TestDeck {
    private static final Logger logger =
            LoggerFactory.getLogger(TestDeck.class);

    //public static void main(String[] args) {
    //    Deck deck = new Deck();
    //    short idx = (short)0;
    //    for (Deck.Card c: deck.getShuffledDeck()) {
    //        logger.debug(++idx + " " + String.valueOf(c));
    //    }
    //}

    @Test
    public void testDeck() {
        Deck deck = new Deck();
        assertEquals(deck.getShuffledDeck().size(), 52);
        deck.dealOne();
        assertEquals(deck.getShuffledDeck().size(), 51);
        deck.dealThree();
        assertEquals(deck.getShuffledDeck().size(), 48);
        deck.dealSeven();
        assertEquals(deck.getShuffledDeck().size(), 41);
    }
}
