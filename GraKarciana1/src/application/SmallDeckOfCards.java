package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmallDeckOfCards {

    private List<Card> smallDeck = new ArrayList<>();

    private Random generator = new Random();

    private int size = 0;


    public SmallDeckOfCards() {
/*
        smallDeck.add(new Card("/pictures/card1.png", 1));
        smallDeck.add(new Card("/pictures/card2.png", 2));
        smallDeck.add(new Card("/pictures/card3.png", 3));
        smallDeck.add(new Card("/pictures/card4.png", 4));
        smallDeck.add(new Card("/pictures/card5.png", 5));
        smallDeck.add(new Card("/pictures/card6.png", 6));

 */
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
