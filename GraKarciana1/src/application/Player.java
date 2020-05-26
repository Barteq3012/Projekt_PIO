package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Player {

    public int hp;
    public int sp;
    public int numberOfCardsOnStack;
    public String name;
    private final int id;

    // Nadaje sobie przeciwnika; wskaznik do oponenta
    public Player myEnemy;

    public Card card;
    public CardInHand cardInHand;

    public boolean ready = false;

    public List<Card> cardsOntable = new ArrayList<>();

    public int positionX = 0;
    public int supPositionX = 60; // do obliczania pozycji po ściśnięciu kart

    // zmienne dotyczące efektów nakładanych przez karte i efektów czasowych
    public int burnTime = 0;   // nadanie czasu trwania podpalenia
    public int poisonTime = 0;  // nadanie czasu trwania zatrucia
    public int bleedingTime = 0;  // nadanie czasu trwania krawawienia
    public int freezing = 0; // zmienna warunkująca procent szans na wystąpienie efektu
    public int immuneTime = 0;
    public int effectsImmuneTime = 0;

    private Text burnTimeNumber = new Text();
    private Text poisonTimeNumber = new Text();
    private Text bleedingTimeNumber = new Text();
    ImageView burnImg;
    ImageView poisonImg;
    ImageView bleedImg;

    private  boolean isStartBurn = false;
    private  boolean isStartPoison = false;
    private  boolean isStartBleeding = false;
    private  boolean isDamaged = false;

    private int crown = 0; // zmiena stworzonana rzecz korony smierci
    public boolean flameArmor = false; // potrzebne do flame Armor
    public boolean medusa = false;
    public boolean hydraHead = false;

    public boolean actualCardData = false;

    private int randomNumber; // liczba posłużąca do określenia procentowych sznas
    public Random random = new Random();


    public Player(String name, int id, int hp, int sp, CardInHand cardInHand) {

        this.name = name;
        this.hp = hp;
        this.sp = sp;
        this.cardInHand = cardInHand;
        this.id = id;
    }

    public void setMyEnemy(Player player) {

        myEnemy = player;
    }

    public void throwCard() {

        checkMedusa();

        // losuje liczbe aby potem użyć do stwierdzenia czy efekt karty sie wywoła czy nie
        randomNumber = random.nextInt(100) + 1;

        if (randomNumber > freezing) {
            // wywołanie akcji karty
            card.action(this, myEnemy);
        }

        freezing = 0; // wyzerowanie zmiennej odpowiedzialenj za brak uzycia akcji karty, uda się czy nie uda zamrożenie to i tak zeruj

        checkFlameArmorEffect();
        checkHydraHead();

        actualCardData = true;
        myEnemy.actualCardData = false;
        isDamaged = false;
    }

    // wywołanie wszystkich efektów czasowych
    public void checkEffects() {

        if (crown > 0) {
            hp += 2 * crown;
        }

        if(immuneTime > 0) {
            immuneTime--;
        }

        if(effectsImmuneTime > 0) {
            effectsImmuneTime--;
        }

        getBurnDamage();
        getBleedingDamage();
        getPoisonDamage();
    }

    //metoda do animacji rzucania kart
    public void moveCard(int id) {

        card.imageView.toFront();

        TranslateTransition t = new TranslateTransition(Duration.millis(1000), card.imageView);

        t.setFromX(0);
        t.setFromY(0);
        t.setToX(positionX - card.imageView.getX());

        card.absolutePosition = positionX - card.imageView.getX(); //pozycja karty po rzuceniu

        // wsp. Y położenia kart rzuconych, 1 - gracz, 2 - wróg

        if (id == 1) {
            t.setToY(-150);
        } else if (id == 2) {
            t.setToY(250);
        }

        t.play();

        // po zakończeniu animacji

        t.setOnFinished(e -> {

            positioning();

            t.stop();
        });
    }

    private void positioning() {

        // przy 8 karty ścisnął się
        if (cardsOntable.size() == 8) {

            //pętla przesuwa wszytskie rzucone już karty z wyjątkiem pierwszej
            for (Card card : cardsOntable) {

                if (cardsOntable.indexOf(card) != 0) {

                    TranslateTransition tt = new TranslateTransition(Duration.millis(2000), card.imageView);

                    tt.setFromX(card.absolutePosition);
                    tt.setToX(card.absolutePosition - supPositionX);
                    tt.play();
                    supPositionX += 60;
                }
            }

            positionX = positionX + 110 - supPositionX; // nowa pozycja po zagęszceniu

        } else if (cardsOntable.size() > 8) {
            //po zagęszczeniu co ile układać
            positionX += 50;
        } else {
            //przed zagęszczeniem co ile układać
            positionX += 110;
        }
    }

    // metoda po kolei układa rzucone karty na wierzch
    public void setNiceCards() {

        for (Card card : cardsOntable) {

            card.imageView.toFront();
        }
    }

    // funkcja zadajaca obrazenia i odejmująca tarcze
    public void dealDamage(int damage) {

        if(damage != 0) {
            isDamaged = true;
        }

        if(immuneTime == 0) {

            if (sp > 0) {
                sp -= damage;
                if (sp < 0) {
                    hp += sp;
                    sp = 0;
                }
            } else {
                hp -= damage;
            }
        }
    }

    // funkcja dodajaca zdrowia
    public void increaseHp(int hpAmount) {
        hp += hpAmount;
    }

    //funkcja dodajaca tarczy
    public void increaseSp(int spAmount) {
        sp += spAmount;
    }

    public void addImmuneTime(int addImmune) {

        immuneTime += addImmune;
    }

    public void addEffectsImmuneTime(int addImmune) {

        effectsImmuneTime += addImmune;
    }

    //funkcja dodaj czas trwania podpalenia
    public void addBurnTime(int addBurn) {

        this.burnTime += addBurn;

        if(!isStartBurn) {

            isStartBurn = true;

            Image image = new Image(getClass().getResourceAsStream("/pictures/burnn.png"));
            burnImg = new ImageView(image);

            if(id == 1) {

                burnImg.setX(1180);
                burnImg.setY(660);
                burnTimeNumber.setX(1185);
                burnTimeNumber.setY(685);
            }
            else {

                burnImg.setX(15);
                burnImg.setY(280);
             //   burnTimeNumber.setX(20);
                burnTimeNumber.setY(305);
            }

            Game.table.getChildren().add(burnImg);
            burnImg.toBack();

            burnTimeNumber.setFill(Color.WHITE);
            burnTimeNumber.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
            Game.table.getChildren().add(burnTimeNumber);
        }

        burnTimeNumber.setText(String.valueOf(burnTime));

        if(id == 1) {
            burnTimeNumber.setX(1180 + 10 - burnTimeNumber.getLayoutBounds().getWidth() / 2);
        }
        else {
            burnTimeNumber.setX(15 + 10 - burnTimeNumber.getLayoutBounds().getWidth() / 2);
        }
    }

    // funkcja zadajaca obrazenia z podpalenia
    public void getBurnDamage() {
        if (burnTime > 0) {
            dealDamage(3);
            burnTime -= 1;

            burnTimeNumber.setText(String.valueOf(burnTime));

            if(id == 1) {
                burnTimeNumber.setX(1180 + 10 - burnTimeNumber.getLayoutBounds().getWidth() / 2);
            }
            else {
                burnTimeNumber.setX(15 + 10 - burnTimeNumber.getLayoutBounds().getWidth() / 2);
            }
        }

        if(burnTime == 0) {

            Game.table.getChildren().remove(burnTimeNumber);
            Game.table.getChildren().remove(burnImg);
            isStartBurn = false;
        }
    }

    //funkcja dodaj czas trwania zatrucia
    public void addPoisonTime(int poison) {
        this.poisonTime += poison;

        if(!isStartPoison) {

            isStartPoison = true;

            Image image = new Image(getClass().getResourceAsStream("/pictures/pois.png"));
            poisonImg = new ImageView(image);

            if(id == 1) {

                poisonImg.setX(1200);
                poisonImg.setY(660);
                poisonTimeNumber.setX(1210);
                poisonTimeNumber.setY(685);
            }
            else {

                poisonImg.setX(35);
                poisonImg.setY(280);
               // poisonTimeNumber.setX(45);
                poisonTimeNumber.setY(305);
            }

            Game.table.getChildren().add(poisonImg);
            poisonImg.toBack();

            poisonTimeNumber.setFill(Color.BLACK);
            poisonTimeNumber.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
            Game.table.getChildren().add(poisonTimeNumber);
        }

        poisonTimeNumber.setText(String.valueOf(poisonTime));

        if(id == 1) {
            poisonTimeNumber.setX(1200 + 15 - poisonTimeNumber.getLayoutBounds().getWidth() / 2);
        }
        else {
            poisonTimeNumber.setX(35 + 15 - poisonTimeNumber.getLayoutBounds().getWidth() / 2);
        }
    }

    // funkcja zadajaca obrazenia z zatrucia
    public void getPoisonDamage() {
        if (poisonTime > 0) {
            dealDamage(5);
            poisonTime -= 1;

            poisonTimeNumber.setText(String.valueOf(poisonTime));

            if(id == 1) {
                poisonTimeNumber.setX(1200 + 15 - poisonTimeNumber.getLayoutBounds().getWidth() / 2);
            }
            else {
                poisonTimeNumber.setX(35 + 15 - poisonTimeNumber.getLayoutBounds().getWidth() / 2);
            }
        }

        if(poisonTime == 0) {

            Game.table.getChildren().remove(poisonTimeNumber);
            Game.table.getChildren().remove(poisonImg);
            isStartPoison = false;
        }
    }

    //funkcja dodaje czas trwania kwawienia
    public void addBleedingTime(int bleeding) {
        this.bleedingTime += bleeding;

        if(!isStartBleeding) {

            isStartBleeding = true;

            Image image = new Image(getClass().getResourceAsStream("/pictures/blod.png"));
            bleedImg = new ImageView(image);

            if(id == 1) {

                bleedImg.setX(1230);
                bleedImg.setY(660);
                bleedingTimeNumber.setX(1235);
                bleedingTimeNumber.setY(685);
            }
            else {

                bleedImg.setX(65);
                bleedImg.setY(280);
             //   bleedingTimeNumber.setX(70);
                bleedingTimeNumber.setY(305);
            }

            Game.table.getChildren().add(bleedImg);
            bleedImg.toBack();

            bleedingTimeNumber.setFill(Color.WHITE);
            bleedingTimeNumber.setFont(Font.font("System", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
            Game.table.getChildren().add(bleedingTimeNumber);
        }

        bleedingTimeNumber.setText(String.valueOf(bleedingTime));

        if(id == 1) {
            bleedingTimeNumber.setX(1230 + 10 - bleedingTimeNumber.getLayoutBounds().getWidth() / 2);
        }
        else {
            bleedingTimeNumber.setX(65 + 10 - bleedingTimeNumber.getLayoutBounds().getWidth() / 2);
        }
    }

    // funkcja zadajaca obrazenia z kwawienia
    public void getBleedingDamage() {
        if (bleedingTime > 0) {
            dealDamage(2);
            bleedingTime -= 1;

            bleedingTimeNumber.setText(String.valueOf(bleedingTime));

            if(id == 1) {
                bleedingTimeNumber.setX(1230 + 10 - bleedingTimeNumber.getLayoutBounds().getWidth() / 2);
            }
            else {
                bleedingTimeNumber.setX(65 + 10 - bleedingTimeNumber.getLayoutBounds().getWidth() / 2);
            }
        }

        if(bleedingTime == 0) {

            Game.table.getChildren().remove(bleedingTimeNumber);
            Game.table.getChildren().remove(bleedImg);
            isStartBleeding = false;
        }
    }

    // funkcja pobierajaca dane o procencie szans na wystapienie zamrozenia
    public void setFreezing(int freez) {
        freezing = freez;
    }

    public void flameArmorUsed(boolean flameArmor){
        this.flameArmor = flameArmor;
    }

    private void checkFlameArmorEffect(){

        if(card.getCardType() == Card.cardType.Weapon && flameArmor){
            addBurnTime(6);
        }

        flameArmor = false;
    }

    private void checkHydraHead(){

        if(hydraHead && myEnemy.isDamaged){

            Card hydraHead = AllCard.getCard(43);

            myEnemy.cardsOntable.add(hydraHead);

            hydraHead.owner = myEnemy;
            hydraHead.onTable = true;

            myEnemy.cardInHand.hand.getChildren().add(hydraHead.imageView);
            hydraHead.imageView.fitWidthProperty().bind(myEnemy.cardInHand.hand.widthProperty());
            hydraHead.imageView.setPreserveRatio(true);
            hydraHead.imageView.setX(myEnemy.positionX);

            if (myEnemy.id == 1) {
                hydraHead.imageView.setY(-150);
            } else {
                hydraHead.imageView.setY(250);
            }

            hydraHead.action(myEnemy, this);

            myEnemy.positioning();
        }

        hydraHead = false;
    }

    /**
     * Zdejmuje podane efekty w przypadku gdy dana zmienna jest "true"
     *
     * @param poison jeśli prawda zdejmuje zatrucie
     * @param bleed  jeśli prawda zdejmuje krwawienie
     * @param burn   jeśli prawda zdejmuje podpalenie
     * @param shield jeśli prawda dejmuje całą tarczę
     */

    public void purification(boolean poison, boolean bleed, boolean burn, boolean shield) {

        if (poison) {
            poisonTime = 0;

            Game.table.getChildren().remove(poisonTimeNumber);
            Game.table.getChildren().remove(poisonImg);
            isStartPoison = false;
        }

        if (bleed) {
            bleedingTime = 0;

            Game.table.getChildren().remove(bleedingTimeNumber);
            Game.table.getChildren().remove(bleedImg);
            isStartBleeding = false;
        }

        if (burn) {
            burnTime = 0;

            Game.table.getChildren().remove(burnTimeNumber);
            Game.table.getChildren().remove(burnImg);
            isStartBurn = false;
        }

        if (shield) {
            sp = 0;
        }
    }

    // funkcja pozwala uruchomic funkcje dodajaca hp co ture
    public void healByTurn() {
        crown++;
    }

    // metoda do wypalenia
    public void letBurnout() {

        dealDamage(burnTime * 2);
    }

    //metoda zadająca obrażenia takie jak poprzednio zadał przeciwnik stworzona na rzecz dragon blood
    public void riposte() {

        if(myEnemy.actualCardData) {

            myEnemy.dealDamage(myEnemy.card.getDamage());
        }
    }

    // funkcja potrzebna do karty charon
    public void charon(){

        if(hp <= 20){
            hp = 0;
        }
    }

    private void checkMedusa() {

        if(medusa && card.getType() == Card.cardType.Magic) {

            freezing = 100;
        }

        medusa = false;
    }

    public void takeCard() {
        numberOfCardsOnStack--;
    }
}

/*   Stare pomysły

    player.saveHp(player);

    private int lostHp;  // zmienna stworzona na rzecz karty dragon blood

    public void saveHp(Player player) {
        lostHp = hp;
    }

    public void riposte(Player enemy) {
        enemy.hp -= lostHp;
        lostHp = 0;
    }

    //funkcja stworzona na potrzeby dragon blood; słuzy do przeprowadzenia kontrataku na przeciwniku za poprzednia ture,
        nie uwzgledznia tarczy

        if (lostHp > hp) {

            lostHp = lostHp - hp;
            riposte(myEnemy);
        }

        public void burnout(boolean burnout){
        this.burnout = true;
    }

    // metoda potrzebna do wypalenia


    public void letBurnout(Player player){
        if(burnout){
            hp -= (burnTime*2);
            burnout = false;
        }
    }


 */