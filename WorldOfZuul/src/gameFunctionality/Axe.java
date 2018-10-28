package gameFunctionality;

public class Axe {

    private final String description;
    private final int damage;
    private final int startDurability;
    private final int price;
    private int durability;

    public Axe(String description, int damage, int startDurability, int price) {
        this.description = description;
        this.damage = damage;
        this.startDurability = startDurability;
        this.price = price;
        this.durability = startDurability;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage() {
        return damage;
    }

    public int getStartDurability() {
        return startDurability;
    }

    public int getPrice() {
        return price;
    }

    public int getDurability() {
        return durability;
    }

    public int reduceDurability() {
        return --durability;
    }

}
