package org.example;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.view.View;

class DealerTest {
//    private static class TestView extends View {
//        int reveals;
//        int draws;
//
//        @Override public boolean getUserDecision() { return false; }
//        @Override public boolean askToContinue() { return false; }
//        @Override public void displayUserDraw() { }
//        @Override public void displayStart() { }
//        @Override public void displayDealerDraw() { draws++; }
//        @Override public void displayDealerReveal() { reveals++; }
//        @Override public void displayUserBlackJack() { }
//        @Override public void displayDealerBlackJack() { }
//        @Override public void displayUserVictory() { }
//        @Override public void displayDealerVictory() { }
//        @Override public void displayDraw() { }
//    }
//
//    private static Shoe shoeWithCards(Card... cards) {
//        Shoe shoe = new Shoe(0);
//        shoe.getCards().addAll(Arrays.asList(cards));
//        return shoe;
//    }
//
//    @Test
//    void startRevealsFirstCardButHidesSecondCard() {
//        Dealer<TestView> dealer = new Dealer<>();
//        TestView view = new TestView();
//        dealer.setView(view);
//
//        Card first = new Card(SuitCode.SPADES, CardCode.FIVE);
//        Card hidden = new Card(SuitCode.HEARTS, CardCode.SIX);
//        dealer.setShoe(shoeWithCards(hidden, first));
//
//        dealer.start();
//
//        assertEquals(2, dealer.getCards().size());
//        assertTrue(first.isRevealed());
//        assertFalse(hidden.isRevealed());
//        assertEquals(11, dealer.getValue());
//    }
//
//    @Test
//    void playRevealsHiddenCardAndDrawsUntilAtLeastSeventeen() {
//        Dealer<TestView> dealer = new Dealer<>();
//        TestView view = new TestView();
//        dealer.setView(view);
//
//        Card first = new Card(SuitCode.SPADES, CardCode.FIVE);
//        Card hidden = new Card(SuitCode.HEARTS, CardCode.SIX);
//        Card third = new Card(SuitCode.CLUBS, CardCode.FOUR);
//
//        dealer.setShoe(shoeWithCards(third, hidden, first));
//        dealer.start();
//
//        dealer.play();
//
//        assertTrue(hidden.isRevealed());
//        assertEquals(15, dealer.getValue());
//        assertEquals(3, dealer.getCards().size());
//        assertEquals(1, view.reveals);
//        assertEquals(1, view.draws);
//    }
//
//    @Test
//    void playDoesNotDrawWhenInitialValueIsAtLeastSeventeen() {
//        Dealer<TestView> dealer = new Dealer<>();
//        TestView view = new TestView();
//        dealer.setView(view);
//
//        Card first = new Card(SuitCode.SPADES, CardCode.NINE);
//        Card hidden = new Card(SuitCode.HEARTS, CardCode.EIGHT);
//        Card extra = new Card(SuitCode.CLUBS, CardCode.TEN);
//
//        dealer.setShoe(shoeWithCards(extra, hidden, first));
//        dealer.start();
//
//        dealer.play();
//
//        assertTrue(hidden.isRevealed());
//        assertEquals(17, dealer.getValue());
//        assertEquals(2, dealer.getCards().size());
//        assertEquals(1, view.reveals);
//        assertEquals(0, view.draws);
//    }
}
