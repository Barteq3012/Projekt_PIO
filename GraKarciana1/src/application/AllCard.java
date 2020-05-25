package application;

import java.util.ArrayList;
import java.util.List;

public class AllCard {
    /**
     * Funkcja tworząca wszystkie karty w grze
     */
    public static List<Card> allCard = new ArrayList<>();
    public static List<Card> summonCard = new ArrayList<>();

    public static void createCards() {

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


        // Helios (30-36) 7

        Card spark = new Card("Spark",31,2,10,0, Card.cardType.Fire,"/pictures/iskry.png",0);
        allCard.add(spark);

        Card raysOfTheSun = new Card("Rays of the Sun",32,1,0,0, Card.cardType.Magic,"/pictures/promienie_slonca.png",0);
        allCard.add(raysOfTheSun);

        Card dazzle = new Card("Dazzle",33,2,0,0, Card.cardType.Magic,"/pictures/oslepienie.png",0);
        allCard.add(dazzle);

        Card sunSword = new Card("Sun sword",34,3,30,0, Card.cardType.Weapon,"/pictures/miecz_slonca.png",0);
        allCard.add(sunSword);

        Card sunInBottle = new Card("Sun in bottle",35,2,0,15, Card.cardType.Potion,"/pictures/slonce_w_butelce.png",15);
        allCard.add(sunInBottle);

        Card chariot = new Card("Chariot",36,1,10,0, Card.cardType.Weapon,"/pictures/rydwan.png",0);
        allCard.add(chariot);

        Card dropTorch = new Card("Drop torch",37,2,10,0, Card.cardType.Weapon,"/pictures/upuszczenie_pochodni.png",0);
        allCard.add(dropTorch);

        // Pandora (37-41) 5

        Card pandorasBox = new Card("Pandora's box",38,3,20,0, Card.cardType.Magic,"/pictures/puszka_pandory.png",0);
        allCard.add(pandorasBox);

        Card duplicity = new Card("Duplicity",39,1,0,0, Card.cardType.Magic,"/pictures/obluda.png",0);
        allCard.add(duplicity);

        Card godsGift = new Card("Gods gift",40,2,0,0, Card.cardType.Magic,"/pictures/dar_bogow.png",0);
        allCard.add(godsGift);

        Card swallowSpell = new Card("Swallow Spell",43,3,0,0, Card.cardType.Magic,"/pictures/czar_jaskolki.png",0);
        allCard.add(swallowSpell);

        Card pandemonium = new Card("Pandemonium",44,3,0,0, Card.cardType.Fire,"/pictures/pandemonium.png",0);
        allCard.add(pandemonium);

        // Hydra (42-47) 6

        Card hydraBlood = new Card("Hydra blood",45,2,0,0, Card.cardType.Magic,"/pictures/krew_hydry.png",10);
        allCard.add(hydraBlood);

        Card hydraHead = new Card("Hydra head",47,2,10,0, Card.cardType.Weapon,"/pictures/glowa_hydry.png",0);
        allCard.add(hydraHead);

        Card typhonSummon = new Card("Typhon summon",48,3,0,0, Card.cardType.Magic,"/pictures/przywolanie_tyfona.png",0);
        allCard.add(typhonSummon);

        Card lernaHerb = new Card("Lerna herb",49,3,0,15, Card.cardType.Magic,"/pictures/ziolo_lernejskie.png",15);
        allCard.add(lernaHerb);

        Card goldenFleece = new Card("Golden fleece",50,2,0,0, Card.cardType.Magic,"/pictures/zlote_runo.png",0);
        allCard.add(goldenFleece);

        Card snakeBite = new Card("Snake bite",51,1,10,0, Card.cardType.Weapon,"/pictures/ukaszenie_weza.png",0);
        allCard.add(snakeBite);

        // Zeus (48-53) 6

        Card lightningOfZeus = new Card("lightning of Zeus", 15, 3, 25, 0, Card.cardType.Weapon, "/pictures/piorun_zeusa.png", 0);
        allCard.add(lightningOfZeus);

        Card ambrosia = new Card("Ambrosia",53,3,0,0, Card.cardType.Magic,"/pictures/ambrozja.png",20);
        allCard.add(ambrosia);

        Card egida = new Card("Egida",54,2,0,10, Card.cardType.Weapon,"/pictures/egida.png",0);
        allCard.add(egida);

        Card soter = new Card("Soter",55,3,0,0, Card.cardType.Magic,"/pictures/soter.png",0);
        allCard.add(soter);

        Card feniks = new Card("Feniks",56,2,0,0, Card.cardType.Fire,"/pictures/feniks.png",0);
        allCard.add(feniks);

        Card lordOfTheGods = new Card("Lord of the gods",57,3,0,0, Card.cardType.Magic,"/pictures/wladca_bogow.png",0);
        allCard.add(lordOfTheGods);

        // Ethon (54-59) 6

        Card appetite = new Card("Appetite",59,2,0,0, Card.cardType.Magic,"/pictures/apetyt.png",10);
        allCard.add(appetite);

        Card claws = new Card("Claws",60,2,25,0, Card.cardType.Weapon,"/pictures/szpony.png",0);
        allCard.add(claws);

        Card rainOfFeathers = new Card("Rain of feathers",61,2,0,15, Card.cardType.Weapon,"/pictures/deszcz_pior.png",0);
        allCard.add(rainOfFeathers);

        Card gliding = new Card("Gliding",62,2,0,0, Card.cardType.Magic,"/pictures/szybowanie.png",0);
        allCard.add(gliding);

        Card quilting = new Card("Quilting",52,3,0,0, Card.cardType.Weapon,"/pictures/pikowanie.png",0);
        allCard.add(quilting);

        Card poisonResistance = new Card("Poison resistance",64,2,0,0, Card.cardType.Water,"/pictures/odpornosc_na_zatrucie.png",10);
        allCard.add(poisonResistance);

        // nagroda na koniec kampanii (60) 1

        Card ethonSlayer = new Card("Ethon slayer",65,4,50,0, Card.cardType.Weapon,"/pictures/pogromca_ethona.png",0);
        allCard.add(ethonSlayer);

        //summony

        Card arrow = new Card("Arrow", 13, 1, 7, 0, Card.cardType.Weapon, "/pictures/strzała.png", 0);
        summonCard.add(arrow);

        Card sweetness = new Card("Sweetness",41,2,0,0, Card.cardType.Water,"/pictures/slodkosc.png",20);
        summonCard.add(sweetness);

        Card pain = new Card("Pain",42,2,0,0, Card.cardType.Fire,"/pictures/cierpienie.png",0);
        summonCard.add(pain);

        Card bloodArrow = new Card("Blood Arrow",46,2,15,0, Card.cardType.Weapon,"/pictures/krwista_strzala.png",10);
        summonCard.add(bloodArrow);
    }

    public static Card getCard(int id) {

        Card drawCard = allCard.get(id);

        Card copyCard = new Card(drawCard.getName(),drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        return copyCard;
    }
}
