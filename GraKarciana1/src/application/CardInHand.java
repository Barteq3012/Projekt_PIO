package application;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class CardInHand {

    private List<Card> cardsInHand = new ArrayList<>();

    public Pane hand = new Pane();

    private Card cardToThrow;
    private Card drawCard;

    public boolean readyToThrown = false;
    public boolean alreadyThrown = false;

    private int positionX = 0;
    public int[] inHandPosition = new int[9];


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

                if (chosen == true) {

                    cardToThrow = cardFromHand;

                    readyToThrown = true;
                }
            }

        cardsInHand.remove(cardToThrow);
        alreadyThrown = true;
    }

    public Card getCardFromHand() {

        return cardToThrow;
    }

    public void drawCardsFromSmallDeck(SmallDeckOfCards smallDeck) {

        moveCard();

        drawCard = smallDeck.getCardFromSmallDeck();
        cardsInHand.add(drawCard);

        drawCard.positionOnTable = positionX/100;
        inHandPosition[positionX/100] = 1;

        hand.getChildren().add(drawCard.imageView);
        drawCard.imageView.setX(positionX);
        drawCard.imageView.fitWidthProperty().bind(hand.widthProperty());
        drawCard.imageView.setPreserveRatio(true);
    }
    public void DrawArrowCards(){
        Card drawCard = AllCard.GetCard(12);
        cardsInHand.add(drawCard);
        cardsInHand.add(drawCard);
    }

    public void initHand(SmallDeckOfCards smallDeck) {

        for(int i=0; i<3; i++) {

            drawCard = smallDeck.getCardFromSmallDeck();
            cardsInHand.add(drawCard);

            drawCard.positionOnTable = i;
            inHandPosition[i] = 1;

            hand.getChildren().add(drawCard.imageView);
            drawCard.imageView.setX(positionX);
            drawCard.imageView.fitWidthProperty().bind(hand.widthProperty());
            drawCard.imageView.setPreserveRatio(true);

            moveCard();
        }
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

                positionX = 110 * i;
                break;
            }
        }
    }

}

