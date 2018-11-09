package game_elements;

public class BackPackFactory {

    private BackPackFactory() {
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
