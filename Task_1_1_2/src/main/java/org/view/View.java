package org.view;

import org.example.Shoe;
import org.example.Player;

/**
 * Declares actions that internal game logic can signal to UI (Dealer card draws and reveals,
 * game start and end, victory, etc.)  and user actions that UI can signal to game logic
 * (user's decision to stop taking cards).
 */
public abstract class View {
    /**
     * Player object needs View to signal their actions as well as View needs Player object to
     * access their cards.
     */
    protected Player<?> dealer;
    protected Player<?> user;
    protected Shoe deck;

    /**
     * Context includes objects that contain information about cards they dispose. It is used for
     * IO.
     *
     * @param user user object.
     *
     * @param dealer dealer object.
     *
     * @param deck deck object.
     */
    public void setContext(Player<?> user, Player<?> dealer, Shoe deck) {
        this.user = user;
        this.dealer = dealer;
        this.deck = deck;
    }

    /**
     * Waits for user to signal its decision through IO.
     *
     * @return whether user decided to continue taking cards or not.
     */
    public abstract boolean getUserDecision();

    public abstract void displayUserDraw();

    public abstract void displayStart();

    public abstract void displayDealerDraw();

    public abstract void displayDealerReveal();

    public abstract void displayUserBlackJack();

    public abstract void displayDealerBlackJack();

    public abstract void displayUserVictory();

    public abstract void displayDealerVictory();

    public abstract void displayDraw();
}