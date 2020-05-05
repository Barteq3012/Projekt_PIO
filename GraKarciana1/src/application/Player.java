package application;

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
    private double freezing; // zmienna warunkująca procent szans na wystąpienie efektu

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
    public void SetMyEnemy(Player player){
        myEnemy = player;
    }

    public void throwCard() {

        placeOnTable.getChildren().add(card.imageView);
        card.imageView.setX(positionX);
        card.imageView.fitWidthProperty().bind(placeOnTable.widthProperty());
        card.imageView.setPreserveRatio(true);

        card.Action(this, myEnemy);

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

    // funkcja adajaca obrazenia
    public void dealDamage(int damage) {
        hp -= damage;
    }

    // funkcja dodajaca zdrowia
    public void increaseHp(int hpAmount) {
        hp += hpAmount;
    }

    //funkcja dodajaca tarczy
    public void increaseSp(int spAmount) {
        sp += spAmount;
    }

    //funkcja odejmujaca tarczy
    public void decreaseSp(int spAmount) {
        sp -= spAmount;
    }

    //funkcja pobierajaca czas trwania podpalenia
    public void GetBurnTime(int burn) {
        this.burnTime += burn;
    }

    // funkcja zadajaca obrazenia z podpalenia
    public void GetBurnDamage() {
        if (burnTime > 0) {
            hp -= 2;
            burnTime -= 1;
        }
    }

    //funkcja pobierajaca czas trwania zatrucia
    public void GetPoisonTime(int poison) {
        this.poisonTime += poison;
    }

    // funkcja zadajaca obrazenia z zatrucia
    public void GetPoisonDamage() {
        if (poisonTime > 0) {
            hp -= 5;
            poisonTime -= 1;
        }
    }

    //funkcja pobierajaca czas trwania kwawienia
    public void GetBleedingTime(int bleeding) {
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

    /**
     * Zdejmuje podane efekty w przypadku gdy dana zmienna jest "true"
     * @param poison jeśli prawda zdejmuje zatrucie
     * @param bleed jeśli prawda zdejmuje krwawienie
     * @param burn jeśli prawda zdejmuje podpalenie
     * @param shield jeśli prawda dejmuje całą tarczę
     */
    public void Purification(boolean poison, boolean bleed, boolean burn, boolean shield){
        if(poison){
            poisonTime = 0;
        }else if(bleed){
            bleedingTime = 0;
        }else if(burn){
            burnTime = 0;
        }else if(shield){
            sp = 0;
        }

    }


    public void takeCard() {
        cardsOnStack--;
    }



}
