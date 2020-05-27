package application;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;

public class Game {

    public SmallDeckOfCards playerSmallDeck = new SmallDeckOfCards();
    public SmallDeckOfCards enemySmallDeck = new SmallDeckOfCards();

    private CardInHand playerCardInHand;
    private CardInHand enemyCardInHand;



    // tworzy rywali
    public Player player;
    public Player enemy;

    Button scores = new Button("Wyniki");
    Button pass = new Button("Poddaj się");
    Image imgCardStack = new Image("/pictures/cardBack.jpg");

    Text textPlayerName = new Text();
    Text textPlayerHp = new Text();
    Text textPlayerSp = new Text();
    Text textPlayerStack = new Text();
    ImageView imgPlayerCardStack = new ImageView();

    Text textOpponentName = new Text();
    Text textOpponentHp = new Text();
    Text textOpponentSp = new Text();
    Text textOpponentStack = new Text();
    ImageView imgOpponentCardStack = new ImageView();

    public static Pane table;

    public static boolean ready = false;
    private boolean isEnd = false;

    public static int level;

    public Game(int level, Pane table, String playerName, String opponentName, int playerHp, int playerSp, int enemyHp, int enemySp) {

        Game.level = level;
        Game.table = table;

        playerCardInHand = new CardInHand(140, 530, table);
        enemyCardInHand = new CardInHand(140, -160, table);

        player = new Player(playerName, 1, playerHp, playerSp, playerCardInHand);
        enemy = new Player(opponentName, 2, enemyHp, enemySp, enemyCardInHand);

        playerCardInHand.setOwner(player);
        enemyCardInHand.setOwner(enemy);
    }

    public void startGame() {


        // przypisuje wskazniki; gracz ma namiar na swojego przciwnika i na odwrot
        player.setMyEnemy(enemy);
        enemy.setMyEnemy(player);
        startPlayerName();
        startPlayerHp();
        startPlayerSp();
        startPlayerStack();
        startPlayerNumberOfStack();

        startOpponentName();
        startOpponentHp();
        startOpponentSp();
        startOpponentStack();
        startOpponentNumberOfStack();
        passButton();

        pass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/Pass.fxml"));
                    Pane passboard = fxmlLoader.load();
                    // table.getChildren().setAll(passboard);
                    Stage window = Main.primaryStage2;
                    window.getScene().setRoot(passboard);
                    window.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //dobiera początkowe karty, po zakońćzeniu animacji game ustawia na ready
        playerCardInHand.initHand(playerSmallDeck);
        enemyCardInHand.initHand(enemySmallDeck);

        setStack();

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
        player.card.positionOnTable = -1;

        //akcja animacja
        player.moveCard(1);

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                player.throwCard();
                setPoints();
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
        });
        new Thread(sleeper).start();
    }

    private void throwCardByEnemy() {

        // task do czekania na wykonanie animacji rzucenia karty gracza
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
        // po czekaniu
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                if (!isEnd && !enemyCardInHand.emptyHand()) {
                    enemy.checkEffects();
                    setPoints();
                    checkEnd();
                }

                if (!isEnd) {

                    enemy.card = enemyCardInHand.getCardFromEnemyHand(); //losuje kartę z ręki, dalej to samo, co u gracza
                    enemyCardInHand.returnToHandFromCubby(); //przywraca karty do ręki ze schowka

                    enemy.cardsOntable.add(enemy.card);

                    enemy.card.owner = enemy;
                    enemy.card.onTable = true;

                    enemyCardInHand.inHandPosition[enemy.card.positionOnTable] = 0;
                    enemy.card.positionOnTable = 0;

                    enemy.moveCard(2);

                    Task<Void> sleeper = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(1100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {

                            enemy.throwCard();
                            setPoints();
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
                    Thread.sleep(1000);
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
                    setStack();
                }

                if (enemySmallDeck.getSize() > 0) {

                    enemyCardInHand.drawCardsFromSmallDeck(enemySmallDeck);
                    setStack();
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
                    Thread.sleep(1000);
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

                    showButtonScores();
                    scores.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (player.hp > enemy.hp) {
                                win();
                                // ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                            } else {
                                lose();
                                // ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                            }
                        }
                    });


                } else {

                    textAnimation();

                    if (playerCardInHand.emptyHand()) {

                        player.checkEffects();
                        setPoints();
                        checkEnd();
                        if (!isEnd) {
                            nextTurn();
                        }
                    } else if (!playerCardInHand.emptyHand() && enemyCardInHand.emptyHand()) {

                        enemy.checkEffects();
                        player.checkEffects();
                        setPoints();
                        checkEnd();
                        if (!isEnd) {
                            Game.ready = true;
                        }
                    } else {
                        player.checkEffects();
                        setPoints();
                        checkEnd();
                        if (!isEnd) {
                            Game.ready = true;
                        }
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

            showButtonScores();
            scores.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (player.hp > enemy.hp) {
                        win();
                        // ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                    } else {
                        lose();
                        // ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                    }
                }
            });

        }
    }

    private void textAnimation() {

        Text text = new Text();
        text.setText("Nowa tura");
        text.setX(610);
        text.setY(300);
        text.setFill(Color.GOLD);

        table.getChildren().add(text);

        FadeTransition ft = new FadeTransition(Duration.millis(750), text);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1500), text);
        scaleTransition.setByY(7);
        scaleTransition.setByX(7);
        scaleTransition.setAutoReverse(false);
        scaleTransition.setOnFinished(e -> {

            table.getChildren().remove(text);

        });
        scaleTransition.play();
        ft.play();
    }

    public void setPoints() {

        textPlayerHp.setText(String.valueOf(player.hp));
        textPlayerSp.setText(String.valueOf(player.sp));
        textOpponentSp.setText(String.valueOf(enemy.sp));
        textOpponentHp.setText(String.valueOf(enemy.hp));
    }

    public void setStack() {

        textPlayerStack.setText(String.valueOf(playerSmallDeck.getSize()));
        textOpponentStack.setText(String.valueOf(enemySmallDeck.getSize()));

    }

    private void startPlayerName() {

        textPlayerName.setText(player.name);
        textPlayerName.setX(5);
        textPlayerName.setY(660);
        textPlayerName.setFill(Color.WHITE);
        textPlayerName.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 20));
        table.getChildren().add(textPlayerName);
    }

    private void startPlayerHp() {
        textPlayerHp.setText(String.valueOf(player.hp));
        textPlayerHp.setX(1200);
        textPlayerHp.setY(480);
        textPlayerHp.setFill(Color.BLACK);
        textPlayerHp.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 28));
        table.getChildren().add(textPlayerHp);
    }

    private void startPlayerSp() {
        textPlayerSp.setText(String.valueOf(player.sp));
        textPlayerSp.setX(1205);
        textPlayerSp.setY(610);
        textPlayerSp.setFill(Color.BLACK);
        textPlayerSp.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 28));
        table.getChildren().add(textPlayerSp);
    }

    private void startPlayerStack() {
        //1173 107
        imgPlayerCardStack.setImage(imgCardStack);
        imgPlayerCardStack.setFitHeight(135.0);
        imgPlayerCardStack.setFitWidth(84.0);
        imgPlayerCardStack.setLayoutX(18.0);
        imgPlayerCardStack.setLayoutY(500.0);
        imgPlayerCardStack.setPickOnBounds(true);
        imgPlayerCardStack.setPreserveRatio(true);
        table.getChildren().add(imgPlayerCardStack);

    }

    private void startPlayerNumberOfStack() {
        textPlayerStack.setText(String.valueOf(playerSmallDeck.getSize()));
        textPlayerStack.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 16));
        textPlayerStack.setFill(Color.WHITE);
        textPlayerStack.setLayoutX(81.0);
        textPlayerStack.setLayoutY(612.0);
        textPlayerStack.setStrokeType(StrokeType.OUTSIDE);
        textPlayerStack.setStrokeWidth(0.0);
        textPlayerStack.setWrappingWidth(20.0);
        table.getChildren().add(textPlayerStack);
    }

    private void startOpponentName() {

        textOpponentName.setText(enemy.name);
        textOpponentName.setX(1180);
        textOpponentName.setY(40);
        textOpponentName.setFill(Color.WHITE);
        textOpponentName.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 24));
        table.getChildren().add(textOpponentName);
    }

    private void startOpponentHp() {
        textOpponentHp.setText(String.valueOf(enemy.hp));
        textOpponentHp.setX(40);
        textOpponentHp.setY(110);
        textOpponentHp.setFill(Color.BLACK);
        textOpponentHp.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 28));
        table.getChildren().add(textOpponentHp);
    }

    private void startOpponentSp() {
        textOpponentSp.setText(String.valueOf(enemy.sp));
        textOpponentSp.setX(40);
        textOpponentSp.setY(225);
        textOpponentSp.setFill(Color.BLACK);
        textOpponentSp.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 28));
        table.getChildren().add(textOpponentSp);
    }

    private void startOpponentStack() {
        //1173 107
        imgOpponentCardStack.setImage(imgCardStack);
        imgOpponentCardStack.setFitHeight(135.0);
        imgOpponentCardStack.setFitWidth(84.0);
        imgOpponentCardStack.setLayoutX(1173.0);
        imgOpponentCardStack.setLayoutY(107.0);
        imgOpponentCardStack.setPickOnBounds(true);
        imgOpponentCardStack.setPreserveRatio(true);
        table.getChildren().add(imgOpponentCardStack);

    }

    private void startOpponentNumberOfStack() {
        textOpponentStack.setText(String.valueOf(enemySmallDeck.getSize()));
        textOpponentStack.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 16));
        textOpponentStack.setFill(Color.WHITE);
        textOpponentStack.setLayoutX(1237.0);
        textOpponentStack.setLayoutY(222.0);
        textOpponentStack.setStrokeType(StrokeType.OUTSIDE);
        textOpponentStack.setStrokeWidth(0.0);
        textOpponentStack.setWrappingWidth(20.0);
        table.getChildren().add(textOpponentStack);
    }

    void showButtonScores() {

        scores.setLayoutX(537.0);
        scores.setLayoutY(331.0);
        scores.setMnemonicParsing(false);
        scores.setPrefHeight(39.0);
        scores.setPrefWidth(206.0);
        scores.getStylesheets().add("/design/button.css");

        table.getChildren().add(scores);
    }

    void passButton() {
        pass.setLayoutX(537.0);
        pass.setLayoutY(331.0);
        pass.setMnemonicParsing(false);
        pass.setPrefHeight(39.0);
        pass.setPrefWidth(206.0);
        pass.getStylesheets().add("/design/button.css");

        table.getChildren().add(pass);
    }

    void win() {

        try {
            if (level == 1 && ReadFile.level < 2) {
                ReadFile.level = 2;
                ReadFile.setLevel("2");
            } else if (level == 2 && ReadFile.level < 3) {
                ReadFile.level = 3;
                ReadFile.setLevel("3");
            } else if (level == 3 && ReadFile.level < 4) {
                ReadFile.level = 4;
                ReadFile.setLevel("4");
            } else if (level == 4 && ReadFile.level < 5) {
                ReadFile.level = 5;
                ReadFile.setLevel("5");
            } else if (level == 5 && ReadFile.level < 6) {
                ReadFile.level = 6;
                ReadFile.setLevel("6");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            if(level != 5) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/Win.fxml"));
                Pane winPane = fxmlLoader.load();

                Stage window = Main.primaryStage2;

                System.out.println("Wynik");

                DropShadow ds = new DropShadow();
                ds.setOffsetY(10.0f);
                ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

                Text scorePlayer = new Text();
                Text scoreOpponent = new Text();

                scorePlayer.setEffect(ds);
                scorePlayer.setText(String.valueOf(player.hp));
                scorePlayer.setFill(Color.WHITE);
                scorePlayer.setX(160);
                scorePlayer.setY(420);
                scorePlayer.setTextAlignment(TextAlignment.CENTER);
                scorePlayer.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 80));

                scoreOpponent.setEffect(ds);
                scoreOpponent.setText(String.valueOf(enemy.hp));
                scoreOpponent.setFill(Color.WHITE);
                scoreOpponent.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 80));
                scoreOpponent.setX(960);
                scoreOpponent.setY(420);
                scoreOpponent.setTextAlignment(TextAlignment.CENTER);

                winPane.getChildren().addAll(scorePlayer);
                winPane.getChildren().addAll(scoreOpponent);

                window.getScene().setRoot(winPane); /* nowe */
                window.show();
            }
            else
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/End.fxml"));
                Pane endPane = fxmlLoader.load();

                Stage window = Main.primaryStage2;

                System.out.println("koniec");
                window.getScene().setRoot(endPane); /* nowe */
                window.show();
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    void lose() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/Lose.fxml"));
            Pane losePane = fxmlLoader.load();

            Stage window = Main.primaryStage2;

            /* stare
            Stage scoreStage = new Stage();
            scoreStage.setScene(new Scene(losePane));
            scoreStage.show();
            scoreStage.setAlwaysOnTop(false);
            scoreStage.setOpacity(1);
            scoreStage.setTitle("Wynik");
            scoreStage.getIcons().add(new Image("pictures/icon.png"));

             */
            System.out.println("Wynik");

            DropShadow ds = new DropShadow();
            ds.setOffsetY(10.0f);
            ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

            Text scorePlayer = new Text();
            Text scoreOpponent = new Text();

            scorePlayer.setEffect(ds);
            scorePlayer.setText(String.valueOf(player.hp));
            scorePlayer.setFill(Color.WHITE);
            scorePlayer.setX(160);
            scorePlayer.setY(420);
            scorePlayer.setTextAlignment(TextAlignment.CENTER);
            scorePlayer.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 80));

            scoreOpponent.setEffect(ds);
            scoreOpponent.setText(String.valueOf(enemy.hp));
            scoreOpponent.setFill(Color.WHITE);
            scoreOpponent.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 80));
            scoreOpponent.setX(960);
            scoreOpponent.setY(420);
            scoreOpponent.setTextAlignment(TextAlignment.CENTER);

            losePane.getChildren().addAll(scorePlayer);
            losePane.getChildren().addAll(scoreOpponent);

            window.getScene().setRoot(losePane); /* nowe */
            window.show();


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
