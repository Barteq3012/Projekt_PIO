package application;

import javafx.scene.layout.Pane;

public class Duel{

    public Game game;
    AllCard allCards;
    DeckOfCards playerDeck;
    DeckOfCards enemyDeck;

    Pane table;

    public Duel(Pane table) {

        this.table = table;
        game = new Game(table, "Promek", "Kartek", 80, 10, 80, 10);
    }

    public void startDuel() {

       // allCards = new AllCard();
     //   System.out.println(allCards.allCard.size());

        //AllCard.createCards();
        //System.out.println(AllCard.allCard.size());

        playerDeck = new DeckOfCards();
        enemyDeck = new DeckOfCards();

        game.playerSmallDeck.setSmallDeck(playerDeck);
        game.enemySmallDeck.setSmallDeck(enemyDeck);

        game.startGame();
    }

}
