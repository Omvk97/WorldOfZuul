package game_locations;

import game_elements.Axe;
import game_elements.AxeFactory;
import game_functionality.Player;

import java.util.Scanner;

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

    private void Axe_menu(Player humanPlayer) {
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
                getAxeInfo(humanPlayer, ironAxe);
                break;
            case "2":
            case "steelaxe":
                getAxeInfo(humanPlayer, steelAxe);
                break;
            case "3":
            case "diamondaxe":
                getAxeInfo(humanPlayer, diamondAxe);
                break;
            case "4":
            case "fireaxe":
                getAxeInfo(humanPlayer, fireAxe);
                break;
            default:
                break;
        }
    }

    private void getAxeInfo(Player humanPlayer, Axe axe) {
        if (humanPlayer.getMoney() >= axe.getPrice()) {
            System.out.println(blackSmith + "You just bought a " + axe.getDescription() + "!\n"
                + "It costs you " + axe.getPrice() + " gold coins"
                + "\nEnjoy it while it lasts!");
            humanPlayer.boughtAxe(axe);
        } else {
            System.out.println(blackSmith + "YOU NEED " + axe.getPrice() + " GOLD COINS TO BUY THIS AXE");
        }
    }

    private void grindAxe_menu(Player humanPlayer) {
        final int pricePerAxeDurability = 2;

        if (humanPlayer.getAxe() == null) {
            System.out.println(blackSmith + "You don't have an axe equipped");

        } else if (humanPlayer.getAxe().getDurability() == humanPlayer.getAxe().getStartDurability()) {
            System.out.println("Your axe is fine! Come back if it ever gets dull");

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
                    humanPlayer.grindedAxe(fixAxePrice);
                    System.out.println("Your axe is done");
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("You do not have enough money");
            }
        }
    }

    @Override
    public void option1(Player humanPlayer) {
        grindAxe_menu(humanPlayer);
    }

    @Override
    public void option2(Player humanPlayer) {
        Axe_menu(humanPlayer);
    }
}
