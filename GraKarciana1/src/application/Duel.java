package application;

import javafx.scene.layout.Pane;

public class Duel{

    public Game game;
    DeckOfCards playerDeck;
    DeckOfCards enemyDeck;

    Pane table;

    public Duel(Pane table) {

        this.table = table;
    }

    public void startDuel() {

        game = new Game(2,table, "Promek", "Kartek", 80, 10, 80, 10);

        playerDeck = new DeckOfCards(ReadFile.level);
        enemyDeck = new DeckOfCards(ReadFile.level);

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeck(enemyDeck);

        game.startGame();
    }
}
