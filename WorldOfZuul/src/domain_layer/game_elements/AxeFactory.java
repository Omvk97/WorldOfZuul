package domain_layer.game_elements;

/**
 * Responsibility of creating new axes whenever the gameWorld needs it, this ensures that when a new
 * axe is made that it always is a new axe in main memory that is made.
 *
 * @author oliver
 */
public class AxeFactory {

    private AxeFactory() {
    }

    public static Axe createStarterAxe() {
        return new Axe("starter axe", 0, 5, 3);
    }

    public static Axe createIronAxe() {
        return new Axe("iron axe", 49, 10, 3);
    }

    public static Axe createSteelAxe() {
        return new Axe("steel axe", 119, 20, 4);
    }

    public static Axe createDiamondAxe() {
        return new Axe("diamond axe", 149, 40, 6);
    }

    public static Axe createFireAxe() {
        return new Axe("fire axe", 349, 80, 12);
    }
}
