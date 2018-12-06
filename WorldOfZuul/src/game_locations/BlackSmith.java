package game_locations;

import game_elements.Axe;
import game_elements.AxeFactory;
import game_functionality.Player;
import java.io.IOException;
import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BlackSmith extends Room {

    private final String blackSmith = "Smith:\n";
    private final Scanner userPurchaseChoice = new Scanner(System.in);

    public BlackSmith() {
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return blackSmith + "Welcome to my shop! \n"
            + "If you pay I will make your axe stronger \n"
            + "You have " + humanPlayer.getMoney() + " gold coins\n"
            + "-----------------------------------------------\n"
            + "○ Repair ➤ Make your axe like new\n"
            + "○ Buy    ➤ Buy a new axe\n"
            + "---------------------------------------------";
    }

    private String Axe_menu(Player humanPlayer) {
        Axe ironAxe = AxeFactory.createIronAxe();
        Axe steelAxe = AxeFactory.createSteelAxe();
        Axe diamondAxe = AxeFactory.createDiamondAxe();
        Axe fireAxe = AxeFactory.createFireAxe();
        System.out.println(blackSmith + "I have many different axes, all "
            + "sharp as an arrow's tip.\n"
            + ironAxe + "\n"
            + steelAxe + "\n"
            + diamondAxe + "\n"
            + fireAxe + "\n"
            + "Which axe would you like to buy?");
        String userAxeChoice = userPurchaseChoice.nextLine();
        String userChoiceWithoutBloat = userAxeChoice.toLowerCase().replaceAll("\\s+", "");
        switch (userChoiceWithoutBloat) {
            case "1":
            case "ironaxe":
                return getAxeInfo(humanPlayer, ironAxe);
            case "2":
            case "steelaxe":
                return getAxeInfo(humanPlayer, steelAxe);
            case "3":
            case "diamondaxe":
                return getAxeInfo(humanPlayer, diamondAxe);
            case "4":
            case "fireaxe":
                return getAxeInfo(humanPlayer, fireAxe);
            default:
                return "Alright bye bye";
        }
    }

    private String getAxeInfo(Player humanPlayer, Axe axe) {
        if (humanPlayer.getMoney() >= axe.getPrice()) {
            humanPlayer.boughtAxe(axe);
            return blackSmith + "You just bought a " + axe.getDescription() + "!\n"
                + "It costs you " + axe.getPrice() + " gold coins"
                + "\nEnjoy it while it lasts!";
        } else {
            return blackSmith + "YOU NEED " + axe.getPrice() + " GOLD COINS TO BUY THIS AXE";
        }
    }

    private String grindAxe_menu(Player humanPlayer) {
        final int pricePerAxeDurability = 2;

        if (humanPlayer.getAxe() == null) {
            return blackSmith + "You don't have an axe equipped";

        } else if (humanPlayer.getAxe().getDurability() == humanPlayer.getAxe().getStartDurability()) {
            return "Your axe is fine! Come back if it ever gets dull";

        } else if (humanPlayer.getAxe().getDurability() < humanPlayer.getAxe().getStartDurability()) {
            int durabilityLostOnAxe = humanPlayer.getAxe().getStartDurability() - humanPlayer.getAxe().getDurability();
            int fixAxePrice = pricePerAxeDurability * durabilityLostOnAxe;
            if (humanPlayer.getMoney() >= fixAxePrice) {
                System.out.println(blackSmith + "I will grind your axe for you. Please wait");
                int timeToWait = 6;
                try {
                    for (int i = 0; i < timeToWait; i++) {
                        Thread.sleep(1000);
                        System.out.println("**Ding**");
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                humanPlayer.grindedAxe(fixAxePrice);
                return "Your axe is done";
            } else {
                return "You do not have enough money";
            }
        } else {
            return "I don't know what to do";
        }
    }

    @Override
    public String option1(Player humanPlayer) {
        return grindAxe_menu(humanPlayer);
    }

    @Override
    public String option2(Player humanPlayer) {
        return Axe_menu(humanPlayer);
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/room_fxml/Blacksmith.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
