package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckOfCards {

    private List<Card> deck = new ArrayList<>();

    private Random generator = new Random();

    private int size = 0;

    int numberOfWeakcards = 0;
    int numberOfMediumcards = 0;
    int numberOfGoodCards = 0;

    public DeckOfCards() {

        setDeck();
    }

    public void setDeck() {

        for(int i = 0 ; i < 29; i++) {

            Card drawCard = AllCard.getCard(i);

            Card copyCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                    drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

            deck.add(copyCard);
        }
    }

    private void supDraw(){

        if(numberOfWeakcards <= 8) {

            int randomCardId = generator.nextInt(9);
            Card drawCard = AllCard.getCard(randomCardId);

            if (!drawCard.alreadyRandToDeck){

                deck.add(drawCard);
                drawCard.alreadyRandToDeck = true;

                drawCard.repeated++;
                numberOfWeakcards ++;
            }
            else if (drawCard.repeated <= 2){

                Card newCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                        drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

                deck.add(newCard);

                drawCard.repeated++;
                numberOfWeakcards ++;
            }
            else {
                supDraw();
            }

        }
        else if(numberOfMediumcards <= 7) {

            int randomCardId = generator.nextInt(10) + 9;
            Card drawCard = AllCard.getCard(randomCardId);

            if (!drawCard.alreadyRandToDeck){

                deck.add(drawCard);
                drawCard.alreadyRandToDeck = true;

                numberOfMediumcards ++;
                drawCard.repeated++;
            }
            else if (drawCard.repeated <= 2){

                Card newCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                        drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

                deck.add(newCard);

                numberOfMediumcards ++;
                drawCard.repeated++;
            }
            else {
                supDraw();
            }
        }
        else if(numberOfGoodCards <= 5) {

            int randomCardId = generator.nextInt(7) + 19;
            Card drawCard = AllCard.getCard(randomCardId);

            if (!drawCard.alreadyRandToDeck){

                deck.add(drawCard);
                drawCard.alreadyRandToDeck = true;

                numberOfGoodCards ++;
                drawCard.repeated++;
            }
            else if (drawCard.repeated <= 2){

                Card newCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                        drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

                deck.add(newCard);

                numberOfGoodCards++;
                drawCard.repeated++;
            }
            else {
                supDraw();
            }
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
