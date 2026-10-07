package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.view.View;

class GameTest {

    /**
     * Test implementation of View that records which methods were called.
     */
    private static class TestView extends View {
        private final boolean userDecision;
        private final boolean continuePlaying;

        int displayStartCalls;
        int userDrawCalls;
        int dealerDrawCalls;
        int dealerRevealCalls;

        int userBlackJackCalls;
        int dealerBlackJackCalls;
        int userVictoryCalls;
        int dealerVictoryCalls;
        int drawCalls;

        int askToContinueCalls;

        TestView(boolean userDecision, boolean continuePlaying) {
            this.userDecision = userDecision;
            this.continuePlaying = continuePlaying;
        }

        @Override
        public boolean getUserDecision() {
            return this.userDecision;
        }

        @Override
        public boolean askToContinue() {
            this.askToContinueCalls++;
            return this.continuePlaying;
        }

        @Override
        public void displayUserDraw() {
            this.userDrawCalls++;
        }

        @Override
        public void displayStart() {
            this.displayStartCalls++;
        }

        @Override
        public void displayDealerDraw() {
            this.dealerDrawCalls++;
        }

        @Override
        public void displayDealerReveal() {
            this.dealerRevealCalls++;
        }

        @Override
        public void displayUserBlackJack() {
            this.userBlackJackCalls++;
        }

        @Override
        public void displayDealerBlackJack() {
            this.dealerBlackJackCalls++;
        }

        @Override
        public void displayUserVictory() {
            this.userVictoryCalls++;
        }

        @Override
        public void displayDealerVictory() {
            this.dealerVictoryCalls++;
        }

        @Override
        public void displayDraw() {
            this.drawCalls++;
        }
    }

    /**
     * Creates a shoe whose last elements are the cards that will be drawn first.
     */
    private static Shoe createShoe(Card... cardsInDrawOrder) {
        Shoe shoe = new Shoe(0);
        shoe.setCards(new ArrayList<>());

        for (int i = cardsInDrawOrder.length - 1; i >= 0; i--) {
            shoe.getCards().add(cardsInDrawOrder[i]);
        }

        return shoe;
    }

    @Test
    void constructorCreatesGameWithEmptyResult() {
        TestView view = new TestView(false, false);

        Game<TestView> game = new Game<>(view);

        assertEquals(null, game.getResult());
        assertTrue(view.getUser() != null);
        assertTrue(view.getDealer() != null);
        assertTrue(view.getDeck() != null);
    }

    @Test
    void setShoeReplacesGameShoe() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        Shoe shoe = new Shoe(0);

        game.setShoe(shoe);

        assertTrue(game.getShoe() == shoe);
    }

    @Test
    void setUserReplacesGameUser() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        User<TestView> user = new User<>();

        game.setUser(user);

        assertTrue(game.getUser() == user);
    }

    @Test
    void setDealerReplacesGameDealer() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        Dealer<TestView> dealer = new Dealer<>();

        game.setDealer(dealer);

        assertTrue(game.getDealer() == dealer);
    }

    @Test
    void playRoundDetectsUserBlackJack() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        Card userAce = new Card(SuitCode.SPADES, CardCode.ACE);
        Card userTen = new Card(SuitCode.HEARTS, CardCode.TEN);

        Card dealerFive = new Card(SuitCode.CLUBS, CardCode.FIVE);
        Card dealerSix = new Card(SuitCode.DIAMONDS, CardCode.SIX);

        game.setShoe(createShoe(
                userAce,
                dealerFive,
                userTen,
                dealerSix));

        game.playRound();

        assertEquals(Result.USER_BLACK_JACK, game.getResult());
        assertEquals(1, view.displayStartCalls);
    }

    @Test
    void playRoundDetectsDealerBlackJack() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        Card userFive = new Card(SuitCode.SPADES, CardCode.FIVE);
        Card userSix = new Card(SuitCode.HEARTS, CardCode.SIX);

        Card dealerAce = new Card(SuitCode.CLUBS, CardCode.ACE);
        Card dealerTen = new Card(SuitCode.DIAMONDS, CardCode.TEN);

//        System.out.println("dealer value before round: " + game.getDealer().getValue());
//        System.out.println("dealer cards before round: " + game.getDealer().getCards());

        Shoe newShoe = createShoe(
                userFive,
                userSix,
                dealerAce,
                dealerTen);

        game.setShoe(newShoe);
        game.getUser().setShoe(newShoe);
        game.getDealer().setShoe(newShoe);

//        System.out.println("cards inside the shoe: " + newShoe.getCards());
//        System.out.println("cards inside the game shoe: " + game.getShoe().getCards());

        game.playRound();

//        System.out.println("user's cards: " + game.getUser().getCards());
//        System.out.println("dealer's cards: " + game.getDealer().getCards());
//        System.out.println("result of the game: " + game.getResult());

        assertEquals(Result.DEALER_BLACK_JACK, game.getResult());
    }

    @Test
    void playRoundDetectsUserBust() {
        TestView view = new TestView(true, false);
        Game<TestView> game = new Game<>(view);

        Card userTen = new Card(SuitCode.SPADES, CardCode.TEN);
        Card userNine = new Card(SuitCode.HEARTS, CardCode.NINE);
        Card userFive = new Card(SuitCode.CLUBS, CardCode.FIVE);

        Card dealerTwo = new Card(SuitCode.DIAMONDS, CardCode.TWO);
        Card dealerThree = new Card(SuitCode.SPADES, CardCode.THREE);

        Shoe newShoe = createShoe(
                            userTen,
                            userFive,
                            dealerThree,
                            dealerTwo,
                            userNine);
        game.setShoe(newShoe);
        game.getUser().setShoe(newShoe);
        game.getDealer().setShoe(newShoe);

        game.playRound();

        assertEquals(Result.DEALER_VICTORY, game.getResult());
        assertEquals(1, view.userDrawCalls);
    }

//    @Test
//    void playRoundDetectsDealerBust() {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        Card userTen = new Card(SuitCode.SPADES, CardCode.TEN);
//        Card userFive = new Card(SuitCode.HEARTS, CardCode.FIVE);
//
//        Card dealerTen = new Card(SuitCode.CLUBS, CardCode.TEN);
//        Card dealerSix = new Card(SuitCode.DIAMONDS, CardCode.SIX);
//        Card dealerFive = new Card(SuitCode.HEARTS, CardCode.FIVE);
//
//        game.setShoe(createShoe(
//                userTen,
//                dealerTen,
//                userFive,
//                dealerSix,
//                dealerFive));
//
//        game.playRound();
//
//        assertEquals(Result.USER_VICTORY, game.getResult());
//    }

//    @Test
//    void playRoundDetectsUserVictoryWhenUserHasHigherValue() {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        Card userTen = new Card(SuitCode.SPADES, CardCode.TEN);
//        Card userSix = new Card(SuitCode.HEARTS, CardCode.SIX);
//
//        Card dealerNine = new Card(SuitCode.CLUBS, CardCode.NINE);
//        Card dealerSix = new Card(SuitCode.DIAMONDS, CardCode.SIX);
//
//        game.setShoe(createShoe(
//                userTen,
//                dealerNine,
//                userSix,
//                dealerSix));
//
//        game.playRound();
//
//        assertEquals(Result.USER_VICTORY, game.getResult());
//    }

//    @Test
//    void playRoundDetectsDealerVictoryWhenDealerHasHigherValue() {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        Card userEight = new Card(SuitCode.SPADES, CardCode.EIGHT);
//        Card userSix = new Card(SuitCode.HEARTS, CardCode.SIX);
//
//        Card dealerTen = new Card(SuitCode.CLUBS, CardCode.TEN);
//        Card dealerSeven = new Card(SuitCode.DIAMONDS, CardCode.SEVEN);
//
//        game.setShoe(createShoe(
//                userEight,
//                dealerTen,
//                userSix,
//                dealerSeven));
//
//        game.playRound();
//
//        assertEquals(Result.DEALER_VICTORY, game.getResult());
//    }

//    @Test
//    void playRoundDetectsDraw() {
//        TestView view = new TestView(false, false);
//        Game<TestView> game = new Game<>(view);
//
//        Card userEight = new Card(SuitCode.SPADES, CardCode.EIGHT);
//        Card userSeven = new Card(SuitCode.HEARTS, CardCode.SEVEN);
//
//        Card dealerNine = new Card(SuitCode.CLUBS, CardCode.NINE);
//        Card dealerSix = new Card(SuitCode.DIAMONDS, CardCode.SIX);
//
//        game.setShoe(createShoe(
//                userEight,
//                dealerNine,
//                userSeven,
//                dealerSix));
//
//        game.playRound();
//
//        assertEquals(Result.DRAW, game.getResult());
//    }

    @Test
    void resetPlayersClearsBothPlayers() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        game.getUser().getCards().add(
                new Card(SuitCode.SPADES, CardCode.TEN));

        game.getDealer().getCards().add(
                new Card(SuitCode.HEARTS, CardCode.TEN));

        game.resetPlayers();

        assertTrue(game.getUser().getCards().isEmpty());
        assertTrue(game.getDealer().getCards().isEmpty());
        assertEquals(0, game.getUser().getValue());
        assertEquals(0, game.getDealer().getValue());
    }

    @Test
    void declareRoundResultDisplaysDraw() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        game.setResult(Result.DRAW);

        game.declareRoundResult();

        assertEquals(1, view.drawCalls);
    }

    @Test
    void declareRoundResultDisplaysUserVictory() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        game.setResult(Result.USER_VICTORY);

        game.declareRoundResult();

        assertEquals(1, view.userVictoryCalls);
    }

    @Test
    void declareRoundResultDisplaysDealerVictory() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        game.setResult(Result.DEALER_VICTORY);

        game.declareRoundResult();

        assertEquals(1, view.dealerVictoryCalls);
    }

    @Test
    void declareRoundResultDisplaysUserBlackJack() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        game.setResult(Result.USER_BLACK_JACK);

        game.declareRoundResult();

        assertEquals(1, view.userBlackJackCalls);
    }

    @Test
    void declareRoundResultDisplaysDealerBlackJack() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        game.setResult(Result.DEALER_BLACK_JACK);

        game.declareRoundResult();

        assertEquals(1, view.dealerBlackJackCalls);
    }

    @Test
    void playStopsWhenViewSaysNotToContinue() {
        TestView view = new TestView(false, false);
        Game<TestView> game = new Game<>(view);

        Card userAce = new Card(SuitCode.SPADES, CardCode.ACE);
        Card userTen = new Card(SuitCode.HEARTS, CardCode.TEN);

        Card dealerFive = new Card(SuitCode.CLUBS, CardCode.FIVE);
        Card dealerSix = new Card(SuitCode.DIAMONDS, CardCode.SIX);

        game.setShoe(createShoe(
                userAce,
                dealerFive,
                userTen,
                dealerSix));

        game.play();

        assertEquals(Result.USER_BLACK_JACK, game.getResult());
        assertEquals(1, view.askToContinueCalls);
        assertEquals(1, view.userBlackJackCalls);
    }
}