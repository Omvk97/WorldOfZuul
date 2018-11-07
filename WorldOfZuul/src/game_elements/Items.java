package game_elements;

public abstract class Items {


    private final String description;
    private final int price;
    
    public Items(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}