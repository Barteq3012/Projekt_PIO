package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckOfCards {

    private List<Card> deck = new ArrayList<>();

    private Random generator = new Random();

    private int size = 0;

    public DeckOfCards() {

        setDeck();
    }

    public void setDeck() {

        for(int i = 0 ; i < AllCard.allCard.size(); i++) {

            supDraw();
        }
    }

    private void supDraw(){

        int randomCardId = generator.nextInt(AllCard.allCard.size());
        Card drawCard = AllCard.getCard(randomCardId);

        if(drawCard.alreadyRandToDeck && drawCard.getValue() == 1) {

            Card newCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                    drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

            deck.add(newCard);
        }
        else if (!drawCard.alreadyRandToDeck){

            deck.add(drawCard);
            drawCard.alreadyRandToDeck = true;
        }
        else {
            supDraw();
        }
    }

    public Card getCard() {

        int randomCardId = generator.nextInt(deck.size());

        Card drawCard = deck.get(randomCardId);

       /* if(drawCard.alreadyRandToSmallDeck) {

            Card newCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                    drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

            return  newCard;
        }
        else {
            drawCard.alreadyRandToSmallDeck = true;
            return  drawCard;
        }*/

        return drawCard;
    }

    public int getSize() {

        return deck.size();
    }
}
