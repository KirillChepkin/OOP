package org.view;

import org.example.Card;
import org.example.CardCode;
import org.example.Player;
import org.example.SuitCode;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A subclass of View that specifically implements the Command line IO.
 */
public class ConsoleView extends View {
    /**
     * These two attributes map internal card representations to IO card representations.
     */
    private static final Map<CardCode, CardRep> card = new HashMap<>();
    private static final Map<SuitCode, SuitRep> suit = new HashMap<>();

    static {
        CardCode[] key = CardCode.values();
        CardRep[] value = CardRep.values();
        int len = key.length;
        if (value.length < len) {
            len = value.length;
        }
        for (int i = 0; i < len; i++) {
            card.put(key[i], value[i]);
        }

        SuitCode[] suitKey = SuitCode.values();
        SuitRep[] suitValue = SuitRep.values();
        len = suitKey.length;
        if (len > suitValue.length) {
            len = suitValue.length;
        }
        for (int i = 0; i < len; i++) {
            suit.put(suitKey[i], suitValue[i]);
        }
    }

    /**
     * Displays cards given to players by printing them into the console.
     */
    public void displayStart() {
        printSeparator();
        System.out.println("Ваши карты:");
        printCardsList(this.user);
        System.out.println("Всего: " + this.user.getValue());
        printSeparator();
        System.out.println("Карты Дилера:");
        printCardsList(this.dealer);
        System.out.println("Всего: "
                + (this.dealer.getValue() - this.dealer.cards.get(1).getValue()));
        printSeparator();
    }

    private static void printCardsList(Player<?> player) {
        int i = 0;
        for (Card card : player.cards) {
            i++;
            System.out.println(i + ") " + getCardRep(card));
        }
    }

    private static String getCardRep(Card cardArg) {
        if (cardArg.revealed) {
            return "<" + card.get(cardArg.card).rep + " " + suit.get(cardArg.suit).rep + ">";
        }
        return "<Скрытая карта>";
    }

    public static void printSeparator() {
        System.out.println("----------------------");
    }

    /**
     * Offers a user to decide whether to continue taking cards. Expects 0 or 1 and retries
     * otherwise.
     *
     * @return whether a user typed 0 or 1 into the console.
     */
    public boolean getUserDecision() {
        System.out.println("Хотите продолжить брать карты?(0 - нет/1 - да)");
        Scanner scanner = new Scanner(System.in);
        String response;
        while (true) {
            response = scanner.nextLine();
            if (response.equals("0")) {
                return false;
            } else if (response.equals("1")) {
                return true;
            } else {
                System.out.println("Недопустимый ввод, попробуйте еще раз.");
            }
        }
    }

    /**
     * Prints all cards at user's disposal and the last drawn card.
     */
    public void displayUserDraw() {
        System.out.println("Вы взяли карту: "
                + getCardRep(this.user.cards.get(this.user.cards.size() - 1))
                + ". Всего " + "очков: " + this.user.getValue());
        System.out.println("Ваши карты: ");
        printCardsList(this.user);
        printSeparator();
    }

    /**
     * Prints all cards at dealer's disposal and the last drawn card.
     */
    public void displayDealerDraw() {
        System.out.println("Дилер взял карту: "
                + getCardRep(this.dealer.cards.get(this.dealer.cards.size() - 1)) + ". "
                + "Очков у Дилера: " + this.dealer.getValue());
        System.out.println("Карты Дилера: ");
        printCardsList(this.dealer);
        printSeparator();
    }

    /**
     * Prints all cards at dealer's disposal and a revealed card.
     */
    public void displayDealerReveal() {
        System.out.println("Дилер открыл карту: "
                + getCardRep(this.dealer.cards.get(this.dealer.cards.size() - 1))
                + ". " + "Очков у Дилера: " + this.dealer.getValue());
        System.out.println("Карты Дилера: ");
        printCardsList(this.dealer);
        printSeparator();
    }

    public void displayUserBlackJack() {
        System.out.println("У вас блэкдлек!");
    }

    public void displayDealerBlackJack() {
        System.out.println("У Дилера блэкджек.");
    }

    public void displayUserVictory() {
        System.out.println("Вы победили");
    }

    public void displayDealerVictory() {
        System.out.println("Вы проиграли");
    }

    public void displayDraw() {
        System.out.println("Ничья");
    }
}
