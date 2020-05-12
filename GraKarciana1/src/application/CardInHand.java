package application;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardInHand {

    private List<Card> cardsInHand = new ArrayList<>();

    public Pane hand = new Pane();

    private Card cardToThrow;

    public boolean readyToThrown = false;
    private boolean initialization = false;
    private int initNumber = 0;

    private int positionX = 0;
    public int[] inHandPosition = new int[9];

    private Random generator = new Random();

    private int size = 0;


    public CardInHand(int handPositionX, int handPositionY, Pane table) {

        table.getChildren().add(hand);

        hand.setLayoutX(handPositionX);
        hand.setLayoutY(handPositionY);
        hand.setPrefSize(100,20);


        for(int i = 0; i<9; i++) {

            inHandPosition[i] = 0;
        }
    }

    public void chooseCardFromHand() {

        for (Card cardFromHand: cardsInHand) {

            boolean chosen = cardFromHand.getChosen();

            if (chosen) {

                cardToThrow = cardFromHand;

                readyToThrown = true;
            }
        }

        cardsInHand.remove(cardToThrow);
    }

    public Card getCardFromHand() {

        return cardToThrow;
    }

    public Card getCardFromEnemyHand() {

        setSize();

        int randomCardId = generator.nextInt(size);

        Card drawCard = cardsInHand.get(randomCardId);

        cardsInHand.remove(drawCard);

        return drawCard;
    }

    public void drawCardsFromSmallDeck(SmallDeckOfCards smallDeck) {

        Card drawCard = smallDeck.getCardFromSmallDeck();
        cardsInHand.add(drawCard);
        drawAnimation(drawCard);

        smallDeck.setSize();

        if(smallDeck.getSize() > 0) {

            drawCard = smallDeck.getCardFromSmallDeck();
            cardsInHand.add(drawCard);
            drawAnimation(drawCard);
        }
    }

    public void DrawArrowCards(){

        Card drawCard = AllCard.getCard(12);

        Card newCard1 = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        cardsInHand.add(newCard1);
        drawAnimation(newCard1);

        Card newCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        cardsInHand.add(newCard);
        drawAnimation(newCard);
    }

    public void initHand(SmallDeckOfCards smallDeck) {

        initialization = true;

        for(int i=0; i<3; i++) {

            Card drawCard = smallDeck.getCardFromSmallDeck();
            cardsInHand.add(drawCard);

            drawAnimation(drawCard);
        }
    }

    private void drawAnimation(Card drawCard) {

        moveCard();

        drawCard.positionOnTable = positionX/100;
        inHandPosition[positionX/100] = 1;

        hand.getChildren().add(drawCard.imageView);
        drawCard.imageView.fitWidthProperty().bind(hand.widthProperty());
        drawCard.imageView.setPreserveRatio(true);
        drawCard.imageView.setX(positionX);

        TranslateTransition t = new TranslateTransition(Duration.millis(1500), drawCard.imageView);
        t.setFromX(1000);
        // t.setFromY(0);
        t.setToX(positionX - drawCard.imageView.getX());

        t.play();

        t.setOnFinished((ActionEvent evt) -> {

            if(initialization = true) {
                initNumber++;

                if(initNumber == 3) {
                    Game.ready = true;
                    initialization = false;
                }
            }
            t.stop();
        });
    }

    public boolean emptyHand ()
    {
        if(cardsInHand.size() != 0) {
            return false;
        } else {
            return true;
        }
    }

    private void moveCard () {

        for(int i=0; i<9; i++) {

            if(inHandPosition[i] == 0) {

                positionX = 105 * i;
                break;
            }
        }
    }

    public void setSize() {

        size = cardsInHand.size();
    }

    public int getSize() {

        return size;
    }
}