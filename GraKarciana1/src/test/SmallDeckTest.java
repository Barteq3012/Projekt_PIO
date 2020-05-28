package test;

import application.AllCard;
import application.CardInHand;
import application.DeckOfCards;
import application.SmallDeckOfCards;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SmallDeckTest {

    SmallDeckOfCards smallDeck;
    DeckOfCards deck;
    CardInHand cardInHand;

    @BeforeClass
    public static void setUpClass() {

        AllCard.createCards();
    }

    @Before
    public void setUp() {

        deck = new DeckOfCards(1);
        deck.setDeck();
        smallDeck = new SmallDeckOfCards();
        cardInHand = new CardInHand(10,10, new Pane());

    }

    @Test
    public void isSmallDeckCorrectSet() {

        smallDeck.setSmallDeck(deck);

        assertEquals(15, smallDeck.getSize());
        assertEquals(4, smallDeck.numberOfGoodCards);
        assertEquals(6, smallDeck.numberOfMediumCards);
        assertEquals(5, smallDeck.numberOfWeakCards);
    }

    @Test
    public void isSmallDeckGetCard() {

        smallDeck.setSmallDeck(deck);

        assertNotNull(smallDeck.getCardFromSmallDeck());
    }

    @Test
    public void isInitHand() {

        smallDeck.setSmallDeck(deck);

        int previousSize = smallDeck.getSize();

        cardInHand.initHand(smallDeck);

        assertEquals(previousSize - 3 , smallDeck.getSize());
    }
}
