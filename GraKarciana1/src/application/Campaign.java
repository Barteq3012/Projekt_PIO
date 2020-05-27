package application;

import javafx.scene.layout.Pane;

public class Campaign {

    public Game game;
    DeckOfCards playerDeck;
    DeckOfCards enemyDeck;

    public Campaign() {

    }

    public void startLevel1(Pane table) {

        Main.mode = 1;

        game = new Game(1,table, "Promek", "Helios", 80, 10, 80, 10);

        playerDeck = new DeckOfCards(ReadFile.level);
        enemyDeck = new DeckOfCards(ReadFile.level);

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeckBoss1(enemyDeck);

        game.startGame();
    }

    public void startLevel2(Pane table) {

        Main.mode = 1;

        game = new Game(2,table, "Promek", "Pandora", 80, 10, 80, 10);

        playerDeck = new DeckOfCards(ReadFile.level);
        enemyDeck = new DeckOfCards(ReadFile.level);

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeckBoss2(enemyDeck);

        game.startGame();
    }

    public void startLevel3(Pane table) {

        Main.mode = 1;

        game = new Game(3,table, "Promek", "Hydra", 80, 10, 80, 10);

        playerDeck = new DeckOfCards(ReadFile.level);
        enemyDeck = new DeckOfCards(ReadFile.level);

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeckBoss3(enemyDeck);

        game.startGame();
    }

    public void startLevel4(Pane table) {

        Main.mode = 1;

        game = new Game(4,table, "Promek", "Zeus", 80, 10, 80, 10);

        playerDeck = new DeckOfCards(ReadFile.level);
        enemyDeck = new DeckOfCards(ReadFile.level);

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeckBoss4(enemyDeck);

        game.startGame();
    }

    public void startLevel5(Pane table) {

        Main.mode = 1;

        game = new Game(5,table, "Promek", "Ethon", 80, 10, 80, 10);

        playerDeck = new DeckOfCards(ReadFile.level);
        enemyDeck = new DeckOfCards(ReadFile.level);

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeckBoss5(enemyDeck);

        game.startGame();
    }
}
