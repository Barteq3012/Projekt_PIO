package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Game {



    public SmallDeckOfCards playerSmallDeck = new SmallDeckOfCards();
    public SmallDeckOfCards enemySmallDeck = new SmallDeckOfCards();

    private CardInHand playerCardInHand ;
    private CardInHand enemyCardInHand;
    // tworzy rywali
    public Player player = new Player("Bartek", 80,10, playerCardInHand);
    public Player enemy  = new Player("Rektor",70,6, enemyCardInHand);
    private Pane table;


    public Game() {

    }

    public void startGame() {
        // przypisuje wskazniki; gracz ma namiar na swojego przciwnika i na odwrot
        player.SetMyEnemy(enemy);
        enemy.SetMyEnemy(player);

        playerCardInHand.initHand(playerSmallDeck);

        nextTurn();

        table.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {

                nextTurn();

                if(playerCardInHand.emptyHand()) {

                    mouseEvent.consume();
                }
            }
        });
    }

    private void nextTurn() {

        playerCardInHand.alreadyThrown = false;

        playerCardInHand.hand.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        if(!playerCardInHand.alreadyThrown) {

                            playerCardInHand.chooseCardFromHand();

                            if(playerCardInHand.readyToThrown) {

                                player.card = playerCardInHand.getCardFromHand();
                                playerCardInHand.inHandPosition[player.card.positionOnTable] = 0;
                                player.card.positionOnTable = 0;

                                player.throwCard();

                                throwCardByEnemy();

                                playerSmallDeck.setSize();

                                if(playerSmallDeck.getSize() > 0) {

                                    playerCardInHand.drawCardsFromSmallDeck(playerSmallDeck);
                                }

                                playerCardInHand.readyToThrown = false;
                            }
                        }
                    }
                });
    }

    private void throwCardByEnemy() {

        enemy.card =  enemySmallDeck.getCardFromSmallDeck();

       /* enemyCardInHand.hand.getChildren().add(enemy.card.imageView);
        enemy.card.imageView.setX(0);
        enemy.card.imageView.fitWidthProperty().bind(enemyCardInHand.hand.widthProperty());
        enemy.card.imageView.setPreserveRatio(true); */

        enemy.throwCard();
    }

    public void setTable(Pane table) {

        this.table = table;

        playerCardInHand = new CardInHand(200, 550, table);
        enemyCardInHand = new CardInHand(200, 50, table);

        player.setPlaceOnTable(200, 390, table);
        enemy.setPlaceOnTable(200, 90, table);
    }

 /*   void endGame() {

        Image image = new Image(getClass().getResourceAsStream("endgame.png"));
        ImageView imageView = new ImageView(image);
        table.getChildren().add(imageView);

    } */
}
