package application;

import java.util.Random;
import java.util.ArrayList;

import javafx.scene.layout.Pane;

public class Player {

    public int hp;
    public int sp;
    public int cardsOnStack;
    public String name;

    // Nadaje sobie przeciwnika; wskaznik do oponenta
    private Player myEnemy;

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

    public Random random;
    public Card card;
    public CardInHand cardInHand;

    private Pane placeOnTable = new Pane();

    private int positionX = 0;


    public Player(String name, int hp, int sp, CardInHand cardInHand) {
        this.name = name;
        this.hp = hp;
        this.sp = sp;
        this.cardInHand = cardInHand;
    }

    public void SetMyEnemy(Player player) {
        myEnemy = player;
    }

    public void throwCard() {
        //funkcja stworzona na potrzeby dragon blood; słuzy do przeprowadzenia kontrataku na przeciwniku za poprzednia ture, nie uwzgledznia tarczy
        if (lostHp > hp) {
            lostHp = lostHp - hp;
            Riposte(myEnemy);
        }
        if (crownReady) {
            hp += 2;
        }
        LetBurnout(myEnemy);

        placeOnTable.getChildren().add(card.imageView);
        card.imageView.setX(positionX);
        card.imageView.fitWidthProperty().bind(placeOnTable.widthProperty());
        card.imageView.setPreserveRatio(true);


        // losuje liczbe aby potem użyć do stwierdzenia czy efekt karty sie wywoła czy nie
        randomNumber = random.nextInt(101);
        if (randomNumber < freezing) {
            freezing = 0;  // wyzerowanie miennej odpowiedzialenj za brak uzycia akcji karty
        } else {
            // wywołanie akcji karty
            card.Action(this, myEnemy);
        }
        FlameArmorEffect(card);

        // wywołanie wszystkich efektów czasowych
        GetBleedingDamage();
        GetBurnDamage();
        GetPoisonDamage();

        moveCard();
    }

    private void moveCard() {

        positionX += 110;
    }

    public void setPlaceOnTable(int placePositionX, int placePositionY, Pane table) {
        table.getChildren().add(placeOnTable);

        placeOnTable.setLayoutX(placePositionX);
        placeOnTable.setLayoutY(placePositionY);
        placeOnTable.setPrefSize(100, 100);
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
    public void GetBurnDamage() {
        if (burnTime > 0) {
            hp -= 2;
            burnTime -= 1;
        }
    }

    //funkcja dodaj czas trwania zatrucia
    public void AddPoisonTime(int poison) {
        this.poisonTime += poison;
    }

    // funkcja zadajaca obrazenia z zatrucia
    public void GetPoisonDamage() {
        if (poisonTime > 0) {
            hp -= 5;
            poisonTime -= 1;
        }
    }

    //funkcja dodaje czas trwania kwawienia
    public void AddBleedingTime(int bleeding) {
        this.bleedingTime += bleeding;
    }

    // funkcja zadajaca obrazenia z kwawienia
    public void GetBleedingDamage() {
        if (bleedingTime > 0) {
            hp -= 2;
            bleedingTime -= 1;
        }
    }

    // funkcja pobierajaca dane o procencie szans na wystapienie zamrozenia
    public void GetFreezing(double freez) {
        this.freezing += freez;
    }
    public void FlameArmorUsed(boolean flameArmor){
        flameArmorUsed = flameArmor;
    }
    private void FlameArmorEffect(Card card){
        if(card.GetCardType() == Card.cardType.Weapon && flameArmorUsed){
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
    public void Purification(boolean poison, boolean bleed, boolean burn, boolean shield) {
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
    public void HealByTurn(boolean crownReady) {
        this.crownReady = true;

    }
    // metoda potrzebna do wypalenia
    public void Burnout(boolean burnout){
        this.burnout = true;
    }
    public void LetBurnout(Player player){
        if(burnout){
            hp -= (burnTime*2);
            burnout = false;
        }
    }

    //funkcje zadająca obrażenia takie jak poprzednio zadał przeciwnik; stworzone na rzecz dragon blood
    public void SaveHp(Player player) {
        lostHp = hp;
    }

    public void Riposte(Player player) {
        hp -= lostHp;
        lostHp = 0;
    }
    // funkcja potrzebna do karty charon
    public void Charon(boolean charonReady){
        if(hp <= 20){
            hp =0;
        }
    }

    public void takeCard() {
        cardsOnStack--;
    }


}
