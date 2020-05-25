package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Random;

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
    public int repeated = 0;
    public Image image;

    public boolean alreadyRandToDeck = false;
    public boolean alreadyRandToSmallDeck = false;

    public enum cardType {Fire, Water, Ice, Weapon, Action, Counter, Potion, Magic}
    private final cardType type;

    int burnTime = 6; // czas nadany przez jedna karte podpalenia
    int poisone = 4; // czas na jaki jedna karta nałoży trucizne
    int bleed = 8; // czas na jaki karta nalozy krwawienie

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

        image = new Image(getClass().getResourceAsStream(imageName));
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
                    case "Flame Armor" -> enemy.flameArmorUsed(true);  // nakłada podpalenie jesli przeciwnik uzyje karty broni
                    case "Spark" -> {

                        Random random = new Random();
                        int randomNumber = random.nextInt(100) + 1;

                        if(randomNumber > 50) {
                            ignite(enemy);
                        }
                    }
                    case "Pain" -> {

                        ignite(player);
                        bleeding(player);
                        player.dealDamage(10);
                    }
                    case "Pandemonium" -> {

                        ignite(enemy);
                        enemy.dealDamage(player.cardsOntable.size() * 2);
                    }
                    case "Feniks" -> {

                        ignite(enemy);

                        if(player.hp < enemy.hp) {

                            enemy.dealDamage(20);
                        }
                    }
                }
                break;
            }
            case Ice: {

                if (value == 1) {

                    // szanse na zamrozenie w 25%
                    freezing(enemy, 25);

                } else if (value == 2) {

                    // szanse na zamrozenie w 30%
                    freezing(enemy, 30);

                } else if (value == 3) {

                    //szanse na zamrozenie w 80%
                    freezing(enemy, 80);
                }
                break;
            }
            case Magic: {

                if (name.equals("Joker")) {
                    // zamienia się w losową kartę ze stołu; wcześniej rzuconą przez nas karte

                    player.cardsOntable.remove(this);

                    Random random = new Random();

                    if(player.cardsOntable.size() > 0) {

                        int randomNumber = random.nextInt(player.cardsOntable.size());

                        player.cardsOntable.add(this);

                        Card randCard = player.cardsOntable.get(randomNumber);

                        player.card.imageView.setImage(randCard.image);
                        randCard.action(player, enemy);
                    } else {
                        player.cardsOntable.add(this);
                    }

                }else if (name.equals("Medusa Look")){

                    // zabrania wywołac karte dającą tarcze oraz karty leczace
                    enemy.medusa = true;

                }else if (name.equals("Summon Cerberus")){
                    bleeding(enemy);
                    poison(enemy);
                }else if (name.equals("Crown of Immortality")){

                    player.healByTurn(true);
                    // dodaje 2hp co ture

                }else if (name.equals("Burnout")){

                    enemy.letBurnout();
                    //zadaje obrazenia z podpalenia i nie zdejmuje go

                }else if (name.equals("Charon")){

                    // zabija przeciwnika jeśli ma 20 i mniej hp, nie uwzglednia tarczy
                    enemy.charon();

                } else if (name.equals("Time of life and death")){

                    enemy.dealDamage(enemy.cardInHand.getSize());
                    player.increaseHp(player.cardInHand.getSize());

                } else if (name.equals("Anger of God")){
                    
                    enemy.setFreezing(50);

                } else if (name.equals("Rays of the Sun")){

                    player.purification(true, true, true, false);

                } else if (name.equals("Dazzle")){

                    enemy.setFreezing(100); //blok

                    enemy.increaseSp(-15);

                    if(enemy.sp < 0) {
                        enemy.sp = 0;
                    }

                } else if (name.equals("Pandora's box")){

                    if(player.bleedingTime > 0) {

                        enemy.dealDamage(10);
                    }

                } else if (name.equals("Duplicity")){

                    if(enemy.sp > 0) {

                        int sup;
                        sup = enemy.hp;
                        enemy.hp = enemy.sp;
                        enemy.sp = sup;
                    }

                } else if (name.equals("Gods gift")) {

                    Random random = new Random();
                    int randomNumber = random.nextInt(100) + 1;

                    if (randomNumber > 10) {

                        player.cardInHand.drawSweetness();
                        enemy.cardInHand.drawPain();
                    } else {

                        player.cardInHand.drawPain();
                        enemy.cardInHand.drawSweetness();
                    }

                } else if (name.equals("Swallow Spell")){

                    List<Card> enemyCardsInHand = enemy.cardInHand.getCardsInHand();

                    Random random = new Random();

                    if(enemyCardsInHand.size() > 0) {

                        int randomNumber = random.nextInt(enemyCardsInHand.size());

                        Card randCard = enemyCardsInHand.get(randomNumber);

                        enemy.cardInHand.inHandPosition[randCard.positionOnTable] = 0;
                        randCard.positionOnTable = 0;

                        enemyCardsInHand.remove(randCard);
                        enemy.cardInHand.hand.getChildren().remove(randCard.imageView);

                        if(enemy.burnTime > 0) {

                            player.cardInHand.drawRandCard(randCard);
                        }
                    }
                } else if (name.equals("Hydra blood")){

                    for(Card card: player.cardsOntable) {

                        if(card.id == 13) {

                            Card bloodArrow = AllCard.summonCard.get(3);
                            player.card.imageView.setImage(bloodArrow.image);
                            bloodArrow.action(player, enemy);

                            break;
                        }
                    }
                } else if (name.equals("Typhon summon")){

                    poison(enemy);
                    ignite(enemy);

                } else if (name.equals("Golden fleece")) {

                    player.addImmuneTime(2);

                } else if (name.equals("Ambrosia")){

                    player.purification(true, false,false,false);

                } else if (name.equals("Soter")){

                    if(player.hp < 20) {

                        player.increaseHp(25);
                    }

                } else if (name.equals("Lord of the gods")){

                    Random random = new Random();
                    int randomNumber = random.nextInt(90) + 1;

                    if(randomNumber <= 30 ) {

                        player.increaseHp(30);
                    }
                    else if(randomNumber <= 60) {

                        player.increaseSp(30);
                    }
                    else {

                        enemy.dealDamage(30);
                    }

                } else if (name.equals("Appetite")){

                    enemy.increaseHp(-10);

                } else if (name.equals("Gliding")){

                    enemy.purification(false, false, false, true);
                    player.addImmuneTime(1);

                }

                break;
            }
            case Water: {
                switch (name) {
                    case "Tsunami" -> enemy.purification(false, false, false, true);
                    case "Fog" -> enemy.setFreezing(50); // blok
                    case "Poseidon's trident", "Sweetness" -> player.purification(true, true, true, false);
                    case "Poison resistance" -> player.purification(true, false, false, false);
                }

                break;
            }
            case Weapon: {
                switch (name) {
                    case "Bow" -> player.cardInHand.drawArrowCards();  //dobierane sa do reki dwie karty Arrow
                    case "Mace" -> bleeding(enemy);
                    case "Dragon Killer" -> player.purification(false, false, true, false);
                    case "Axe" -> player.dealDamage(5);
                    case "Perseus shield" -> player.increaseSp((int) (player.hp * 0.1));
                    case "Sun sword" -> freezing(enemy,50);
                    case "Drop torch" -> ignite(enemy);
                    case "Hydra head" -> enemy.hydraHead = true;
                    case "Snake bite" -> poison(enemy);
                    case "Egida" -> player.addEffectsImmuneTime(3);
                    case "Claws" -> bleeding(enemy);
                    case "Rain of feathers" -> freezing(enemy, 100);
                    case "Quilting" -> {

                        if(player.cardsOntable.get(player.cardsOntable.size() - 2).id == 62) {

                            enemy.dealDamage(30);
                        }
                        else {
                            enemy.dealDamage(15);
                        }
                    }
                }
                break;
            }
            case Potion: {
                switch (name) {
                    case "Potion of Poison" -> poison(enemy);
                    case "Dragon Blood" -> player.riposte();
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
    private void ignite(Player enemy) {

        if(enemy.effectsImmuneTime == 0) {

            enemy.addBurnTime(burnTime);
        }
    }

    //podanie dlugosci zatrucia
    private void poison(Player enemy) {

        if(enemy.effectsImmuneTime == 0) {

            enemy.addPoisonTime(poisone);
        }
    }

    // podanie dlugosci krwaweinia
    private void bleeding(Player enemy) {

        if(enemy.effectsImmuneTime == 0) {

            enemy.addBleedingTime(bleed);
        }
    }

    // podanie szans na zamrozenie
    private void freezing(Player enemy, int freez) {

        if(enemy.effectsImmuneTime == 0) {
            enemy.setFreezing(freez);
        }
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

    public cardType getCardType(){
        return type;
    }
}

