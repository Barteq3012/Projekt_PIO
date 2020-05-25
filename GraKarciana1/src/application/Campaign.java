package application;

import javafx.scene.layout.Pane;

public class Campaign {

    public Game game;
    DeckOfCards playerDeck;
    DeckOfCards enemyDeck;

    public Campaign() {

    }

    public void startLevel1(Pane table) {

        game = new Game(1,table, "Promek", "Helios", 80, 10, 80, 10);

        playerDeck = new DeckOfCards(ReadFile.level);
        enemyDeck = new DeckOfCards(ReadFile.level);

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeckBoss1(enemyDeck);

        game.startGame();
    }
}
