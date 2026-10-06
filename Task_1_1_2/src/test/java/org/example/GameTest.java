package org.example;

//import static org.junit.jupiter.api.Assertions.*;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//import org.junit.jupiter.api.Test;
//import org.view.View;

class GameTest {
//    private static class TestView extends View {
//        boolean continuePlaying;
//        boolean userDecision;
//        int start;
//        int userDraw;
//        int dealerDraw;
//        int dealerReveal;
//        int userBlackJack;
//        int dealerBlackJack;
//        int userVictory;
//        int dealerVictory;
//        int draw;
//
//        TestView(boolean continuePlaying, boolean userDecision) {
//            this.continuePlaying = continuePlaying;
//            this.userDecision = userDecision;
//        }
//
//        @Override public boolean getUserDecision() { return userDecision; }
//        @Override public boolean askToContinue() { return continuePlaying; }
//        @Override public void displayUserDraw() { userDraw++; }
//        @Override public void displayStart() { start++; }
//        @Override public void displayDealerDraw() { dealerDraw++; }
//        @Override public void displayDealerReveal() { dealerReveal++; }
//        @Override public void displayUserBlackJack() { userBlackJack++; }
//        @Override public void displayDealerBlackJack() { dealerBlackJack++; }
//        @Override public void displayUserVictory() { userVictory++; }
//        @Override public void displayDealerVictory() { dealerVictory++; }
//        @Override public void displayDraw() { draw++; }
//    }
//
//    private static Shoe controlledShoe(Card... cards) {
//        Shoe shoe = new Shoe(0);
//        shoe.getCards().addAll(Arrays.asList(cards));
//        return shoe;
//    }
//
//    private static void replaceShoe(Game<?> game, Shoe shoe) throws Exception {
//        Field shoeField = Game.class.getDeclaredField("shoe");
//        shoeField.setAccessible(true);
//        shoeField.set(game, shoe);
//
//        Field userField = Game.class.getDeclaredField("user");
//        userField.setAccessible(true);
//        Player<?> user = (Player<?>) userField.get(game);
//        user.setShoe(shoe);
//
//        Field dealerField = Game.class.getDeclaredField("dealer");
//        dealerField.setAccessible(true);
//        Player<?> dealer = (Player<?>) dealerField.get(game);
//        dealer.setShoe(shoe);
//    }
//
//    @Test
//    void constructorSetsViewContext() {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        assertNotNull(game);
//        assertNull(game.getResult());
//        assertNotNull(view.getUser());
//        assertNotNull(view.getDealer());
//        assertNotNull(view.getDeck());
//        assertEquals(312, view.getDeck().getCards().size());
//    }
//
//    @Test
//    void playRoundDetectsUserBlackJack() throws Exception {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        Card userAce = new Card(SuitCode.SPADES, CardCode.ACE);
//        Card userTen = new Card(SuitCode.HEARTS, CardCode.TEN);
//        Card dealerFive = new Card(SuitCode.CLUBS, CardCode.FIVE);
//        Card dealerSix = new Card(SuitCode.DIAMONDS, CardCode.SIX);
//
//        replaceShoe(game, controlledShoe(dealerSix, dealerFive, userTen, userAce));
//
//        game.playRound();
//
//        assertEquals(Result.USER_BLACK_JACK, game.getResult());
//        assertEquals(1, view.start);
//    }
//
//    @Test
//    void playRoundDetectsDealerBlackJack() throws Exception {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        Card userFive = new Card(SuitCode.SPADES, CardCode.FIVE);
//        Card userSix = new Card(SuitCode.HEARTS, CardCode.SIX);
//        Card dealerAce = new Card(SuitCode.CLUBS, CardCode.ACE);
//        Card dealerTen = new Card(SuitCode.DIAMONDS, CardCode.TEN);
//
//        replaceShoe(game, controlledShoe(dealerTen, dealerAce, userSix, userFive));
//
//        game.playRound();
//
//        assertEquals(Result.DEALER_BLACK_JACK, game.getResult());
//    }
//
//    @Test
//    void playRoundDetectsUserBust() throws Exception {
//        TestView view = new TestView(false, true);
//        Game<TestView> game = new Game<>(view);
//
//        Card userTen = new Card(SuitCode.SPADES, CardCode.TEN);
//        Card userNine = new Card(SuitCode.HEARTS, CardCode.NINE);
//        Card userFive = new Card(SuitCode.CLUBS, CardCode.FIVE);
//        Card dealerTwo = new Card(SuitCode.DIAMONDS, CardCode.TWO);
//        Card dealerThree = new Card(SuitCode.SPADES, CardCode.THREE);
//
//        replaceShoe(game, controlledShoe(dealerThree, dealerTwo, userFive, userNine, userTen));
//
//        game.playRound();
//
//        assertEquals(Result.DEALER_VICTORY, game.getResult());
//        assertEquals(1, view.userDraw);
//    }
//
//    @Test
//    void declareRoundResultCallsCorrectViewMethod() {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        setResult(game, Result.DRAW);
//        game.declareRoundResult();
//        assertEquals(1, view.draw);
//
//        setResult(game, Result.USER_VICTORY);
//        game.declareRoundResult();
//        assertEquals(1, view.userVictory);
//
//        setResult(game, Result.DEALER_VICTORY);
//        game.declareRoundResult();
//        assertEquals(1, view.dealerVictory);
//
//        setResult(game, Result.USER_BLACK_JACK);
//        game.declareRoundResult();
//        assertEquals(1, view.userBlackJack);
//
//        setResult(game, Result.DEALER_BLACK_JACK);
//        game.declareRoundResult();
//        assertEquals(1, view.dealerBlackJack);
//    }
//
//    @Test
//    void resetPlayersClearsBothPlayers() throws Exception {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        Field userField = Game.class.getDeclaredField("user");
//        Field dealerField = Game.class.getDeclaredField("dealer");
//        userField.setAccessible(true);
//        dealerField.setAccessible(true);
//
//        Player<?> user = (Player<?>) userField.get(game);
//        Player<?> dealer = (Player<?>) dealerField.get(game);
//
//        user.getCards().add(new Card(SuitCode.SPADES, CardCode.TWO));
//        dealer.getCards().add(new Card(SuitCode.HEARTS, CardCode.THREE));
//
//        game.resetPlayers();
//
//        assertTrue(user.getCards().isEmpty());
//        assertTrue(dealer.getCards().isEmpty());
//        assertEquals(0, user.getValue());
//        assertEquals(0, dealer.getValue());
//    }
//
//    private static void setResult(Game<?> game, Result result) {
//        try {
//            Field field = Game.class.getDeclaredField("result");
//            field.setAccessible(true);
//            field.set(game, result);
//        } catch (ReflectiveOperationException e) {
//            throw new AssertionError(e);
//        }
//    }
}
