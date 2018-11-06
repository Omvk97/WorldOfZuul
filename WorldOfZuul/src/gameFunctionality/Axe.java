package gameFunctionality;

public class Axe extends Items {

    private final int damage;
    private final int startDurability;
    private int durability;

    public Axe(String name, int price, int startDurability, int damage) {
        super(name, price);
        this.damage = damage;
        this.startDurability = startDurability;
        this.durability = startDurability;
    }

    public int getDamage() {
        return damage;
    }

    public int getStartDurability() {
        return startDurability;
    }

    public int getDurability() {
        return durability;
    }

    public int reduceDurability() {
        return --durability;
    }
    

    @Override
    public String toString() {
        return getDescription() + ": " + getPrice();
    }
}
