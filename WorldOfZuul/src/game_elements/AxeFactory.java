package game_elements;

public class AxeFactory {

    private AxeFactory() {
    }

    public static Axe createStarterAxe() {
        return new Axe("Starter Axe", 0, 5, 3);
    }

    public static Axe createIronAxe() {
        return new Axe("Iron Axe", 49, 20, 3);
    }

    public static Axe createSteelAxe() {
        return new Axe("Steel Axe", 119, 40, 4);
    }

    public static Axe createDiamondAxe() {
        return new Axe("Diamond Axe", 229, 60, 6);
    }

    public static Axe createFireAxe() {
        return new Axe("Fire Axe", 349, 120, 12);
    }
}
