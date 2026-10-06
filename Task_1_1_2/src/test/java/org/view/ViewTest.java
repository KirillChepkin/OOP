package org.view;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.example.Player;
import org.example.Shoe;
import org.junit.jupiter.api.Test;

class ViewTest {
    private static class TestView extends View {
        @Override
        public boolean getUserDecision() {
            return false;
        }

        @Override
        public boolean askToContinue() {
            return false;
        }

        @Override
        public void displayUserDraw() {

        }

        @Override
        public void displayStart() {

        }

        @Override
        public void displayDealerDraw() {

        }

        @Override
        public void displayDealerReveal() {

        }

        @Override
        public void displayUserBlackJack() {

        }

        @Override
        public void displayDealerBlackJack() {

        }

        @Override
        public void displayUserVictory() {

        }

        @Override
        public void displayDealerVictory() {

        }

        @Override
        public void displayDraw() {

        }

        Player<?> getUserContext() {
            return user;
        }

        Player<?> getDealerContext() {
            return dealer;
        }

        Shoe getDeckContext() {
            return deck;
        }
    }

    @Test
    void setContextStoresAllReferences() {
        TestView view = new TestView();
        Shoe shoe = new Shoe(0);

        view.setContext(null, null, shoe);

        assertNull(view.getUserContext());
        assertNull(view.getDealerContext());
        assertSame(shoe, view.getDeckContext());
    }
}
