package domain_layer.game_elements;

/**
 *
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
