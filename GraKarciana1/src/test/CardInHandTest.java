package test;

import application.AllCard;
import application.Card;
import application.CardInHand;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CardInHandTest {

    CardInHand cardInHand;
    List<Card> cardsInHand;

    @BeforeClass
    public static void setUpClass() {

        AllCard.createCards();
    }

    @Before
    public void setUp() {

        cardInHand = new CardInHand(10,10, new Pane());
        cardsInHand = cardInHand.getCardsInHand();

        for(int i =0 ; i<5; i++) {

            cardsInHand.add(AllCard.getCard(i));
        }
    }
    @Test
    public void isMoveCard() {

        for(int i =0 ; i < 11 ; i++) {
            cardInHand.inHandPosition[i] = 1;
        }

        cardInHand.inHandPosition[3] = 0;

        cardInHand.moveCard();

        assertEquals(270, cardInHand.getPositionX());
    }

    @Test
    public void isSearchCard() {

        int idInHand = cardInHand.searchCard(1);

        assertEquals(0, idInHand);

        idInHand = cardInHand.searchCard(60);

        assertEquals(-1, idInHand);
    }
}


