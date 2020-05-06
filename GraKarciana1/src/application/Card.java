package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

    // podstawowe informacje do karty
    private final String name;
    private final int id;
    private final int damage;
    private final int armor;
    private final int value;
    private final int hpIncrease;
    public ImageView imageView;
    private boolean chosen = false;
    public int positionOnTable = 0;

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
        Image image = new Image(getClass().getResourceAsStream(imageName));
        imageView = new ImageView(image);
        imageView.setOnMouseEntered(e -> {

            imageView.setSmooth(false);
            imageView.setScaleX(1.5);
            imageView.setScaleY(1.5);

        });
        imageView.setOnMouseExited(e -> {

            imageView.setSmooth(false);
            imageView.setScaleX(1);
            imageView.setScaleY(1);

        });

        imageView.setOnMouseClicked(ee -> {

            setChosen();
        });
    }

    /*
    funkcja służąca do określenia dodatkowych funkcji karty w zależności od jej typu
     */
    public void Action(Player player, Player enemy) {
        TakeDamage(enemy);
        GiveArmor(player);
        GiveHp(player);
        switch (type) {
            case Fire: {
                switch (name) {
                    case "FireBall" -> Ignite(enemy);
                    case "Flame Armor" -> FlameArmor(enemy);  // nakłada podpalenie jesli przeciwnik uzyje karty broni
                    case "Promethean fire" -> Ignite(enemy);
                }
                break;
            }
            case Ice: {
                if (value == 1) {
                     freez = 25;
                    Freezing(enemy);
                    // szanse na zamrozenie w 25%
                } else if (value == 2) {
                     freez = 30;
                    Freezing(enemy);
                    // szanse na zamrozenie w 30%
                } else if (value == 3) {
                     freez = 80;
                    Freezing(enemy);
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
                    Bleeding(enemy);
                    Poison(enemy);
                }else if (name.equals("Crown of Immortality")){
                    player.HealByTurn(true);
                    // dodaje 2hp co ture
                }else if (name.equals("Burnout")){
                    player.Burnout(true);
                    //zadaje obrazenia z podpalenia i nie zdejmuje go
                }else if (name.equals("Charon")){
                    CharonReady(enemy);
                    // zabija przeciwnika jeśli ma 20 i mniej hp, nie uwzglednia tarczy
                }
                break;
            }
            case Action: {
                break;
            }
            case Water: {
                switch (name) {
                    case "Tsunami" -> player.Purification(false, false, false, true);
                    case "Fog" -> {
                         freez = 50;
                        Freezing(enemy);  // przeciwnik ma 50% wywołanie efektu karty
                    }
                    case "Poseidon's trident" -> player.Purification(true, true, true, false);
                }

                break;
            }
            case Weapon: {
                switch (name) {
                    case "Bow" -> player.cardInHand.DrawArrowCards();  //dobierane sa do reki dwie karty Arrow
                    case "Mace" -> Bleeding(enemy);
                    case "Dragon Killer" -> player.Purification(false, false, true, false);
                }
                break;
            }
            case Potion: {
                TakeDamage(enemy);
                GiveArmor(player);
                GiveHp(player);
                switch (name) {
                    case "Potion of Poison" -> Poison(enemy);
                    case "Dragon Blood" -> player.SaveHp(player);
                    case "Tulip" -> Bleeding(enemy);
                }

                break;
            }
            default:
                break;

        }


    }


    //kolejne funkcjie określające dodatkowe umiejetności kart

    // zadanie ataku
    private void TakeDamage(Player player) {
        player.dealDamage(damage);
    }

    // nadanie pancerza
    private void GiveArmor(Player player) {
        player.increaseSp(armor);
    }

    // dadanie hp
    private void GiveHp(Player player) {
        player.increaseHp(hpIncrease);
    }

    // podanie dlugosci podpalenia
    private void Ignite(Player player) {
        player.AddBurnTime(burn);
    }

    //podanie dlugosci zatrucia
    private void Poison(Player player) {
        player.AddPoisonTime(poisone);
    }

    public cardType GetCardType(){
        return type;
    }

    // podanie dlugosci krwaweinia
    private void Bleeding(Player player) {
        player.AddBleedingTime(bleed);
    }

    // podanie szans na zamrozenie
    private void Freezing(Player player) {
        player.GetFreezing(freez);
    }

    // funkcja potrzebna do karty charona
    private void CharonReady(Player player){
        player.Charon(true);
    }
    private void FlameArmor(Player player){
        player.FlameArmorUsed(true);
    }

    private void setChosen() {

        chosen = true;
    }

    public boolean getChosen() {

        return chosen;
    }

}

