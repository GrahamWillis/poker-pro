package evaluations;

import base.Deck;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by graham on 08/07/16.
 */
public class TestEvaluations {
    @Test
    public void testForNoPair() {
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> evaluated = Evaluations.evaluateRanks(new Deck.Card[]{
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.KING, Deck.Suit.DIAMONDS),
                new Deck.Card(Deck.Rank.TWO, Deck.Suit.HEARTS),
                new Deck.Card(Deck.Rank.SEVEN, Deck.Suit.SPADES)
        });
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> expected = new HashMap<Evaluations.RankingGroupSize, Set<Deck.Rank>>();
        Set<Deck.Rank> hs = new HashSet<>();
        hs.add(Deck.Rank.ACE);
        hs.add(Deck.Rank.KING);
        hs.add(Deck.Rank.TWO);
        hs.add(Deck.Rank.SEVEN);
        expected.put(Evaluations.RankingGroupSize.SINGLE, hs);
        assertEquals(evaluated, expected);
    }

    @Test
    public void testForOnePair() {
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> evaluated = Evaluations.evaluateRanks(new Deck.Card[]{
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.DIAMONDS)
        });
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> expected = new HashMap<Evaluations.RankingGroupSize, Set<Deck.Rank>>();
        Set<Deck.Rank> hs = new HashSet<>();
        hs.add(Deck.Rank.ACE);
        expected.put(Evaluations.RankingGroupSize.PAIR, hs);
        assertEquals(evaluated, expected);
    }

    @Test
    public void testForTwoPairs() {
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> evaluated = Evaluations.evaluateRanks(new Deck.Card[]{
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.DIAMONDS),
                new Deck.Card(Deck.Rank.KING, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.KING, Deck.Suit.DIAMONDS),
                new Deck.Card(Deck.Rank.NINE, Deck.Suit.SPADES)
        });
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> expected = new HashMap<Evaluations.RankingGroupSize, Set<Deck.Rank>>();
        Set<Deck.Rank> hs = new HashSet<>();
        hs.add(Deck.Rank.ACE);
        hs.add(Deck.Rank.KING);
        expected.put(Evaluations.RankingGroupSize.PAIR, hs);
        Set<Deck.Rank> hs2 = new HashSet<>();
        hs2.add(Deck.Rank.NINE);
        expected.put(Evaluations.RankingGroupSize.SINGLE, hs2);
        assertEquals(evaluated, expected);
    }

    @Test
    public void testForSet() {
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> evaluated = Evaluations.evaluateRanks(new Deck.Card[]{
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.DIAMONDS),
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.HEARTS),
                new Deck.Card(Deck.Rank.KING, Deck.Suit.DIAMONDS),
                new Deck.Card(Deck.Rank.NINE, Deck.Suit.SPADES)
        });
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> expected = new HashMap<Evaluations.RankingGroupSize, Set<Deck.Rank>>();
        Set<Deck.Rank> hs = new HashSet<>();
        hs.add(Deck.Rank.ACE);
        expected.put(Evaluations.RankingGroupSize.SET, hs);
        Set<Deck.Rank> hs2 = new HashSet<>();
        hs2.add(Deck.Rank.KING);
        hs2.add(Deck.Rank.NINE);
        expected.put(Evaluations.RankingGroupSize.SINGLE, hs2);
        assertEquals(evaluated, expected);
    }

    @Test
    public void testForQuads() {
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> evaluated = Evaluations.evaluateRanks(new Deck.Card[]{
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.DIAMONDS),
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.HEARTS),
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.SPADES),
                new Deck.Card(Deck.Rank.NINE, Deck.Suit.SPADES)
        });
        Map<Evaluations.RankingGroupSize, Set<Deck.Rank>> expected = new HashMap<Evaluations.RankingGroupSize, Set<Deck.Rank>>();
        Set<Deck.Rank> hs = new HashSet<>();
        hs.add(Deck.Rank.ACE);
        expected.put(Evaluations.RankingGroupSize.QUAD, hs);
        Set<Deck.Rank> hs2 = new HashSet<>();
        hs2.add(Deck.Rank.NINE);
        expected.put(Evaluations.RankingGroupSize.SINGLE, hs2);
        assertEquals(evaluated, expected);
    }


    @Test
    public void testForFourConsecutive() {
        List<Evaluations.Consecutive> expected = new ArrayList<>();
        expected.add(new Evaluations.Consecutive(Deck.Rank.ACE, Deck.Rank.FOUR));
        expected.add(new Evaluations.Consecutive(Deck.Rank.KING, Deck.Rank.ACE));
        List<Evaluations.Consecutive> evaluated = Evaluations.evaluateConsecutives(new Deck.Card[]{
                new Deck.Card(Deck.Rank.ACE, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.TWO, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.THREE, Deck.Suit.DIAMONDS),
                new Deck.Card(Deck.Rank.FOUR, Deck.Suit.CLUBS),
                new Deck.Card(Deck.Rank.KING, Deck.Suit.HEARTS),
            }
        );
        assertEquals(evaluated, expected);
    }

    @Test
    public void testForThreeConsecutive() {
        List<Evaluations.Consecutive> expected = new ArrayList<>();
        expected.add(new Evaluations.Consecutive(Deck.Rank.TWO, Deck.Rank.FOUR));
        expected.add(new Evaluations.Consecutive(Deck.Rank.QUEEN, Deck.Rank.KING));
        List<Evaluations.Consecutive> evaluated = Evaluations.evaluateConsecutives(new Deck.Card[]{
                        new Deck.Card(Deck.Rank.TWO, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.THREE, Deck.Suit.DIAMONDS),
                        new Deck.Card(Deck.Rank.FOUR, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.QUEEN, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.KING, Deck.Suit.HEARTS),
                }
        );
        assertEquals(evaluated, expected);
    }

    @Test
    public void testForFiveConsecutive() {
        List<Evaluations.Consecutive> expected = new ArrayList<>();
        expected.add(new Evaluations.Consecutive(Deck.Rank.ACE, Deck.Rank.FIVE));
        expected.add(new Evaluations.Consecutive(Deck.Rank.TEN, Deck.Rank.ACE));
        List<Evaluations.Consecutive> evaluated = Evaluations.evaluateConsecutives(new Deck.Card[]{
                        new Deck.Card(Deck.Rank.ACE, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.TWO, Deck.Suit.DIAMONDS),
                        new Deck.Card(Deck.Rank.THREE, Deck.Suit.DIAMONDS),
                        new Deck.Card(Deck.Rank.FOUR, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.FIVE, Deck.Suit.DIAMONDS),
                        new Deck.Card(Deck.Rank.TEN, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.JACK, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.QUEEN, Deck.Suit.CLUBS),
                        new Deck.Card(Deck.Rank.KING, Deck.Suit.HEARTS),
                }
        );
        assertEquals(evaluated, expected);
    }


}