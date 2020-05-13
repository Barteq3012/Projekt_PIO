package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class Player {

    public int hp;
    public int sp;
    public int numberOfCardsOnStack;
    public String name;

    // Nadaje sobie przeciwnika; wskaznik do oponenta
    private Player myEnemy;

    public Card card;
    public CardInHand cardInHand;

    public boolean ready = false;

    public List<Card> cardsOntable = new ArrayList<>();

    public int positionX = 0;
    public int supPositionX = 60; // do obliczania pozycji po ściśnięciu kart

    // zmienne dotyczące efektów nakładanych przez karte i efektów czasowych
    private int burnTime;   // nadanie czasu trwania podpalenia
    private int poisonTime;  // nadanie czasu trwania zatrucia
    private int bleedingTime;  // nadanie czasu trwania krawawienia
    private int freezing; // zmienna warunkująca procent szans na wystąpienie efektu
    private boolean crownReady; // zmiena stworzonana rzecz korony smierci
    private boolean burnout; // zmienna potrzeba do wypalenia
    private boolean flameArmorUsed; // potrzebne do flame Armor

    private int lostHp;  // zmienna stworzona na rzecz karty dragon blood
    private int randomNumber; // liczba posłużąca do określenia procentowych sznas

    public Random random = new Random();


    public Player(String name, int hp, int sp, CardInHand cardInHand) {

        this.name = name;
        this.hp = hp;
        this.sp = sp;
        this.cardInHand = cardInHand;
    }

    public void setMyEnemy(Player player) {

        myEnemy = player;
    }

    public void throwCard() {
        //funkcja stworzona na potrzeby dragon blood; słuzy do przeprowadzenia kontrataku na przeciwniku za poprzednia ture, nie uwzgledznia tarczy
        if (lostHp > hp) {
            lostHp = lostHp - hp;
            riposte(myEnemy);
        }
        if (crownReady) {
            hp += 2;
        }
        letBurnout(myEnemy);

        // losuje liczbe aby potem użyć do stwierdzenia czy efekt karty sie wywoła czy nie
        randomNumber = random.nextInt(101);

        if (randomNumber < freezing) {
            freezing = 0;  // wyzerowanie zmiennej odpowiedzialenj za brak uzycia akcji karty
        } else {
            // wywołanie akcji karty
            card.action(this, myEnemy);
        }

        flameArmorEffect(card);

        // wywołanie wszystkich efektów czasowych
        getBleedingDamage();
        getBurnDamage();
        getPoisonDamage();
    }

    //metoda do animacji rzucania kart
    public void moveCard(int id) {

        TranslateTransition t = new TranslateTransition(Duration.millis(1000), card.imageView);

        card.imageView.toFront();

        t.setFromX(0);
        t.setFromY(0);
        t.setToX(positionX - card.imageView.getX());

        card.absolutePosition = positionX - card.imageView.getX(); //pozycja karty po rzuceniu

        // wsp. Y położenia kart rzuconych, 1 - gracz, 2 - wróg

        if (id == 1) {
            t.setToY(-160);
        } else if (id == 2) {
            t.setToY(250);
        }

        t.play();

        // po zakończeniu animacji

        t.setOnFinished(e -> {

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
                positionX += 70;
            } else {
                //przed zagęszczeniem co ile układać
                positionX += 110;
            }

            t.stop();
        });
    }

    // metoda po kolei układa rzucone karty na wierzch
    public void setNiceCards() {

        for (Card card : cardsOntable) {

            card.imageView.toFront();
        }
    }

    // funkcja zadajaca obrazenia i odejmująca tarcze
    public void dealDamage(int damage) {
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

    // funkcja dodajaca zdrowia
    public void increaseHp(int hpAmount) {
        hp += hpAmount;
    }

    //funkcja dodajaca tarczy
    public void increaseSp(int spAmount) {
        sp += spAmount;
    }

    //funkcja dodaj czas trwania podpalenia
    public void AddBurnTime(int burn) {
        this.burnTime += burn;
    }

    // funkcja zadajaca obrazenia z podpalenia
    public void getBurnDamage() {
        if (burnTime > 0) {
            hp -= 2;
            burnTime -= 1;
        }
    }

    //funkcja dodaj czas trwania zatrucia
    public void addPoisonTime(int poison) {
        this.poisonTime += poison;
    }

    // funkcja zadajaca obrazenia z zatrucia
    public void getPoisonDamage() {
        if (poisonTime > 0) {
            hp -= 5;
            poisonTime -= 1;
        }
    }

    //funkcja dodaje czas trwania kwawienia
    public void addBleedingTime(int bleeding) {
        this.bleedingTime += bleeding;
    }

    // funkcja zadajaca obrazenia z kwawienia
    public void getBleedingDamage() {
        if (bleedingTime > 0) {
            hp -= 2;
            bleedingTime -= 1;
        }
    }

    // funkcja pobierajaca dane o procencie szans na wystapienie zamrozenia
    public void getFreezing(double freez) {
        this.freezing += freez;
    }
    public void flameArmorUsed(boolean flameArmor){
        flameArmorUsed = flameArmor;
    }
    private void flameArmorEffect(Card card){
        if(card.getCardType() == Card.cardType.Weapon && flameArmorUsed){
            AddBurnTime(8);
            flameArmorUsed = false;
        }
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
        } else if (bleed) {
            bleedingTime = 0;
        } else if (burn) {
            burnTime = 0;
        } else if (shield) {
            sp = 0;
        }

    }

    // funkcja pozwala uruchomic funkcje dodajaca hp co ture
    public void healByTurn(boolean crownReady) {
        this.crownReady = true;

    }
    // metoda potrzebna do wypalenia
    public void burnout(boolean burnout){
        this.burnout = true;
    }
    public void letBurnout(Player player){
        if(burnout){
            hp -= (burnTime*2);
            burnout = false;
        }
    }

    //funkcje zadająca obrażenia takie jak poprzednio zadał przeciwnik; stworzone na rzecz dragon blood
    public void saveHp(Player player) {
        lostHp = hp;
    }

    public void riposte(Player player) {
        hp -= lostHp;
        lostHp = 0;
    }
    // funkcja potrzebna do karty charon
    public void charon(boolean charonReady){
        if(hp <= 20){
            hp =0;
        }
    }

    public void takeCard() {
        numberOfCardsOnStack--;
    }
}
