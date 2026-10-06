package org.example;

import org.view.ConsoleView;

/**
 * Only creates basic instances and calls game starting method.
 */
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
    }
}