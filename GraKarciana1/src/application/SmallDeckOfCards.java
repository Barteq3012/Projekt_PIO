package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmallDeckOfCards {

    private List<Card> smallDeck = new ArrayList<>();

    private Random generator = new Random();

    private int size = 0;

    int numberOfWeakCards = 0;
    int numberOfMediumCards = 0;
    int numberOfGoodCards = 0;


    public SmallDeckOfCards() {

    }

    public void setSmallDeck(DeckOfCards deck) {

        for(int i = 0 ; i < 15; i++) {

            supDraw(deck);
        }
    }

    public void setSmallDeckBoss1(DeckOfCards deck) {

        for(int i = 30 ; i < 37; i++) {

            smallDeck.add(AllCard.getCard(i));
        }

        for(int i = 0 ; i < 3; i++) {

            randValue1Card(deck);
        }

        for(int i = 0 ; i < 3; i++) {

            randValue2Card(deck);
        }

        for(int i = 0 ; i < 2; i++) {

            randValue3Card(deck);
        }
    }

    public void setSmallDeckBoss2(DeckOfCards deck) {

        for(int i = 37 ; i < 42; i++) {

            smallDeck.add(AllCard.getCard(i));
        }

        for(int i = 0 ; i < 3; i++) {

            randValue1Card(deck);
        }

        for(int i = 0 ; i < 4; i++) {

            randValue2Card(deck);
        }

        for(int i = 0 ; i < 3; i++) {

            randValue3Card(deck);
        }
    }

    public void setSmallDeckBoss3(DeckOfCards deck) {

        for(int i = 42 ; i < 48; i++) {

            smallDeck.add(AllCard.getCard(i));
        }

        smallDeck.add(AllCard.getCard(5));

        randValue1Card(deck);

        for(int i = 0 ; i < 3; i++) {

            randValue2Card(deck);
        }

        for(int i = 0 ; i < 4; i++) {

            randValue3Card(deck);
        }
    }

    public void setSmallDeckBoss4(DeckOfCards deck) {

        for(int i = 48 ; i < 54; i++) {

            smallDeck.add(AllCard.getCard(i));
        }

        for(int i = 0 ; i < 2; i++) {

            randValue1Card(deck);
        }

        for(int i = 0 ; i < 2; i++) {

            randValue2Card(deck);
        }

        for(int i = 0 ; i < 5; i++) {

            randValue3Card(deck);
        }
    }

    public void setSmallDeckBoss5(DeckOfCards deck) {

        for(int i = 54 ; i < 60; i++) {

            smallDeck.add(AllCard.getCard(i));
        }

        for(int i = 0 ; i < 3; i++) {

            randValue2Card(deck);
        }

        for(int i = 0 ; i < 6; i++) {

            randValue3Card(deck);
        }
    }

    private void randValue1Card(DeckOfCards deck) {

        int randomCardId = generator.nextInt(10);

        Card drawCard = deck.getCard(randomCardId);

        if(!drawCard.alreadyRandToSmallDeck) {

            smallDeck.add(drawCard);
            drawCard.alreadyRandToSmallDeck = true;
        }
        else {

            randValue1Card(deck);
        }
    }

    private void randValue2Card(DeckOfCards deck) {

        int randomCardId = generator.nextInt(12) + 10;

        Card drawCard = deck.getCard(randomCardId);

        if(!drawCard.alreadyRandToSmallDeck) {

            smallDeck.add(drawCard);
            drawCard.alreadyRandToSmallDeck = true;
        }
        else {

            randValue2Card(deck);
        }
    }

    private void randValue3Card(DeckOfCards deck) {

        int randomCardId = generator.nextInt(8) + 22;

        Card drawCard = deck.getCard(randomCardId);

        if(!drawCard.alreadyRandToSmallDeck) {

            smallDeck.add(drawCard);
            drawCard.alreadyRandToSmallDeck = true;
        }
        else {

            randValue3Card(deck);
        }
    }

    private void supDraw(DeckOfCards deck){

        int randomCardId = generator.nextInt(deck.getSize());

        Card drawCard = deck.getCard(randomCardId);

        if(drawCard.getValue() == 1 && !drawCard.alreadyRandToSmallDeck) {

            if(numberOfWeakCards < 5 ) {

                numberOfWeakCards++;
                smallDeck.add(drawCard);
                drawCard.alreadyRandToSmallDeck = true;
            }
            else {
                supDraw(deck);
            }
        }
        else if(drawCard.getValue() == 2 && !drawCard.alreadyRandToSmallDeck) {

            if(numberOfMediumCards < 6 ) {

                numberOfMediumCards++;
                smallDeck.add(drawCard);
                drawCard.alreadyRandToSmallDeck = true;
            }
            else {
                supDraw(deck);
            }
        }
        else if((drawCard.getValue() == 3 || drawCard.getValue() == 4)  && !drawCard.alreadyRandToSmallDeck) {

            if(numberOfGoodCards < 4 ) {

                numberOfGoodCards++;
                smallDeck.add(drawCard);
                drawCard.alreadyRandToSmallDeck = true;
            }
            else {
                supDraw(deck);
            }
        }
        else {
            supDraw(deck);
        }
    }

    public Card getCardFromSmallDeck() {

        setSize();

        int randomCardId = generator.nextInt(size);

        Card drawCard = smallDeck.get(randomCardId);

        smallDeck.remove(drawCard);

        return drawCard;
    }

    public void setSize() {

        size = smallDeck.size();
    }

    public int getSize() {

        return size;
    }
}
