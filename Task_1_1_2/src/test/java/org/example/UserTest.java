package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.view.View;

class UserTest {
    private static class TestView extends View {
        private final boolean[] decisions;
        private int decisionIndex;
        int draws;

        TestView(boolean... decisions) {
            this.decisions = decisions;
        }

        @Override
        public boolean getUserDecision() {
            return decisions[decisionIndex++];
        }

        @Override
        public boolean askToContinue() {
            return false;
        }

        @Override
        public void displayUserDraw() {
            draws++;
        }

        @Override
        public void displayStart() {}

        @Override
        public void displayDealerDraw() {}

        @Override
        public void displayDealerReveal() {}

        @Override
        public void displayUserBlackJack() {}

        @Override
        public void displayDealerBlackJack() {}

        @Override
        public void displayUserVictory() {}

        @Override
        public void displayDealerVictory() {}

        @Override
        public void displayDraw() {}

    }

    private static Shoe shoeWithTopCards(Card... topCards) {
        Shoe shoe = new Shoe(0);
        shoe.getCards().addAll(Arrays.asList(topCards));
        return shoe;
    }

    @Test
    void startDrawsTwoRevealedCards() {
        User<TestView> user = new User<>();
        TestView view = new TestView();
        user.setView(view);

        Card first = new Card(SuitCode.SPADES, CardCode.FIVE);
        Card second = new Card(SuitCode.HEARTS, CardCode.SIX);
        user.setShoe(shoeWithTopCards(second, first));

        user.start();

        assertEquals(2, user.getCards().size());
        assertTrue(first.isRevealed());
        assertTrue(second.isRevealed());
        assertEquals(11, user.getValue());
    }

    @Test
    void playStopsImmediatelyWhenUserSaysNo() {
        TestView view = new TestView(false);
        User<TestView> user = new User<>();
        user.setView(view);

        Card first = new Card(SuitCode.SPADES, CardCode.FIVE);
        Card second = new Card(SuitCode.HEARTS, CardCode.SIX);
        Card extra = new Card(SuitCode.CLUBS, CardCode.TEN);
        user.setShoe(shoeWithTopCards(extra, second, first));
        user.start();

        user.play();

        assertEquals(2, user.getCards().size());
        assertEquals(11, user.getValue());
        assertEquals(0, view.draws);
    }

    @Test
    void playDrawsWhileUserContinuesThenStops() {
        TestView view = new TestView(true, false);
        User<TestView> user = new User<>();
        user.setView(view);

        Card first = new Card(SuitCode.SPADES, CardCode.FIVE);
        Card second = new Card(SuitCode.HEARTS, CardCode.SIX);
        Card extra = new Card(SuitCode.CLUBS, CardCode.FOUR);
        user.setShoe(shoeWithTopCards(extra, second, first));
        user.start();

        user.play();

        assertEquals(3, user.getCards().size());
        assertEquals(15, user.getValue());
        assertEquals(1, view.draws);
    }
}
