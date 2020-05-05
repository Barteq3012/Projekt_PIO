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

    public enum cardType {Fire, Water, Ice, Earth, Weapon, Action, Counter, Potion, Magic}

    private final cardType type;

    //dodatkowe efekty kart
    private int burn = 6;  // czas nadany przez jedna karte podpalenia
    private int poisone = 4;   // czas na jaki jedna karta nałoży trucizne
    private int bleed = 8;   // czas na jaki karta nalozy krwawienie
    private double freez; // uwarunkuje zamroznie;
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
    public Card(String name, int id, int value, int damage, int armor, cardType type, String imageName, int hpIncrease ) {
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
        switch (type) {
            case Fire: {
                TakeDamage(enemy);
                GiveArmor(player);
                GiveHp(player);
                if (name.equals("FireBall")) {
                    Ignite(enemy);
                } else if (name.equals("Flame Armor")) {
                    flameArmor = true;
                    // nakłada podpalenie jesli przeciwnik uzyje karty broni

                } else if (name.equals("Promethean fire")) {
                    Ignite(enemy);
                }
                break;
            }
            case Ice: {
                TakeDamage(enemy);
                GiveArmor(player);
                if(value == 1){
                    // szanse na zamrozenie w 25%
                }else if(value == 2){
                    // szanse na zamrozenie w 30%
                }else if (value == 3){
                    //szanse na zamrozenie w 80%
                }

                break;
            }
            case Earth: {
                TakeDamage(enemy);
                GiveArmor(player);
                break;
            }
            case Action: {
                TakeDamage(enemy);
                GiveArmor(player);
                break;
            }
            case Water: {
                TakeDamage(enemy);
                GiveArmor(player);
                if (name.equals("Tsunami")) {
                    player.Purification(false,false,false, true);
                }else if(name.equals("Fog")){
                    // przeciwnik ma 50% wywołanie efektu karty
                }else if(name.equals("Poseidon's trident")){
                    player.Purification(true,true,true,false);
                }

                break;
            }
            case Weapon: {
                TakeDamage(enemy);
                GiveArmor(player);
                if(name.equals("Bow")){
                    player.cardInHand.DrawArrowCards();
                    //dobierane sa do reki dwie karty Arrow
                }
                break;
            }
            case Counter: {

                break;
            }
            case Potion: {
                TakeDamage(enemy);
                GiveArmor(player);

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
        player.GetBurnTime(burn);
    }

    //podanie dlugosci zatrucia
    private void Poison(Player player) {
        player.GetPoisonTime(poisone);
    }

    // podanie dlugosci krwaweinia
    private void Bleeding(Player player) {
        player.GetBleedingTime(bleed);
    }

    // podanie szans na zamrozenie
    private void Freezing(Player player) {
        player.GetFreezing(freez);
    }

    private void setChosen() {

        chosen = true;
    }

    public boolean getChosen() {

        return chosen;
    }

}

