package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

    // podstawowe informacje do karty
    private final String name;
    private final int id;
    public ImageView imageView;

    private final int value;
    private final int damage;
    private final int armor;
    private final int hpIncrease;
    private String imageName;

    public Player owner;

    private boolean chosen = false;
    public boolean onTable = false;
    public int positionOnTable = 0;
    public double absolutePosition;

    public boolean alreadyRandToDeck = false;
    public boolean alreadyRandToSmallDeck = false;

    public enum cardType {Fire, Water, Ice, Weapon, Action, Counter, Potion, Magic}
    private final cardType type;

    //dodatkowe efekty kart
    private final int burn = 6;  // czas nadany przez jedna karte podpalenia
    private final int poisone = 4;   // czas na jaki jedna karta nałoży trucizne
    private final int bleed = 8;   // czas na jaki karta nalozy krwawienie
    private int freez; // uwarunkuje zamroznie; liczby od 0 do 100;
    private boolean flameArmor;  //specjalna zmienna dla plomiennej zbroi

    /**
     * wyjasnienie danych do objektu Card
     *
     * @param name       Nawa karty
     * @param id         numer karty dzieki któremu będzie wywoływana
     * @param value      cenność karty
     * @param damage     zadawane obrażenia podstawowe
     * @param armor      dodawany pancerz dla postaci
     * @param type       rodzaj karty dostosowany do nakładanych efektów
     * @param imageName  nazwa obraku przypisanego do karty
     * @param hpIncrease ilość dodawanego hp przez karte
     */
    public Card(String name, int id, int value, int damage, int armor, cardType type, String imageName, int hpIncrease) {

        this.name = name;
        this.id = id;
        this.damage = damage;
        this.armor = armor;
        this.hpIncrease = hpIncrease;
        this.type = type;
        this.value = value;
        this.imageName = imageName;

        Image image = new Image(getClass().getResourceAsStream(imageName));
        imageView = new ImageView(image);

        imageView.setOnMouseEntered(e -> {

            imageView.toFront(); //wyświetlanie na wierzchu

            imageView.setSmooth(false); //lepsza jakość czy szybkość podczas skalowania
            imageView.setScaleX(1.5);
            imageView.setScaleY(1.5);

        });

        imageView.setOnMouseExited(e -> {

            if(onTable) {

                owner.setNiceCards(); // jesli karta na stole to po zjechaniu układa ładnie karty
            }


            imageView.setSmooth(false);
            imageView.setScaleX(1);
            imageView.setScaleY(1);

        });

        imageView.setOnMouseClicked(ee -> {

            if(Game.ready) {
                setChosen();
            }
        });
    }

    /*
    funkcja służąca do określenia dodatkowych funkcji karty w zależności od jej typu
     */
    public void action(Player player, Player enemy) {
        takeDamage(enemy);
        giveArmor(player);
        giveHp(player);
        switch (type) {
            case Fire: {
                switch (name) {
                    case "FireBall" -> ignite(enemy);
                    case "Flame Armor" -> flameArmor(enemy);  // nakłada podpalenie jesli przeciwnik uzyje karty broni
                    case "Promethean fire" -> ignite(enemy);
                }
                break;
            }
            case Ice: {
                if (value == 1) {
                     freez = 25;
                    freezing(enemy);
                    // szanse na zamrozenie w 25%
                } else if (value == 2) {
                     freez = 30;
                    freezing(enemy);
                    // szanse na zamrozenie w 30%
                } else if (value == 3) {
                     freez = 80;
                    freezing(enemy);
                    //szanse na zamrozenie w 80%
                }

                break;
            }
            case Magic: {
                if (name.equals("Joker")) {
                    // wywołuje wybraną kartę ze stołu; wcześniej rzuconą przez nas karte
                }else if (name.equals("Medusa Look")){
                    // zabrania wywołac karte dającą tarcze oraz karty leczace
                }else if (name.equals("Summon Cerberus")){
                    bleeding(enemy);
                    poison(enemy);
                }else if (name.equals("Crown of Immortality")){
                    player.healByTurn(true);
                    // dodaje 2hp co ture
                }else if (name.equals("Burnout")){
                    player.burnout(true);
                    //zadaje obrazenia z podpalenia i nie zdejmuje go
                }else if (name.equals("Charon")){
                    charonReady(enemy);
                    // zabija przeciwnika jeśli ma 20 i mniej hp, nie uwzglednia tarczy
                }
                break;
            }
            case Action: {
                break;
            }
            case Water: {
                switch (name) {
                    case "Tsunami" -> player.purification(false, false, false, true);
                    case "Fog" -> {
                         freez = 50;
                        freezing(enemy);  // przeciwnik ma 50% wywołanie efektu karty
                    }
                    case "Poseidon's trident" -> player.purification(true, true, true, false);
                }

                break;
            }
            case Weapon: {
                switch (name) {
                    case "Bow" -> player.cardInHand.DrawArrowCards();  //dobierane sa do reki dwie karty Arrow
                    case "Mace" -> bleeding(enemy);
                    case "Dragon Killer" -> player.purification(false, false, true, false);
                }
                break;
            }
            case Potion: {
                takeDamage(enemy);
                giveArmor(player);
                giveHp(player);
                switch (name) {
                    case "Potion of Poison" -> poison(enemy);
                    case "Dragon Blood" -> player.saveHp(player);
                    case "Tulip" -> bleeding(enemy);
                }

                break;
            }
            default:
                break;

        }


    }


    //kolejne metody określające dodatkowe umiejetności kart

    // zadanie ataku
    private void takeDamage(Player player) {
        player.dealDamage(damage);
    }

    // nadanie pancerza
    private void giveArmor(Player player) {
        player.increaseSp(armor);
    }

    // dadanie hp
    private void giveHp(Player player) {
        player.increaseHp(hpIncrease);
    }

    // podanie dlugosci podpalenia
    private void ignite(Player player) {
        player.AddBurnTime(burn);
    }

    //podanie dlugosci zatrucia
    private void poison(Player player) {
        player.addPoisonTime(poisone);
    }

    public cardType getCardType(){
        return type;
    }

    // podanie dlugosci krwaweinia
    private void bleeding(Player player) {
        player.addBleedingTime(bleed);
    }

    // podanie szans na zamrozenie
    private void freezing(Player player) {
        player.getFreezing(freez);
    }

    // funkcja potrzebna do karty charona
    private void charonReady(Player player){
        player.charon(true);
    }

    private void flameArmor(Player player){
        player.flameArmorUsed(true);
    }

    private void setChosen() {

        chosen = true;
    }

    public boolean getChosen() {

        return chosen;
    }

    public int getValue() {
        return value;
    }

    public cardType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getArmor() {
        return armor;
    }

    public int getDamage() {
        return damage;
    }

    public int getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }

    public int getHpIncrease() {
        return hpIncrease;
    }
}

