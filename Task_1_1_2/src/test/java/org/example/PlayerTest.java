package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.view.View;

class PlayerTest {
    private static class TestPlayer extends Player<View> {
        @Override
        public void start() { }

        @Override
        public void play() { }

        void give(Card card, boolean reveal) {
            takeCard(card, reveal);
        }

        int aceCount() {
            return countAces();
        }
    }

    @Test
    void newPlayerHasNoCardsAndZeroValue() {
        TestPlayer player = new TestPlayer();

        assertTrue(player.getCards().isEmpty());
        assertEquals(0, player.getValue());
        assertEquals(0, player.getVictories());
    }

    @Test
    void takeCardAddsCardAndRevealsItWhenRequested() {
        TestPlayer player = new TestPlayer();
        Card card = new Card(SuitCode.SPADES, CardCode.FIVE);

        player.give(card, true);

        assertEquals(1, player.getCards().size());
        assertSame(card, player.getCards().get(0));
        assertTrue(card.isRevealed());
        assertEquals(5, player.getValue());
    }

    @Test
    void hiddenNonAceCardStillCountsTowardsValue() {
        TestPlayer player = new TestPlayer();

        player.give(new Card(SuitCode.SPADES, CardCode.SEVEN), false);

        assertFalse(player.getCards().get(0).isRevealed());
        assertEquals(7, player.getValue());
    }

    @Test
    void aceCountsAsElevenWhenItDoesNotBust() {
        TestPlayer player = new TestPlayer();

        player.give(new Card(SuitCode.SPADES, CardCode.ACE), true);

        assertEquals(1, player.aceCount());
        assertEquals(11, player.getValue());
    }

    @Test
    void aceIsReducedToOneWhenElevenWouldBust() {
        TestPlayer player = new TestPlayer();

        player.give(new Card(SuitCode.SPADES, CardCode.ACE), true);
        player.give(new Card(SuitCode.HEARTS, CardCode.KING), true);
        player.give(new Card(SuitCode.CLUBS, CardCode.FIVE), true);

        assertEquals(16, player.getValue());
    }

    @Test
    void multipleAcesAreHandledCorrectly() {
        TestPlayer player = new TestPlayer();

        player.give(new Card(SuitCode.SPADES, CardCode.ACE), true);
        player.give(new Card(SuitCode.HEARTS, CardCode.ACE), true);
        player.give(new Card(SuitCode.CLUBS, CardCode.NINE), true);

        assertEquals(21, player.getValue());
    }

    @Test
    void resetPlayerRemovesCardsAndResetsValueAndAces() {
        TestPlayer player = new TestPlayer();

        player.give(new Card(SuitCode.SPADES, CardCode.ACE), true);
        player.give(new Card(SuitCode.HEARTS, CardCode.KING), true);

        player.resetPlayer();

        assertTrue(player.getCards().isEmpty());
        assertEquals(0, player.getValue());
        assertEquals(0, player.aceCount());
    }

    @Test
    void victoriesCanBeIncremented() {
        TestPlayer player = new TestPlayer();

        player.addVictory();
        player.addVictory();

        assertEquals(2, player.getVictories());
    }

    @Test
    void setShoeAndSetViewStoreReferences() {
        TestPlayer player = new TestPlayer();
        Shoe shoe = new Shoe(0);
        TestView view = new TestView();

        player.setShoe(shoe);
        player.setView(view);

        assertDoesNotThrow(() -> player.start());
    }

    private static class TestView extends View {
        @Override public boolean getUserDecision() { return false; }
        @Override public boolean askToContinue() { return false; }
        @Override public void displayUserDraw() { }
        @Override public void displayStart() { }
        @Override public void displayDealerDraw() { }
        @Override public void displayDealerReveal() { }
        @Override public void displayUserBlackJack() { }
        @Override public void displayDealerBlackJack() { }
        @Override public void displayUserVictory() { }
        @Override public void displayDealerVictory() { }
        @Override public void displayDraw() { }
    }
}
