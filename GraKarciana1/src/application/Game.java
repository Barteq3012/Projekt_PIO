package application;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Game {

    public SmallDeckOfCards playerSmallDeck = new SmallDeckOfCards();
    public SmallDeckOfCards enemySmallDeck = new SmallDeckOfCards();

    private CardInHand playerCardInHand;
    private CardInHand enemyCardInHand;

    // tworzy rywali
    public Player player;
    public Player enemy;

    private Pane table;

    public static boolean ready = false;
    private boolean isEnd = false;


    public Game(Pane table) {

        this.table = table;

        playerCardInHand = new CardInHand(180, 540, table);
        enemyCardInHand = new CardInHand(180, -150, table);

        player = new Player("Bartek", 200, 10, playerCardInHand);
        enemy = new Player("Rektor", 200, 10, enemyCardInHand);
    }

    public void startGame() {

        // przypisuje wskazniki; gracz ma namiar na swojego przciwnika i na odwrot
        player.SetMyEnemy(enemy);
        enemy.SetMyEnemy(player);

        //dopiera początkowe karty, po zakońćzeniu animacji game ustawia na ready
        playerCardInHand.initHand(playerSmallDeck);
        enemyCardInHand.initHand(enemySmallDeck);

        // kliknięcie powoduje wejście do metody nextTurn() jeśli gra jest gotowa, tzn. kiedy oczekiwana jest interakcja użytkownika
        playerCardInHand.hand.setOnMouseClicked(e -> {

            if (Game.ready) {
                nextTurn();
            }
        });

        // jeśli gra nie oczekuje interakcji to blokuje wywoływanie setOnMouseCliced
        table.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent e) {

                if (!Game.ready) {
                    e.consume();
                }
            }
        });
    }

    private void nextTurn() {

        // chooseCrad wywoływane jest przy każdym kliknięciu jeśli game jest ready i ręka nie jest pusta,
        // pierwsze kliknięcie w kartę w ręce powoduje ustawienie redyToThrown na true,
        // co blokuje dlasze wchodzenie do choose i wywołuje sekwencje tury

        if (!playerCardInHand.emptyHand()) {
            playerCardInHand.chooseCardFromHand();
            player.ready = true;
        } else {
            playerCardInHand.readyToThrown = true;
        }

        if (playerCardInHand.readyToThrown) {

            Game.ready = false;
            playerCardInHand.readyToThrown = false;

            if (player.ready) {
                player.ready = false;
                throwCardByPlayer(); //wywołuje animacje i akcje gracza
            }

            if (!isEnd && !enemyCardInHand.emptyHand()) {
                throwCardByEnemy(); //wywołuje animacje i akcje wroga, a potem też dobieranie kart i sprawdzanie końca gry
            }
        }
    }

    private void throwCardByPlayer() {

        player.card = playerCardInHand.getCardFromHand(); //przypisuje wybraną kartę
        player.cardsOntable.add(player.card); //dodaje do kolekcji karty na stole (rzucone)

        //te dwa to potrzebne do ładnego układania kart po zjechaniu
        player.card.owner = player;
        player.card.onTable = true;

        playerCardInHand.inHandPosition[player.card.positionOnTable] = 0;
        player.card.positionOnTable = 0;

        //akcja animacja
        player.moveCard(1);
        player.throwCard();
        System.out.println("Po akcji gracza:");
        System.out.println("Gracz: hp: " + player.hp + " sp: " + player.sp);
        System.out.println("wróg: hp: " + enemy.hp + " sp: " + enemy.sp);
        checkEnd();

        if (!isEnd && enemyCardInHand.emptyHand()) {
            //sprawdza rozmiar małych talii(0-15)
            playerSmallDeck.setSize();
            enemySmallDeck.setSize();

            //jeśli są jeszcze karty, to dobrać po jednej karcie, jak nie sprawdź, czy koniec gry
            if (playerSmallDeck.getSize() > 0 || enemySmallDeck.getSize() > 0) {
                drawCards();
            } else {
                checkNewTurn();
            }
        }
    }

    private void throwCardByEnemy() {

        // task do czekania na wykonanie animacji rzucenia karty gracza
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        // po czekaniu
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                enemy.card = enemyCardInHand.getCardFromEnemyHand(); //losuje kartę z ręki, dalej to samo, co u gracza
                enemy.cardsOntable.add(enemy.card);

                enemy.card.owner = enemy;
                enemy.card.onTable = true;

                enemyCardInHand.inHandPosition[enemy.card.positionOnTable] = 0;
                enemy.card.positionOnTable = 0;

                enemy.moveCard(2);
                enemy.throwCard();
                System.out.println("Po akcji wroga:");
                System.out.println("Gracz: hp" + player.hp + " sp: " + player.sp);
                System.out.println("wróg: hp " + enemy.hp + " sp: " + enemy.sp);
                checkEnd();

                if (!isEnd) {
                    //sprawdza rozmiar małych talii(0-15)
                    playerSmallDeck.setSize();
                    enemySmallDeck.setSize();

                    //jeśli są jeszcze karty, to dobrać po jednej karcie, jak nie sprawdź, czy koniec gry
                    if (playerSmallDeck.getSize() > 0 || enemySmallDeck.getSize() > 0) {
                        drawCards();
                    } else {
                        checkNewTurn();
                    }
                }
            }
        });
        new Thread(sleeper).start();
    }

    private void drawCards() {

        // task do czekania na wykonanie animacji rzucenia karty wroga
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                //dobiera karty kto może
                if (playerSmallDeck.getSize() > 0) {

                    playerCardInHand.drawCardsFromSmallDeck(playerSmallDeck);
                }

                if (enemySmallDeck.getSize() > 0) {

                    enemyCardInHand.drawCardsFromSmallDeck(enemySmallDeck);
                }

                // po dobraniu, co dalej
                checkNewTurn();
            }
        });
        new Thread(sleeper).start();
    }

    private void checkNewTurn() {

        // task do czekania na wykonanie animacji poprzedzającej
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                if (playerCardInHand.emptyHand() && enemyCardInHand.emptyHand()) {

                    System.out.println("Koniec gry!");
                } else {

                    textAnimation();

                    if (playerCardInHand.emptyHand()) {
                        nextTurn();
                    }
                    else {
                        Game.ready = true;
                    }
                }
            }
        });
        new Thread(sleeper).start();
    }

    private void checkEnd() {

        if (player.hp <= 0 || enemy.hp <= 0) {

            isEnd = true;
            System.out.println("Koniec gry!");
        }
    }

    private void textAnimation() {

        Text text = new Text();
        text.setText("Nowa tura");
        text.setX(610);
        text.setY(300);
        text.setFill(Color.GOLD);

        table.getChildren().add(text);

        FadeTransition ft = new FadeTransition(Duration.millis(1500), text);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(3000), text);
        scaleTransition.setByY(7);
        scaleTransition.setByX(7);
        scaleTransition.setAutoReverse(false);
        scaleTransition.setOnFinished(e -> {

            table.getChildren().remove(text);

        });
        scaleTransition.play();
        ft.play();
    }

    public void setTable(Pane table) {

        this.table = table;


    }

 /*   void endGame() {

        Image image = new Image(getClass().getResourceAsStream("endgame.png"));
        ImageView imageView = new ImageView(image);
        table.getChildren().add(imageView);

    } */
}
