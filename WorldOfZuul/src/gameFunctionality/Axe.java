package gameFunctionality;

public class Axe {
    private final int price = 49;
    private final int damage = 3;
    private final String description;
    private int longevity = 40;

    public Axe(String description, int longevity) {
        this.description = description;
        this.longevity = longevity;
    }

    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public String getDescription() {
        return description;
    }

    public int getLongevity() {
        return longevity;
    }
    
    public int reduceLongevity() {
        return --longevity;
    }
    
    
    
    
    
}