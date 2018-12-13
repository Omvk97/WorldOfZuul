package domain_layer.game_elements;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Axe with damage
 * Price which is how many gold coins is required to purchase the axe and
 * Durability which is how many trees the axe can fell before getting destroyed.
 * @author oliver
 */
public class Axe extends Item {

    private final int damage;
    private final int startDurability;
    private final SimpleIntegerProperty durability = new SimpleIntegerProperty();

    public Axe(String name, int price, int startDurability, int damage) {
        super(name, price);
        this.damage = damage;
        this.startDurability = startDurability;
        this.durability.setValue(startDurability);
    }

    public int getDamage() {
        return damage;
    }

    public int getStartDurability() {
        return startDurability;
    }
    
    public SimpleIntegerProperty getDurabilityIntegerProperty() {
        return durability;
    }

    public int getDurability() {
        return durability.getValue();
    }

    public void reduceDurability() {
        durability.setValue(durability.getValue() - 1);
    }

    public void grindAxe() {
        durability.setValue(startDurability);
    }

    @Override
    public String toString() {
        return getDescription() + ": " + getPrice();
    }
}
