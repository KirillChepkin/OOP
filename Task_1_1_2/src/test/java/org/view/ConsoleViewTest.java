package org.view;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.example.Card;
import org.example.CardCode;
import org.example.Dealer;
import org.example.Shoe;
import org.example.SuitCode;
import org.example.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ConsoleViewTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @AfterEach
    void restoreSystemStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void getUserDecisionAcceptsZeroAndReturnsFalse() {
        System.setIn(new ByteArrayInputStream("0\n".getBytes(StandardCharsets.UTF_8)));

        ConsoleView view = new ConsoleView();

        assertFalse(view.getUserDecision());
    }

    @Test
    void getUserDecisionAcceptsOneAndReturnsTrue() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes(StandardCharsets.UTF_8)));

        ConsoleView view = new ConsoleView();

        assertTrue(view.getUserDecision());
    }

    @Test
    void getUserDecisionRetriesInvalidInput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream("abc\n1\n".getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(output));

        ConsoleView view = new ConsoleView();

        assertTrue(view.getUserDecision());
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Недопустимый ввод"));
    }

    @Test
    void askToContinueAcceptsZero() {
        System.setIn(new ByteArrayInputStream("0\n".getBytes(StandardCharsets.UTF_8)));

        ConsoleView view = new ConsoleView();

        assertFalse(view.askToContinue());
    }

    @Test
    void displayMethodsProduceOutput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        final User<ConsoleView> user = new User<>();
        final Dealer<ConsoleView> dealer = new Dealer<>();
        final Shoe shoe = new Shoe(0);
        final ConsoleView view = new ConsoleView();

        Card userCard = new Card(SuitCode.HEARTS, CardCode.ACE);
        userCard.setRevealed(true);
        user.getCards().add(userCard);

        Card dealerCard1 = new Card(SuitCode.CLUBS, CardCode.TEN);
        dealerCard1.setRevealed(true);
        Card dealerCard2 = new Card(SuitCode.SPADES, CardCode.FIVE);

        dealer.getCards().add(dealerCard1);
        dealer.getCards().add(dealerCard2);

        view.setContext(user, dealer, shoe);
        view.displayStart();
        view.displayUserDraw();
        view.displayDealerDraw();
        view.displayDealerReveal();
        view.displayUserBlackJack();
        view.displayDealerBlackJack();
        view.displayUserVictory();
        view.displayDealerVictory();
        view.displayDraw();

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Ваши карты:"));
        assertTrue(text.contains("Дилера"));
        assertTrue(text.contains("Туз"));
        assertTrue(text.contains("Скрытая карта"));
        assertTrue(text.contains("У вас блэкджек!"));
        assertTrue(text.contains("Вы победили"));
        assertTrue(text.contains("Ничья"));
    }
}
