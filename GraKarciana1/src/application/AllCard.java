package application;

import java.util.ArrayList;
import java.util.List;

public class AllCard {
    /**
     * Funkcja tworząca wszystkie karty w grze
     */
    public static List<Card> allCard = new ArrayList<>();
    public static List<Card> summonCard = new ArrayList<>();

    public AllCard() {
        createCards();
    }

    public static void createCards() {

        //Card nothing = new Card("Nothing", 0, 0, 0, 0, Card.cardType.Magic, "Nothing", 0);
        //allCard.add(nothing);

        //karty value 1 (0-9) 10

        Card fireBall = new Card("FireBall", 1, 1, 10, 0, Card.cardType.Fire, "/pictures/fireball.png", 0);
        allCard.add(fireBall);

        Card fog = new Card("Fog", 5, 1, 0, 0, Card.cardType.Water, "/pictures/mgła.png", 0);
        allCard.add(fog);

        Card iceBolt = new Card("Ice Bolt", 7, 1, 10, 0, Card.cardType.Ice, "/pictures/lodowy_pocisk.png", 0);
        allCard.add(iceBolt);

        Card sword = new Card("Sword", 10, 1, 10, 0, Card.cardType.Weapon, "/pictures/miecz.png", 0);
        allCard.add(sword);

        Card axe = new Card("Axe", 11, 1, 15, 0, Card.cardType.Weapon, "/pictures/topór.png", 0);
        allCard.add(axe);

        Card bow = new Card("Bow", 12, 1, 10, 0, Card.cardType.Weapon, "/pictures/łuk.png", 0);
        allCard.add(bow);

        Card shield = new Card("Shield", 14, 1, 0, 10, Card.cardType.Weapon, "/pictures/tarcza.png", 0);
        allCard.add(shield);

        Card potionOfPoison = new Card("Potion of Poison", 20, 1, 0, 0, Card.cardType.Potion, "/pictures/fiolka_trucizny.png", 0);
        allCard.add(potionOfPoison);

        Card burnout = new Card("Burnout", 28,1,0,0, Card.cardType.Magic,"/pictures/wypalenie.png",0);
        allCard.add(burnout);

        Card tulip = new Card("Tulip", 22, 1, 5, 0, Card.cardType.Potion, "/pictures/tulipan.png", 0);
        allCard.add(tulip);


        //karty value 2 (10-21) 12

        Card flameArmor = new Card("Flame Armor", 2, 2, 0, 15, Card.cardType.Fire, "/pictures/ognista_zbroja.png", 0);
        allCard.add(flameArmor);

        Card tsunami = new Card("Tsunami", 4, 2, 0, 0, Card.cardType.Water, "/pictures/tsunami.png", 0);
        allCard.add(tsunami);

        Card heartOfIce = new Card("Heart of ice", 8, 2, 15, 0, Card.cardType.Ice, "/pictures/lodowe_serce.png", 0);
        allCard.add(heartOfIce);

        Card herculesMace = new Card("Hercules's mace", 16, 2, 15, 0, Card.cardType.Weapon, "/pictures/maczuga_herkulesa.png", 0);
        allCard.add(herculesMace);

        Card mace = new Card("Mace", 17, 2, 10, 0, Card.cardType.Weapon, "/pictures/buława.png", 0);
        allCard.add(mace);

        Card dragonKiller = new Card("Dragon Killer", 18, 2, 15, 0, Card.cardType.Weapon, "/pictures/smokobójca.png", 0);
        allCard.add(dragonKiller);

        Card potionOfLife = new Card("Potion of Life", 19, 2, 0, 0, Card.cardType.Potion, "/pictures/fiolka_życia.png", 15);
        allCard.add(potionOfLife);

        Card ariadneThread = new Card("Ariadne's Thread",26,2,0,0, Card.cardType.Magic,"/pictures/nić_ariadny.png",20);
        allCard.add(ariadneThread);

        Card timeOfLifeAndDeath = new Card("Time of life and death",30,2,0,0, Card.cardType.Magic,"/pictures/pora_życia_i_śmierci.png",0);
        allCard.add(timeOfLifeAndDeath);

        Card joker = new Card("Joker", 23, 2, 0, 0, Card.cardType.Magic, "/pictures/joker.png", 0);
        allCard.add(joker);

        Card perseusShield = new Card("Perseus shield", 63, 2, 0, 0, Card.cardType.Weapon, "/pictures/tarcza_perseusza.png", 0);
        allCard.add(perseusShield);

        Card medusaLook = new Card("Medusa Look", 24,2,0,0, Card.cardType.Magic,"/pictures/spojrzenie_meduzy.png",0);
        allCard.add(medusaLook);


        //karty value 3 (22 - 29) 8

        Card prometheanFire = new Card("Stal Hefajstosa", 3, 3, 20, 10, Card.cardType.Fire, "/pictures/stal_hefajstosa.png", 0);
        allCard.add(prometheanFire);

        Card poseidonTrident = new Card("Poseidon's trident", 6, 3, 20, 0, Card.cardType.Water, "/pictures/trójząb_posejdona.png", 0);
        allCard.add(poseidonTrident);

        Card chioneAttribute = new Card("Chione attribute", 9, 3, 0, 0, Card.cardType.Ice, "/pictures/atrybut_chione.png", 0);
        allCard.add(chioneAttribute);

        Card dragonBlood = new Card("Dragon Blood", 21, 3, 0, 25, Card.cardType.Potion, "/pictures/smocza_krew.png", 0);
        allCard.add(dragonBlood);

        Card summonCerberus = new Card("Summon Cerberus",25,3,0,0, Card.cardType.Magic,"/pictures/przywołanie_cerbera.png",0);
        allCard.add(summonCerberus);

        Card crownOfImmortality = new Card("Crown of Immortality",27,3,0,0, Card.cardType.Magic,"/pictures/korona_nieśmiertelności.png",0);
        allCard.add(crownOfImmortality);

        Card charon = new Card("Charon",29,3,0,0, Card.cardType.Magic,"/pictures/charon.png",0);
        allCard.add(charon);

        Card angerOfGod = new Card("Anger of God",58,3,20,0, Card.cardType.Magic,"/pictures/gniew_boga.png",0);
        allCard.add(angerOfGod);



        // Zeus

        Card lightningOfZeus = new Card("lightning of Zeus", 15, 3, 25, 0, Card.cardType.Weapon, "/pictures/piorun_zeusa.png", 0);
        allCard.add(lightningOfZeus);

        //summony

        Card arrow = new Card("Arrow", 13, 1, 7, 0, Card.cardType.Weapon, "/pictures/strzała.png", 0);
        summonCard.add(arrow);
    }

    public static Card getCard(int id) {

        return allCard.get(id);
    }
}
