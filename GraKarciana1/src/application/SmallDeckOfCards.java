package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmallDeckOfCards {

    private List<Card> smallDeck = new ArrayList<>();

    private Random generator = new Random();

    private int size = 0;


    public SmallDeckOfCards() {

    }

    public void setSmallDeck(DeckOfCards deck) {

        for(int i = 0 ; i < 15; i++) {

            supDraw(deck);
        }
    }

    private void supDraw(DeckOfCards deck){

        Card drawCard = deck.getCard();
        if(!drawCard.alreadyRandToSmallDeck) {

            smallDeck.add(drawCard);
            drawCard.alreadyRandToSmallDeck = true;
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
