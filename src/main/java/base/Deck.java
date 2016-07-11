package base;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by graham on 08/07/16.
 */
public class Deck {


    public enum Suit {
        SPADES((short)0),
        HEARTS((short)1),
        DIAMONDS((short)2),
        CLUBS((short)3);
        private final short value;
        Suit(short value) {
            this.value = value;
        }
    }

    public enum Rank {
        TWO((short)2), THREE((short)3), FOUR((short)4), FIVE((short)5), SIX((short)6),
        SEVEN((short)7), EIGHT((short)8), NINE((short)9), TEN((short)10),
        JACK((short)11), QUEEN((short)12), KING((short)13), ACE((short)14);
        private final short value;
        Rank(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }
    }

    public static Comparator<Rank> simpleComparitor =
            (Rank r1, Rank r2)-> {
                if (r1.getValue()-r2.getValue()>=1) {
                    return 1;
                } else if (r1.getValue()-r2.getValue()<=-1) {
                    return -1;
                } else {
                    return 0;
                }
            };


    public static class Card {
        private Rank rank;
        private Suit suit;

        public Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }
        @Override
        public String toString() {
            return "Card{" +
                    "rank=" + rank +
                    " of suit=" + suit +
                    '}';
        }

        public Rank getRank() {
            return rank;
        }

        public Suit getSuit() {
            return suit;
        }
    }

    public Comparator<Card> newPacket = new Comparator<Card>() {
        public int compare(Card c1, Card c2) {
            return c1.getRank() == c2.getRank() ? c1.getSuit().compareTo(c2.getSuit()) : c1.getRank().compareTo(c2.getRank());
        }
    };

    private List<Card> sortedDeck = null;
    private ArrayDeque<Card> shuffledDeck = null;

    public Deck() {
        sortedDeck = new ArrayList<>();
        for(Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                sortedDeck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(sortedDeck);
        shuffledDeck = new ArrayDeque<>(sortedDeck);
    }

    public ArrayDeque<Card> getShuffledDeck() {
        return shuffledDeck;
    }

    public Card dealOne() {
        return shuffledDeck.pop();
    }

    public Card[] dealThree() {
        return new Card[] {shuffledDeck.pop(), shuffledDeck.pop(), shuffledDeck.pop()};
    }

    public Card[] dealSeven() {
        return new Card[] {shuffledDeck.pop(), shuffledDeck.pop(), shuffledDeck.pop(),shuffledDeck.pop(), shuffledDeck.pop(), shuffledDeck.pop(),shuffledDeck.pop()};
    }
}
