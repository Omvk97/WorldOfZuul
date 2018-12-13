package domain_layer.game_elements;

/**
 * Responsibility of creating new backpacks whenever the gameWorld needs it, this ensures that
 * when a new backpack is made that it always is a new backpack in main memory that is made.
 * @author oliver
 */
public class BackPackFactory {

    private BackPackFactory() {
    }
    public static BackPack createStarterBackPack() {
        return new BackPack("Starter Backpack", 0, 5);
    }
    public static BackPack createSmallBackPack() {
        return new BackPack("Small backpack", 129, 10);
    }
    public static BackPack createMediumBackPack() {
        return new BackPack("Medium backpack", 249, 15);
    }
    public static BackPack createLargeBackPack() {
        return new BackPack("Large backpack", 389, 20);
    }
}
