package test;

import application.*;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    Player player;
    Player enemy;

    @BeforeClass
    public static void setUpClass() {

        AllCard.createCards();
        Game.table = new Pane();
    }

    @Before
    public void setUp() {

        Pane table = new Pane();
        CardInHand cardInHand = new CardInHand(100,100,table);
        player = new Player("player", 1,100, 10, cardInHand);
        enemy = new Player("enemy", 2,100, 10, cardInHand);
        enemy.setMyEnemy(player);
        player.setMyEnemy(enemy);
    }

    @Test
    public void isCardDealDamage() {

        int previousPlayerHpPlusSP = player.hp + player.sp;

        enemy.card = AllCard.allCard.get(33);
        enemy.throwCard();

        int actualPlayerHpPlusSP = player.hp + player.sp;

        assertEquals( previousPlayerHpPlusSP - enemy.card.getDamage(),actualPlayerHpPlusSP);
    }

    @Test
    public void isCardsAddEffects() {

        enemy.card = AllCard.allCard.get(7); // +4 poison
        enemy.throwCard();

        enemy.card = AllCard.allCard.get(0); // +6 burn
        enemy.throwCard();

        enemy.card = AllCard.allCard.get(44); // +4 posion + 6 burn
        enemy.throwCard();

        enemy.card = AllCard.allCard.get(9); // +8 bleed
        enemy.throwCard();

        enemy.card = AllCard.allCard.get(47); // +4 posion
        enemy.throwCard();

        assertEquals(12, player.poisonTime);
        assertEquals(12, player.burnTime);
        assertEquals(8, player.bleedingTime);

    }

    @Test
    public void isPlayerImmune() {

        int previousPlayerHpPlusSP = player.hp + player.sp;

        player.card = AllCard.allCard.get(46); //immune
        player.throwCard();

        enemy.card = AllCard.allCard.get(0); //damage
        enemy.throwCard();

        int actualPlayerHpPlusSP = player.hp + player.sp;

        assertEquals(previousPlayerHpPlusSP, actualPlayerHpPlusSP);
    }

    @Test
    public void checkEffectTest() {

        int previousPlayerHpPlusSP = player.hp + player.sp;

        player.card = AllCard.allCard.get(27); // korona +2hp
        player.throwCard();

        enemy.card = AllCard.allCard.get(44); // +4 posionTime -5hp + 6 burnTime -3hp
        enemy.throwCard();

        enemy.card = AllCard.allCard.get(26); // +8 bleedTime -2hp + 4 posionTime -5hp
        enemy.throwCard();

        int previousBurnTime = player.burnTime;
        int previousPosionTime = player.poisonTime;
        int previousBleedTime = player.bleedingTime;

        player.checkEffects();

        int actualPlayerHpPlusSP = player.hp + player.sp;

        assertEquals(previousPlayerHpPlusSP + 2 - 5 - 3 - 2, actualPlayerHpPlusSP);
        assertEquals(previousPosionTime-1, player.poisonTime);
        assertEquals(previousBurnTime-1, player.burnTime);
        assertEquals(previousBleedTime-1, player.bleedingTime);
    }

    @Test
    public void dazzleTest() {

        int previousPlayerHp = player.hp;
        int previousPlayerSp = player.sp;

        enemy.card = AllCard.allCard.get(32);
        enemy.throwCard();

        int actualPlayerHp = player.hp;
        int actualPlayerSp = player.sp;

        int expectedPlayerSP;

        if(previousPlayerSp >= 15) {
            expectedPlayerSP = previousPlayerSp - 15;
        }
        else {
            expectedPlayerSP = 0;
        }

        assertEquals( actualPlayerHp,previousPlayerHp);
        assertEquals( expectedPlayerSP,actualPlayerSp);
    }
}


