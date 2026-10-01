package org.example;

import org.view.ConsoleView;
import javax.annotation.processing.Generated;

/**
 * Only creates basic instances and calls game starting method.
 */
@Generated("manual-exclusion")
public class Main {
    /**
     * A standard main method.
     *
     * @param args default argument.
     */
    public static void main(String[] args) {
        ConsoleView view  = new ConsoleView();
        Game<ConsoleView> game = new Game<>(view);
        game.play();

//        Deck deck = new Deck();
//        for (int i = 0; i < deck.cards.size(); i++) {
//            System.out.println(i + ") " + deck.cards.get(i));
//        }
    }
}