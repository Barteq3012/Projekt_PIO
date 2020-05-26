package application;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardInHand {

    private List<Card> cardsInHand = new ArrayList<>();
    private List<Card> cubby = new ArrayList<>(); //schowek

    public Pane hand = new Pane();

    private Card cardToThrow;

    public boolean readyToThrown = false;
    private boolean initialization = false;
    private int initNumber = 0;

    private int positionX = 0;
    public int[] inHandPosition = new int[11];

    private Random generator = new Random();

    private int size = 0;

    private Player owner;

    boolean[] check = new boolean[16];


    public CardInHand(int handPositionX, int handPositionY, Pane table) {

        table.getChildren().add(hand);

        hand.setLayoutX(handPositionX);
        hand.setLayoutY(handPositionY);
        hand.setPrefSize(100, 20);


        for (int i = 0; i < 11; i++) {

            inHandPosition[i] = 0;
        }

        fillFalseCheck();
    }

    public void chooseCardFromHand() {

        for (Card cardFromHand : cardsInHand) {

            boolean chosen = cardFromHand.getChosen();

            if (chosen) {

                cardToThrow = cardFromHand;

                readyToThrown = true;
            }
        }

        cardsInHand.remove(cardToThrow);
    }

    public Card getCardFromHand() {

        return cardToThrow;
    }

    public Card getCardFromEnemyHand() {

        fillFalseCheck();

        //jeśli gracz rzucił meduzę to usuwa tymczasowo karty magiczne
        if (owner.medusa) {

            for (Card card : cardsInHand) {

                if (card.getType() == Card.cardType.Magic) {
                    cubby.add(card);
                }
            }

            for (Card card : cubby) {

                cardsInHand.remove(card);
            }
        }

        //na wypadek hydry jeśli enemy ma mało życia
        if(owner.hydraHead && (owner.hp + owner.sp <= 10 || (owner.hp + owner.sp <= 15 && numberOfEffects(owner) >= 2))) {

            for (Card card : cardsInHand) {

                if (card.getDamage() > 0) {
                    cubby.add(card);
                }
            }

            for (Card card : cubby) {

                cardsInHand.remove(card);
            }
        }

        //dobicie charonem
        if (owner.myEnemy.hp <= 20 && owner.freezing <= 50) {

            if (searchCard(29) != -1) {
                return supDraw(searchCard(29)); //charon
            }
        }

        //dobicie apetytem
        if (owner.myEnemy.hp <= 10 && owner.myEnemy.sp > 0 && owner.freezing <= 50) {

            if (searchCard(59) != -1) {
                return supDraw(searchCard(59)); //apetyt
            }
        }

        //dobicie pogromcą
        if (owner.myEnemy.hp + owner.myEnemy.sp <= 50 && owner.freezing <= 50) {

            if (searchCard(65) != -1) {
                return supDraw(searchCard(65)); //pogromca
            }
        }

        //pikowanie po szybowaniu
        if(owner.cardsOntable.size() > 0) {
            if (owner.cardsOntable.get(owner.cardsOntable.size() - 1).getId() == 62 && owner.myEnemy.immuneTime == 0 && owner.freezing < 100) {

                if (searchCard(52) != -1) {
                    return supDraw(searchCard(52)); //pikowanie wysoki prior
                }
            }
        }

        if (owner.hp < 20 && owner.freezing <= 50) {

            if (searchCard(55) != -1) {
                return supDraw(searchCard(55)); //soter
            }
        }

        //dobicie niepewne
        if (owner.myEnemy.hp + owner.myEnemy.sp <= 15 && owner.freezing <= 50 && owner.immuneTime == 0) {

            for (Card card : cardsInHand) {

                if (card.getDamage() >= 10) {
                    return supDraw(cardsInHand.indexOf(card));
                }
                if (card.getDamage() >= 5 && (owner.myEnemy.burnTime > 0 || owner.myEnemy.poisonTime > 0 || owner.myEnemy.bleedingTime > 0)) {
                    return supDraw(cardsInHand.indexOf(card));
                }

            }
        }
        if ((owner.myEnemy.hp + owner.myEnemy.sp <= 30 || cardsInHand.size() < 3) && owner.freezing <= 50 && owner.immuneTime == 0) {

            for (Card card : cardsInHand) {

                if (card.getDamage() >= 20) {
                    return supDraw(cardsInHand.indexOf(card));
                }
                if (card.getDamage() >= 15 && (owner.myEnemy.poisonTime > 0 || owner.myEnemy.burnTime > 0 || owner.myEnemy.bleedingTime > 0)) {
                    return supDraw(cardsInHand.indexOf(card));
                }
                if (card.getDamage() >= 10 && owner.myEnemy.poisonTime > 0 && (owner.myEnemy.burnTime > 0 || owner.myEnemy.bleedingTime > 0)) {
                    return supDraw(cardsInHand.indexOf(card));
                }
                if (card.getDamage() >= 5 && owner.myEnemy.burnTime > 0 && owner.myEnemy.poisonTime > 0 && owner.myEnemy.bleedingTime > 0) {
                    return supDraw(cardsInHand.indexOf(card));
                }
            }
        }

        //karty ratunku
        if ((owner.hp <= 25 || cardsInHand.size() < 3) && owner.freezing <= 50) {

            Card drawCard = getHighRescueCard();
            if (drawCard != null) {
                return drawCard;
            }
        }

        //jeśli gracz ma tylko jedną kartę to jaskółka bez względu na zamrożenie
        if (owner.myEnemy.cardInHand.getSize() == 1) {

            if (searchCard(43) != -1) {
                return supDraw(searchCard(43)); //jaskółka
            }
        }

        if(owner.immuneTime > 0 || owner.effectsImmuneTime > 0 || owner.freezing >= 50){

            if (searchCard(42) != -1) {
                return supDraw(searchCard(42)); //cierpienie większy prior
            }
        }

        //na wypadek hydry jeśli enemy ma trochę więcej życia
        if(owner.hydraHead) {

            for (Card card : cardsInHand) {

                if (card.getDamage() > 0) {
                    cubby.add(card);
                }
            }

            for (Card card : cubby) {

                cardsInHand.remove(card);
            }
        }

        //jeśli enemy ma sznase na zamrożenie to rzuca karte value 1
        if (owner.freezing > 0) {

            for (Card card : cardsInHand) {

                if (card.getValue() == 1) {
                    return supDraw(cardsInHand.indexOf(card));
                }
            }

            if (searchCard(42) != -1) {
                return supDraw(searchCard(42)); //cierpienie mniejszy prior
            }

            if(owner.freezing == 100) {
                for (Card card : cardsInHand) {

                    if (card.getValue() == 2) {
                        return supDraw(cardsInHand.indexOf(card));
                    }
                }
            }
        }

        //jeśli gracz rzucił ognistą zbroję to usuwa tymczasowo karty broni
        if (owner.flameArmor) {

            for (Card card : cardsInHand) {

                if (card.getType() == Card.cardType.Weapon) {
                    cubby.add(card);
                }
            }

            for (Card card : cubby) {

                cardsInHand.remove(card);
            }
        }

        //jak najszybciej rzucić
        if (searchCard(27) != -1) {
            return supDraw(searchCard(27)); // korona infinity
        }

        if(searchCard(52) != -1 && owner.myEnemy.sp > 0) {

            if (searchCard(62) != -1) {
                return supDraw(searchCard(62)); //szybowanie większy prior
            }
        }

        //jeśli gracz ma tarcze
        if (owner.myEnemy.sp >= 10) {

            if (searchCard(4) != -1) {
                return supDraw(searchCard(4)); //tsunami większy prior
            }

            //jak nie tamto to może to
            if (searchCard(33) != -1) {
                return supDraw(searchCard(33)); //oślepienie większy prior
            }
        }

        if (owner.myEnemy.immuneTime == 0 && owner.myEnemy.actualCardData && owner.myEnemy.card.getDamage() > 10) {
            if (searchCard(21) != -1) {
                return supDraw(searchCard(21)); //smocza krew większy prior
            }
        }

        //jeśli enemy ma podpalenie
        if (owner.myEnemy.burnTime > 0) {

            if (searchCard(43) != -1) {
                return supDraw(searchCard(43)); //jaskółka
            }
        }

        //jeśli enemy ma mniej hp niż gracz i gracz nie jest damage immune
        if (owner.hp < owner.myEnemy.hp && owner.myEnemy.immuneTime == 0) {

            if (searchCard(56) != -1) {
                return supDraw(searchCard(56)); //feniks
            }
        }

        if (owner.myEnemy.immuneTime == 0 && owner.bleedingTime > 0) {

            if (searchCard(38) != -1) {
                return supDraw(searchCard(38)); //puszka pandory większy prior
            }

        }

        // jeśli enemy ma co najmniej dwa nałożone efekty i gracz nie jest odporny na obrażenia
        if (owner.myEnemy.immuneTime == 0 && numberOfEffects(owner) > 1) {

            if (searchCard(6) != -1) {
                return supDraw(searchCard(6)); //trójząb wysoki prior
            }

        }

        //jeśli enemy ma co najmniej dwa efekty to czyści
        if (numberOfEffects(owner) > 1) {

            if (searchCard(41) != -1) {
                return supDraw(searchCard(41)); //slodkosc wyższy prior
            }

            if (searchCard(32) != -1) {
                return supDraw(searchCard(32)); //promienie słońca wyższy prior
            }

            if (searchCard(40) != -1) {
                return supDraw(searchCard(40)); //dar wyższy prior
            }
        }

        if (owner.myEnemy.burnTime > 5 && owner.myEnemy.immuneTime == 0) {
            if (searchCard(28) != -1) {
                return supDraw(searchCard(28)); //wypalenie większy prior
            }
        }

        //jeśli enemy ma rzucone co najmniej trzy złote karty i maksymalnie dwie value 1 to joker
        int v1 = 0;
        int v3 = 0;
        for (Card card : owner.cardsOntable) {

            if (card.getValue() == 3) {
                v3++;
            }
            if (card.getValue() == 1) {
                v1++;
            }
        }
        if (v3 > 2 && v1 < 3) {

            if (searchCard(23) != -1) {
                return supDraw(searchCard(23)); //joker wiekszy prior
            }
        }

        //jeśli enemy ma podpalenie a gracz nie jest odporny na damage
        if (owner.myEnemy.immuneTime == 0 && owner.burnTime > 0) {

            if (searchCard(18) != -1) {
                return supDraw(searchCard(18)); //smokobójca większy prior
            }
        }

        //jeśli enemy ma poison
        if (owner.poisonTime > 0) {

            if (searchCard(53) != -1) {
                return supDraw(searchCard(53)); //ambrozja
            }
        }

        //jeśli enemy ma poison
        if (owner.poisonTime > 0) {

            if (searchCard(64) != -1) {
                return supDraw(searchCard(64)); //poison odpo
            }
        }

        if (cardsInHand.size() > 6 && owner.myEnemy.immuneTime == 0) {
            if (searchCard(30) != -1) {
                return supDraw(searchCard(30)); //pora ż&ś
            }
        }

        //jeśli gracz nie jest na nic odporny to enemy rzuci kartę z efektami i atakiem
        if (owner.myEnemy.immuneTime == 0 && owner.myEnemy.effectsImmuneTime == 0) {

            if(owner.cardsOntable.size() > 9) {

                if (searchCard(44) != -1) {
                    return supDraw(searchCard(44)); //pandemonium większy prior
                }
            }

            Card drawCard = getCardIfAbsoluteImmune();
            if (drawCard != null) {
                return drawCard;
            }
        }

        //jeśli enemy odporny na damage a gracz nie
        if (owner.myEnemy.immuneTime == 0 && owner.immuneTime > 0) {

            if (searchCard(11) != -1) {
                return supDraw(searchCard(11)); //topór większy prior
            }
        }

        if (owner.myEnemy.immuneTime == 0 && owner.myEnemy.actualCardData && owner.myEnemy.card.getDamage() > 0) {
            if (searchCard(21) != -1) {
                return supDraw(searchCard(21)); //smocza krew średni prior
            }
        }

        //jeśli gracz nie jest odporny na efekty i obrażenia to karta która ma efekt
        if (owner.myEnemy.effectsImmuneTime == 0 && owner.myEnemy.immuneTime == 0) {

            Card drawCard = getCardIfPlayerNotEffectsImmune();
            if (drawCard != null) {
                return drawCard;
            }
        }

        if (searchCard(45) != -1) {

            if (searchCard(13) != -1) {
                return supDraw(searchCard(13)); //strzała jeśli mamy w ręku krew hydry
            }
        }

        // jeśli gracz nie jest odporny na obrażenia to enemy zada obrażenia
        if (owner.myEnemy.immuneTime == 0) {

            Card drawCard = getCardIfPlayerNotDamageImmune();
            if (drawCard != null) {
                return drawCard;
            }
        }

        //jeśli gracz się pali i nie jest odporny na damage
        if (owner.myEnemy.burnTime > 0 && owner.myEnemy.immuneTime == 0) {
            if (searchCard(28) != -1) {
                return supDraw(searchCard(28)); //wypalenie mniejszy prior
            }
        }

        //karty bez specjalnych warunków
        Card drawCardd = getCardWithNoSpecialCondition();
        if (drawCardd != null) {
            return drawCardd;
        }

        //jeśli gracz ma tarcze
        if (owner.myEnemy.sp > 0) {

            if (searchCard(4) != -1) {
                return supDraw(searchCard(4)); //tsunami mniejszy prior
            }

            //jak nie tamto to może to
            if (searchCard(33) != -1) {
                return supDraw(searchCard(33)); //oślepienie mniejszy prior
            }
        }

        //jeśli enemy ma rzucone co najmniej trzy złote karty to joker
        int j = 0;
        for (Card card : owner.cardsOntable) {

            if (card.getValue() == 3) {
                j++;
            }
        }
        if (j > 2) {

            if (searchCard(23) != -1) {
                return supDraw(searchCard(23)); //joker mniejszy prior
            }
        }

        // jeśli enemy ma nałożone efekty i gracz nie jest odporny na obrażenia
        if (owner.myEnemy.immuneTime == 0 && numberOfEffects(owner) > 0) {


            if (searchCard(6) != -1) {
                return supDraw(searchCard(6)); //trójząb mniejszy prior
            }

        }

        if (numberOfEffects(owner) > 0) {

            if (searchCard(41) != -1) {
                return supDraw(searchCard(41)); //slodkosc mniejszy prior
            }

            if (searchCard(32) != -1) {
                return supDraw(searchCard(32)); //promienie słońca mniejszy prior
            }
        }

        if(owner.myEnemy.immuneTime == 0 ) {
            for (Card card : owner.cardsOntable) {
                if (card.getId() == 13) {
                    if (searchCard(45) != -1) {
                        return supDraw(searchCard(45)); //krew hydry jeśli strzała rzucona i gracz nie jest immune
                    }
                }
            }
        }

        //niżej karty które coś tracą

        //jeśli gracz nie jest odporny na efekty a jest na obrażenia
        if (owner.myEnemy.effectsImmuneTime == 0) {

            Card drawCard = getCardIfPlayerNotEffectsImmune();
            if (drawCard != null) {
                return drawCard;
            }
        }

        if(searchCard(52) != -1) {

            if (searchCard(62) != -1) {
                return supDraw(searchCard(62)); //szybowanie mniejszy prior
            }
        }

        // jeśli gracz nie jest odporny na obrażenia to enemy zada obrażenia kartami które mają dodatkowo inne efekty których nie mogą użyć
        if (owner.myEnemy.immuneTime == 0) {

            Card drawCard = getCardIfPlayerNotDamageImmuneSmallPrior();
            if (drawCard != null) {
                return drawCard;
            }

            if(owner.cardsOntable.size() > 12) {

                if (searchCard(44) != -1) {
                    return supDraw(searchCard(44)); //pandemonium mneijszy prior
                }
            }
        }

        if(cardsInHand.size() == 0) {
            returnToHandFromCubby(); //jeśli nie ma żadnej dostępnej karty, karty ze schowka mogą być wylosowane
        }

        //jeśli żaden powyższy warunek nie spełniony to pierwsza karta  z value 1, jak nie ma to z value2, a jak nie to coć innego
        System.out.println("tu");

        for (Card card : cardsInHand) {

            if (card.getValue() == 1) {
                return supDraw(cardsInHand.indexOf(card));
            }
        }
        for (Card card : cardsInHand) {

            if (card.getValue() == 2) {
                return supDraw(cardsInHand.indexOf(card));
            }
        }

        int randomNum = generator.nextInt(cardsInHand.size());
        return supDraw(randomNum);
    }

    private Card getCardIfAbsoluteImmune() {

        int l = 0;

        for (int i = 0; i < 7; i++) {
            if (check[i]) {
                l++;
            }
        }

        if (l == 7) {
            fillFalseCheck();
            return null;
        }

        int randNumber = generator.nextInt(7);

        switch (randNumber) {
            case 0 -> {
                if (searchCard(31) != -1) {
                    return supDraw(searchCard(31)); //iskry większy prior
                } else {
                    check[0] = true;
                    return getCardIfAbsoluteImmune();
                }
            }
            case 1 -> {
                if (searchCard(1) != -1) {
                    return supDraw(searchCard(1)); //kula ognia większy prior
                } else {
                    check[1] = true;
                    return getCardIfAbsoluteImmune();
                }
            }
            case 2 -> {
                if (searchCard(17) != -1) {
                    return supDraw(searchCard(17)); //buława większy prior
                } else {
                    check[2] = true;
                    return getCardIfAbsoluteImmune();
                }
            }
            case 3 -> {
                if (searchCard(22) != -1) {
                    return supDraw(searchCard(22)); //tulipan większy prior
                } else {
                    check[3] = true;
                    return getCardIfAbsoluteImmune();
                }
            }
            case 4 -> {
                if (searchCard(37) != -1) {
                    return supDraw(searchCard(37)); //pochodnia większy prior
                } else {
                    check[4] = true;
                    return getCardIfAbsoluteImmune();
                }
            }
            case 5 -> {
                if (searchCard(51) != -1) {
                    return supDraw(searchCard(51)); //wąż większy prior
                } else {
                    check[5] = true;
                    return getCardIfAbsoluteImmune();
                }
            }
            case 6 -> {
                if (searchCard(60) != -1) {
                    return supDraw(searchCard(60)); //szpony większy prior
                } else {
                    check[6] = true;
                    return getCardIfAbsoluteImmune();
                }
            }


        }

        return null;
    }

    private Card getCardIfPlayerNotEffectsImmune() {

        int l = 0;

        for (int i = 0; i < 4; i++) {
            if (check[i]) {
                l++;
            }
        }

        if (l == 4) {
            fillFalseCheck();
            return null;
        }

        int randNumber = generator.nextInt(4);

        switch (randNumber) {
            case 0 -> {
                if (searchCard(20) != -1) {
                    return supDraw(searchCard(20)); //fiolka trucizny
                } else {
                    check[0] = true;
                    return getCardIfPlayerNotEffectsImmune();
                }
            }
            case 1 -> {
                if (searchCard(2) != -1) {
                    return supDraw(searchCard(2)); //ognista zbroja większy prior
                } else {
                    check[1] = true;
                    return getCardIfPlayerNotEffectsImmune();
                }
            }
            case 2 -> {
                if (searchCard(25) != -1) {
                    return supDraw(searchCard(25)); //cerber
                } else {
                    check[2] = true;
                    return getCardIfPlayerNotEffectsImmune();
                }
            }
            case 3 -> {
                if (searchCard(48) != -1) {
                    return supDraw(searchCard(48)); //tyfon
                } else {
                    check[3] = true;
                    return getCardIfPlayerNotEffectsImmune();
                }
            }


        }

        return null;
    }

    private Card getCardIfPlayerNotDamageImmune() {

        int l = 0;

        for (int i = 0; i < 16; i++) {
            if (check[i]) {
                l++;
            }
        }

        if (l == 16) {
            fillFalseCheck();
            return null;
        }

        int randNumber = generator.nextInt(16);

        switch (randNumber) {
            case 0 -> {
                if (searchCard(3) != -1) {
                    return supDraw(searchCard(3)); //stal hefcia
                } else {
                    check[0] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 1 -> {
                if (searchCard(7) != -1) {
                    return supDraw(searchCard(7)); //lodowy pocisk
                } else {
                    check[1] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 2 -> {
                if (searchCard(8) != -1) {
                    return supDraw(searchCard(8)); //lodowe serce
                } else {
                    check[2] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 3 -> {
                if (searchCard(10) != -1) {
                    return supDraw(searchCard(10)); //miecz
                } else {
                    check[3] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 4 -> {
                if (searchCard(11) != -1) {
                    return supDraw(searchCard(11)); //topór mniejszy priorytet
                } else {
                    check[4] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 5 -> {
                if (searchCard(12) != -1) {
                    return supDraw(searchCard(12)); //łuk
                } else {
                    check[5] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 6 -> {
                if (searchCard(13) != -1) {
                    return supDraw(searchCard(13)); //strzała
                } else {
                    check[6] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 7 -> {
                if (searchCard(16) != -1) {
                    return supDraw(searchCard(16)); //maczuga herkulesa
                } else {
                    check[7] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 8 -> {
                if (searchCard(58) != -1) {
                    return supDraw(searchCard(58)); //gniew boga
                } else {
                    check[8] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 9 -> {
                if (searchCard(34) != -1) {
                    return supDraw(searchCard(34)); //miecz słońca
                } else {
                    check[9] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 10 -> {
                if (searchCard(36) != -1) {
                    return supDraw(searchCard(36)); //rydwan
                } else {
                    check[10] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 11 -> {
                if (searchCard(47) != -1) {
                    return supDraw(searchCard(47)); //hydra
                } else {
                    check[11] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 12 -> {
                if (searchCard(15) != -1) {
                    return supDraw(searchCard(15)); //piorun
                } else {
                    check[12] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 13 -> {
                if (searchCard(57) != -1) {
                    return supDraw(searchCard(57)); //lord god
                } else {
                    check[13] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 14 -> {
                if (searchCard(59) != -1) {
                    return supDraw(searchCard(59)); //apetyt
                } else {
                    check[14] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
            case 15 -> {
                if (searchCard(65) != -1) {
                    return supDraw(searchCard(65)); //pogromca
                } else {
                    check[15] = true;
                    return getCardIfPlayerNotDamageImmune();
                }
            }
        }

        return null;
    }

    private Card getCardIfPlayerNotDamageImmuneSmallPrior() {

        int l = 0;

        for (int i = 0; i < 11; i++) {
            if (check[i]) {
                l++;
            }
        }

        if (l == 11) {
            fillFalseCheck();
            return null;
        }

        int randNumber = generator.nextInt(11);

        switch (randNumber) {
            case 0 -> {
                if (searchCard(17) != -1) {
                    return supDraw(searchCard(17)); //buława mniejszy priorytet
                } else {
                    check[0] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 1 -> {
                if (searchCard(1) != -1) {
                    return supDraw(searchCard(1)); //kula ognia mniejszy priorytet
                } else {
                    check[1] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 2 -> {
                if (searchCard(31) != -1) {
                    return supDraw(searchCard(31)); //iskry mniejszy priorytet
                } else {
                    check[2] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 3 -> {
                if (searchCard(22) != -1) {
                    return supDraw(searchCard(22)); //tulipan mniejszy prior
                } else {
                    check[3] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 4 -> {
                if (searchCard(18) != -1) {
                    return supDraw(searchCard(18)); //smokobójca mniejszy priorytet
                } else {
                    check[4] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 5 -> {
                if (searchCard(37) != -1) {
                    return supDraw(searchCard(37)); //pochodnia mniejszy priorytet
                } else {
                    check[5] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 6 -> {
                if (searchCard(38) != -1) {
                    return supDraw(searchCard(38)); //puszka pandy mniejszy priorytet
                } else {
                    check[6] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 7 -> {
                if (searchCard(51) != -1) {
                    return supDraw(searchCard(51)); //wąż mniejszy priorytet
                } else {
                    check[7] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 8 -> {
                if (searchCard(60) != -1) {
                    return supDraw(searchCard(60)); //szpony mniejszy priorytet
                } else {
                    check[8] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 9 -> {
                if (searchCard(52) != -1) {
                    return supDraw(searchCard(52)); //pikowanie mniejszy priorytet
                } else {
                    check[9] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }
            case 10 -> {
                if (searchCard(6) != -1) {
                    return supDraw(searchCard(6)); //trójząb najmniejszy priorytet
                } else {
                    check[10] = true;
                    return getCardIfPlayerNotDamageImmuneSmallPrior();
                }
            }

        }

        return null;
    }

    private Card getHighRescueCard() {

        if (searchCard(50) != -1) {
            return supDraw(searchCard(50)); //złote runo damage immune 2 tury
        }

        if (searchCard(35) != -1) {
            return supDraw(searchCard(35)); //słońce w butelce 15hp + 15sp
        }

        if (searchCard(49) != -1) {
            return supDraw(searchCard(49)); //zioło 15hp + 15sp
        }

        if (searchCard(41) != -1) {
            return supDraw(searchCard(41)); //slodkosc 20hp + oczyszczenie
        }

        if (searchCard(53) != -1) {
            return supDraw(searchCard(53)); //ambrozja 20hp + oczyszczenie poison
        }

        if (searchCard(26) != -1) {
            return supDraw(searchCard(26)); //nić Ariadny 20hp
        }

        if (searchCard(61) != -1) {
            return supDraw(searchCard(61)); //deszcz piór 15sp + zamro 100
        }

        if (searchCard(57) != -1) {
            return supDraw(searchCard(57)); //lord god może da 30hp lub 30sp
        }

        if (searchCard(19) != -1) {
            return supDraw(searchCard(19)); //fiolka życia 15hp
        }

        if (searchCard(21) != -1) {
            return supDraw(searchCard(21)); //smocza krew 25sp
        }

        if (searchCard(39) != -1) {
            return supDraw(searchCard(39)); //obłuda 1 tura absoluteImmune
        }

        if (searchCard(40) != -1) {
            return supDraw(searchCard(40)); //może da słodkość
        }

        if (searchCard(2) != -1) {
            return supDraw(searchCard(2)); //ognista zbroja 15sp
        }

        if (searchCard(59) != -1) {
            return supDraw(searchCard(59)); //apetyt 10hp + damage
        }

        if (searchCard(3) != -1) {
            return supDraw(searchCard(3)); //stal hefcia 10sp + damage
        }

        if (searchCard(31) != -1) {
            return supDraw(searchCard(31)); //iskry 10hp + damage
        }

        if (searchCard(64) != -1) {
            return supDraw(searchCard(64)); //pois odpo 10hp + pois oczyszcza
        }

        if (searchCard(54) != -1) {
            return supDraw(searchCard(54)); //egida 10sp + efectImmune 3 tury
        }

        if (searchCard(14) != -1) {
            return supDraw(searchCard(14)); //tarcza 10sp
        }

        if (searchCard(9) != -1) {
            return supDraw(searchCard(9)); //atrybut chione zamro 80
        }


        return null;
    }

    private Card getCardWithNoSpecialCondition() {

        int l = 0;

        for (int i = 0; i < 15; i++) {
            if (check[i]) {
                l++;
            }
        }

        if (l == 15) {
            fillFalseCheck();
            return null;
        }

        int randNumber = generator.nextInt(15);

        switch (randNumber) {
            case 0 -> {
                if (searchCard(5) != -1) {
                    return supDraw(searchCard(5)); //mgła
                } else {
                    check[0] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 1 -> {
                if (searchCard(14) != -1) {
                    return supDraw(searchCard(14)); //tarcza
                } else {
                    check[1] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 2 -> {
                if (searchCard(63) != -1) {
                    return supDraw(searchCard(63)); //tarcza Persa
                } else {
                    check[2] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 3 -> {
                if (searchCard(2) != -1) {
                    return supDraw(searchCard(2)); //ognista zbroja mniejszy prior
                } else {
                    check[3] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 4 -> {
                if (searchCard(24) != -1) {
                    return supDraw(searchCard(24)); //meduza
                } else {
                    check[4] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 5 -> {
                if (searchCard(9) != -1) {
                    return supDraw(searchCard(9)); //atrybut chione
                } else {
                    check[5] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 6 -> {
                if (searchCard(19) != -1) {
                    return supDraw(searchCard(19)); //fiolka życia
                } else {
                    check[6] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 7 -> {
                if (searchCard(26) != -1) {
                    return supDraw(searchCard(26)); //nić Ariadny
                } else {
                    check[7] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 8 -> {
                if (searchCard(35) != -1) {
                    return supDraw(searchCard(35)); //słońce w butelce
                } else {
                    check[8] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 9 -> {
                if (searchCard(39) != -1) {
                    return supDraw(searchCard(39)); //obłuda
                } else {
                    check[9] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 10 -> {
                if (searchCard(40) != -1) {
                    return supDraw(searchCard(40)); //dar
                } else {
                    check[10] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 11 -> {
                if (searchCard(49) != -1) {
                    return supDraw(searchCard(49)); //zioło
                } else {
                    check[11] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 12 -> {
                if (searchCard(50) != -1) {
                    return supDraw(searchCard(50)); //złote runo
                } else {
                    check[12] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 13 -> {
                if (searchCard(54) != -1) {
                    return supDraw(searchCard(54)); //egida
                } else {
                    check[13] = true;
                    return getCardWithNoSpecialCondition();
                }
            }
            case 14 -> {
                if (searchCard(61) != -1) {
                    return supDraw(searchCard(61)); //deszcz piór
                } else {
                    check[14] = true;
                    return getCardWithNoSpecialCondition();
                }
            }

        }
        return null;
    }

    private int searchCard(int id) {

        for (Card card : cardsInHand) {

            if (card.getId() == id) {
                return cardsInHand.indexOf(card);
            }
        }

        return -1;
    }

    private Card supDraw(int id) {

        Card drawCard = cardsInHand.get(id);
        cardsInHand.remove(drawCard);
        return drawCard;
    }

    public void drawCardsFromSmallDeck(SmallDeckOfCards smallDeck) {

        Card drawCard = smallDeck.getCardFromSmallDeck();
        //   cardsInHand.add(drawCard);
        drawAnimation(drawCard);

        smallDeck.setSize();

        if (smallDeck.getSize() > 0) {

            drawCard = smallDeck.getCardFromSmallDeck();
            // cardsInHand.add(drawCard);
            drawAnimation(drawCard);
        }
    }

    public void drawArrowCards() {

        Card drawCard = AllCard.summonCard.get(0);

        Card newCard1 = new Card(drawCard.getName(), drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        //cardsInHand.add(newCard1);
        drawAnimation(newCard1);

        Card newCard = new Card(drawCard.getName(), drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        // cardsInHand.add(newCard);
        drawAnimation(newCard);
    }

    public void drawSweetness() {

        Card drawCard = AllCard.summonCard.get(1);

        Card newCard = new Card(drawCard.getName(), drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        // cardsInHand.add(newCard);
        drawAnimation(newCard);
    }

    public void drawPain() {

        Card drawCard = AllCard.summonCard.get(2);

        Card newCard = new Card(drawCard.getName(), drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        // cardsInHand.add(newCard);
        drawAnimation(newCard);
    }

    public void drawRandCard(Card drawCard) {

        Card newCard = new Card(drawCard.getName(), drawCard.getId(), drawCard.getValue(), drawCard.getDamage(), drawCard.getArmor(),
                drawCard.getType(), drawCard.getImageName(), drawCard.getHpIncrease());

        // cardsInHand.add(newCard);
        drawAnimation(newCard);
    }

    public void initHand(SmallDeckOfCards smallDeck) {

        initialization = true;

        for (int i = 0; i < 3; i++) {

            Card drawCard = smallDeck.getCardFromSmallDeck();
            //cardsInHand.add(drawCard);

            drawAnimation(drawCard);
        }
    }

    private void drawAnimation(Card drawCard) {

        drawCard.ownerHand = this;
        drawCard.imageView.toFront();

        moveCard();

        drawCard.positionOnTable = positionX / 90;
        inHandPosition[positionX / 90] = 1;

        hand.getChildren().add(drawCard.imageView);
        drawCard.imageView.fitWidthProperty().bind(hand.widthProperty());
        drawCard.imageView.setPreserveRatio(true);
        drawCard.imageView.setX(positionX);

        TranslateTransition t = new TranslateTransition(Duration.millis(1500), drawCard.imageView);
        t.setFromX(1000);
        // t.setFromY(0);
        t.setToX(positionX - drawCard.imageView.getX());

        t.play();

        t.setOnFinished((ActionEvent evt) -> {

            cardsInHand.add(drawCard);
            setNiceCards();

            if (initialization = true) {
                initNumber++;

                if (initNumber == 3) {
                    Game.ready = true;
                    initialization = false;
                }
            }
            t.stop();
        });
    }

    public boolean emptyHand() {
        if (cardsInHand.size() != 0) {
            return false;
        } else {
            return true;
        }
    }

    private void moveCard() {

        for (int i = 0; i < 11; i++) {

            if (inHandPosition[i] == 0) {

                positionX = 90 * i;
                break;
            }
        }
    }

    public int getSize() {

        return cardsInHand.size();
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void returnToHandFromCubby() {
        cardsInHand.addAll(cubby);
        cubby.clear();
    }

    private int numberOfEffects(Player player) {

        int k = 0;

        if (player.burnTime > 0) {
            k++;
        }
        if (player.bleedingTime > 0) {
            k++;
        }
        if (player.poisonTime > 0) {
            k++;
        }
        return k;
    }

    private void fillFalseCheck() {
        for (int i = 0; i < 16; i++) {
            check[i] = false;
        }
    }

    public void setNiceCards() {

        for (int i = 0; i < 11; i++) {
            for (Card card : cardsInHand) {
                if (card.positionOnTable == i) {
                    card.imageView.toFront();
                }
            }
        }
    }
}