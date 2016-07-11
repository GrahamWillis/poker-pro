package evaluations;

import base.Deck;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

/**
 * Created by graham on 08/07/16.
 */
public class Evaluations {

    public enum RankingGroupSize {
        SINGLE((short)1), PAIR((short)2), SET((short)3), QUAD((short)4);
        private short value;
        RankingGroupSize(short value) {
            this.value = value;
        }
        public static RankingGroupSize getRankingGroupSize(Long longValue) {
            return RankingGroupSize.values()[toIntExact(longValue) - 1];
        }
    }

    public static Map<RankingGroupSize, Set<Deck.Rank>> evaluateRanks(Deck.Card[] cards) {
        Map<Deck.Rank, Long> countRank = Arrays.stream(cards)
                .collect(Collectors.groupingBy(card -> card.getRank(),
                        Collectors.counting()));

        Map<RankingGroupSize, Set<Deck.Rank>> rankingGroupSizeAtRank = countRank.entrySet().stream()
                .collect(Collectors.groupingBy(e -> RankingGroupSize.getRankingGroupSize(e.getValue()),
                        Collectors.mapping(e-> e.getKey(), Collectors.toSet())));

        return rankingGroupSizeAtRank;
    }

    public static class Consecutive {
        private Deck.Rank startRank;
        private Deck.Rank endRank;

        public Consecutive(Deck.Rank startRank, Deck.Rank endRank) {
            this.startRank = startRank;
            this.endRank = endRank;
        }

        public Deck.Rank getStartRank() {
            return startRank;
        }
        public Deck.Rank getEndRank() {
            return endRank;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Consecutive that = (Consecutive) o;

            if (startRank != that.startRank) return false;
            return endRank == that.endRank;

        }

        @Override
        public int hashCode() {
            int result = startRank.hashCode();
            result = 31 * result + endRank.hashCode();
            return result;
        }
    }

    private static Deck.Rank[] allConsecutives =
            Stream.concat(Arrays.stream(new Deck.Rank[] {Deck.Rank.ACE}), Arrays.stream(Deck.Rank.values()))
                    .toArray(Deck.Rank[]::new);

    public static List<Consecutive> evaluateConsecutives(Deck.Card[] cards) {

        Deck.Rank[] pair = new Deck.Rank[2];
        short length = 0;
        Deck.Rank startRank = null;
        Deck.Rank endRank = null;
        List<Consecutive> consecutives = new ArrayList<>();

        Set<Deck.Rank> ranks = Arrays.stream(cards).map(Deck.Card::getRank).collect(Collectors.toSet());
        
        for(int i = 0; i < allConsecutives.length + 1; i++) {
            if (i == 0) {
                pair[0] = null;
                pair[1] = allConsecutives[i];
            } else if(i == allConsecutives.length) {
                pair[0] = allConsecutives[i - 1];
                pair[1] = null;
            } else {
                pair[0] = allConsecutives[i - 1];
                pair[1] = allConsecutives[i];
            }

            if (ranks.contains(pair[0]) && ranks.contains(pair[1]) && length == (short)0) {
                length++;
                startRank = pair[0];
                endRank = pair[1];
            } else if (ranks.contains(pair[0]) && ranks.contains(pair[1]) && length != (short)0) {
                length++;
                endRank = pair[1];
            } else if (ranks.contains(pair[0]) && !ranks.contains(pair[1]) && length != (short)0) {
                endRank = pair[0];
                consecutives.add(new Consecutive(startRank, endRank));
                length = (short)0;
            }
        }
        
        return consecutives;
    }

    public static class Flush {
        private Deck.Suit suit;
        private Deck.Rank highCard;
        short size;

        public Flush(Deck.Suit suit, Deck.Rank highCard, short size) {
            this.suit = suit;
            this.highCard = highCard;
            this.size = size;
        }
        public Flush(Deck.Suit suit, Deck.Rank highCard, Long size) {
            this(suit, highCard, (short)toIntExact(size));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Flush flush = (Flush) o;

            if (size != flush.size) return false;
            if (suit != flush.suit) return false;
            return highCard == flush.highCard;
        }

        @Override
        public int hashCode() {
            int result = suit.hashCode();
            result = 31 * result + highCard.hashCode();
            result = 31 * result + (int) size;
            return result;
        }
    }

    public static List<Flush> evaluateFlushes(Deck.Card[] cards) {
        return Arrays.stream(cards)
                    .collect(Collectors.groupingBy(e -> e.getSuit(), Collectors.toSet()))
                        .entrySet().stream().map(f ->
                            new Flush(f.getKey(), f.getValue().stream().map(Deck.Card::getRank).max(Deck.simpleComparitor).get(),
                                    (short)f.getValue().size())).collect(Collectors.toList());
    }

    public static class ConsecutiveFlush {
        Deck.Rank startRank = null;
        Deck.Rank endRank = null;
        private Deck.Suit suit;
        public ConsecutiveFlush(Deck.Rank startRank, Deck.Rank endRank, Deck.Suit suit) {
            this.startRank = startRank;
            this.endRank = endRank;
            this.suit = suit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ConsecutiveFlush that = (ConsecutiveFlush) o;

            if (startRank != that.startRank) return false;
            if (endRank != that.endRank) return false;
            return suit == that.suit;

        }

        @Override
        public int hashCode() {
            int result = startRank.hashCode();
            result = 31 * result + endRank.hashCode();
            result = 31 * result + suit.hashCode();
            return result;
        }
    }



    public static List<ConsecutiveFlush> evaluateConsecutiveFlushes(Deck.Card[] cards) {
        Map<Deck.Suit, Set<Deck.Rank>> ranksBySuits = Arrays.stream(cards)
                .collect(Collectors.groupingBy(e -> e.getSuit(),
                        Collectors.mapping(e -> e.getRank(), Collectors.toSet())));

        List<ConsecutiveFlush> consecutives = new ArrayList<>();

        ranksBySuits.entrySet().forEach(p -> {
            Deck.Rank[] pair = new Deck.Rank[2];
            Deck.Rank startRank = null;
            Deck.Rank endRank = null;
            Set<Deck.Rank> ranks = p.getValue();
            Deck.Suit suit = p.getKey();
            short length = 0;

            for(int i = 0; i < allConsecutives.length + 1; i++) {
                if (i == 0) {
                    pair[0] = null;
                    pair[1] = allConsecutives[i];
                } else if(i == allConsecutives.length) {
                    pair[0] = allConsecutives[i - 1];
                    pair[1] = null;
                } else {
                    pair[0] = allConsecutives[i - 1];
                    pair[1] = allConsecutives[i];
                }

                if (ranks.contains(pair[0]) && ranks.contains(pair[1]) && length == (short)0) {
                    length++;
                    startRank = pair[0];
                    endRank = pair[1];
                } else if (ranks.contains(pair[0]) && ranks.contains(pair[1]) && length != (short)0) {
                    length++;
                    endRank = pair[1];
                } else if (ranks.contains(pair[0]) && !ranks.contains(pair[1]) && length != (short)0) {
                    endRank = pair[0];
                    consecutives.add(new ConsecutiveFlush(startRank, endRank, suit));
                    length = (short)0;
                }
            }
        });

        return consecutives;
    }
}
