package application;

import java.util.ArrayList;
import java.util.List;

public class AllCard {
    /**
     * Funkcja tworząca wszystkie karty w grze
     */
    private static final List<Card> allCard = new ArrayList<>();

    public AllCard() {
        CreateCards();
    }

    private void CreateCards() {
        Card nothing = new Card("Nothing", 0, 0, 0, 0, Card.cardType.Magic, "Nothing", 0);
        allCard.add(nothing);
        Card fireBall = new Card("FireBall", 1, 1, 10, 0, Card.cardType.Fire, "FireBall", 0);
        allCard.add(fireBall);
        Card flameArmor = new Card("Flame Armor", 2, 2, 1, 10, Card.cardType.Fire, "Flame Armor", 0);
        allCard.add(flameArmor);
        Card prometheanFire = new Card("Promethean fire", 3, 3, 0, 0, Card.cardType.Fire, "Promethean fire", 15);
        allCard.add(prometheanFire);
        Card tsunami = new Card("Tsunami", 4, 2, 0, 0, Card.cardType.Water, "Tsunami", 0);
        allCard.add(tsunami);
        Card fog = new Card("Fog", 5, 1,0,0, Card.cardType.Water,"Fog",0);
        allCard.add(fog);
        Card poseidonTrident = new Card("Poseidon's trident", 6,3,20,0, Card.cardType.Water,"Poseidon's trident",0);
        allCard.add(poseidonTrident);
        Card iceBolt = new Card("Ice Bolt", 7, 1, 10, 0, Card.cardType.Ice, "Ice Bolt", 0);
        allCard.add(iceBolt);
        Card heartOfIce = new Card("Heart of ice",7,2,15,0, Card.cardType.Ice,"Heart of ice",0);
        allCard.add(heartOfIce);
        Card chioneAttribute = new Card("Chione attribute", 8,3,0,0, Card.cardType.Ice,"Chione attribute",0);
        allCard.add(chioneAttribute);
        Card sword = new Card("Sword",9,1,10,0, Card.cardType.Weapon,"Sword",0);
        allCard.add(sword);
        Card axe = new Card("Axe", 10,1,15,0, Card.cardType.Weapon,"Axe",-5);
        allCard.add(axe);
        Card bow = new Card("Bow", 11,1,7,0, Card.cardType.Weapon,"Bow",0);
        allCard.add(bow);
        Card arrow = new Card("Arrow", 12,1,5,0, Card.cardType.Weapon,"Arrow",0);
        allCard.add(arrow);




    }

    public static Card GetCard(int id) {
        return allCard.get(id);
    }
}
