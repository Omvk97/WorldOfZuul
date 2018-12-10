package game_elements;

/**
 *
 * @author oliver
 */
public abstract class Item {

    private final String description;
    private final int price;

    public Item(String description, int price) {
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
