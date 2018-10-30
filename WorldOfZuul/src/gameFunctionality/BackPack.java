package gameFunctionality;

public class BackPack extends Items {

    private final int backpackCapacity;
    
    public BackPack(String description, int price, int backpackCapacity) {
        super(description, price);
        this.backpackCapacity = backpackCapacity;
    }

    public int getBackpackCapacity() {
        return backpackCapacity;
    }

    @Override
    public String toString() {
        return getDescription() + " with a capacity of " + getBackpackCapacity() +" - " + getPrice() + " gold coins";
    }
    
    
}