package Locations;

import gameFunctionality.*;
import java.util.Scanner;

public class Store extends Room {

    private final Trailer trailer;

    private final Axe ironAxe = new Axe("Iron axe", 49, 30, 3);
    private final Axe steelAxe = new Axe("Steel axe", 119, 40, 4);
    private final Axe diamondAxe = new Axe("Diamond axe", 149, 60, 6);
    private final Axe fireAxe = new Axe("Fire axe", 349, 120, 12);

    private final BackPack smallBackPack = new BackPack("Small backpack", 129, 10);
    private final BackPack mediumBackPack = new BackPack("Medium backpack", 249, 15);
    private final BackPack largeBackPack = new BackPack("Large backpack", 389, 20);
    Scanner userPurchaseChoice = new Scanner(System.in);

    public Store(String description, Player player, Trailer trailer) {
        super(description, player);
        this.trailer = trailer;
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "Here you can sell your logs and purchase new equipment \n"
            + "Option 1 - Sell logs\n"
            + "Option 2 - Buy a new axe\n"
            + "Option 3 - Upgrade your storage and backpack";
    }

    /**
     * Denne metode benytter sig af information fra 'trailer' og 'player' Den sælger træerne som spilleren enten går
     * rundt med, eller træer som spilleren har i sit storage. Eller begge dele på en gang.
     */
    @Override
    public void option1() {
        if (trailer.getLogsInStorage().isEmpty() && humanPlayer.getLogsCarrying().isEmpty()) {
            System.out.println("You have no logs to sell!");
            return;
        }
        if (!humanPlayer.getLogsCarrying().isEmpty()) {
            for (Tree tree : humanPlayer.getLogsCarrying()) {
                humanPlayer.addMoney(tree.getTreePrice());
            }
            humanPlayer.loadOfLogs();
            System.out.println("You have sold all the logs you were carrying!");
        }

        if (!trailer.getLogsInStorage().isEmpty()) {
            trailer.getLogsInStorage().forEach((tree) -> {
                humanPlayer.addMoney(tree.getTreePrice());
            });
            trailer.loadOffLogsInStorage();
            System.out.println("You have sold all the logs in your storage!");
        }
    }

    @Override
    public void option2() {
        System.out.println("You see here my good friend! 4 different axes, sharp as an arrowtip.\n"
            + "Iron Axe: " + ironAxe.getPrice()
            + "\nSteel Axe: " + steelAxe.getPrice()
            + "\nDiamond Axe: " + diamondAxe.getPrice()
            + "\nFire Axe: " + fireAxe.getPrice());

        System.out.println("Which axe would you like to buy?");
        String userAxeChoice = userPurchaseChoice.nextLine();
        String userChoiceWithoutBloat = userAxeChoice.toLowerCase().replaceAll("\\s+", "");
        switch (userChoiceWithoutBloat) {
            case "1":
            case "ironaxe":
                if (humanPlayer.getMoney() >= ironAxe.getPrice()) {
                    System.out.println("You just bought a " + ironAxe.getDescription() + "!\n"
                        + "It costs you " + ironAxe.getPrice() + " gold coins"
                        + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(ironAxe);
                } else {
                    System.out.println("YOU NEED " + ironAxe.getPrice() + " GOLD COINS TO BUY THIS. CHOPSUEY");
                }
                break;
            case "2":
            case "steelaxe":
                if (humanPlayer.getMoney() >= steelAxe.getPrice()) {
                    System.out.println("You just bought a " + steelAxe.getDescription() + "!\n"
                        + "It costs you " + steelAxe.getPrice() + " gold coins"
                        + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(steelAxe);
                } else {
                    System.out.println("YOU NEED " + steelAxe.getPrice() + " GOLD COINS TO BUY THIS. CHOPSUEY");
                }
                break;
            case "3":
            case "diamondaxe":
                if (humanPlayer.getMoney() >= diamondAxe.getPrice()) {
                    System.out.println("You just bought a " + diamondAxe.getDescription() + "!\n"
                        + "It costs you " + diamondAxe.getPrice() + " gold coins"
                        + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(diamondAxe);
                } else {
                    System.out.println("YOU NEED " + diamondAxe.getPrice() + " GOLD COINS TO BUY THIS. CHOPSUEY");
                }
                break;
            case "4":
            case "fireaxe":
                if (humanPlayer.getMoney() >= fireAxe.getPrice()) {
                    System.out.println("You just bought a " + fireAxe.getDescription() + "!\n"
                        + "It costs you " + fireAxe.getPrice() + " gold coins"
                        + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(fireAxe);
                } else {
                    System.out.println("YOU NEED " + fireAxe.getPrice() + " GOLD COINS TO BUY THIS. CHOPSUEY");
                }
                break;
            default:
                System.out.println("I don't know what you mean");
                break;
        }
    }

    @Override
    public void option3() {
        System.out.println("Mnyess! I have 4 different backpacks for you!\n"
            + smallBackPack + "\n"
            + mediumBackPack + "\n"
            + largeBackPack + "\n"
            + "which one do you want?");

        String userBackPackChoice = userPurchaseChoice.nextLine();
        String userChoiceWithoutBloat = userBackPackChoice.toLowerCase().replaceAll("\\s+", "");
        switch (userChoiceWithoutBloat) {
            case "1":
            case "smallbackpack":
                if (humanPlayer.getMoney() >= smallBackPack.getPrice()) {
                    System.out.println("You just bought a " + smallBackPack.getDescription() + "!\n"
                        + "It costs you " + smallBackPack.getPrice() + " gold coins");
                    humanPlayer.boughtBackPack(smallBackPack);
                } else {
                    System.out.println("YOU NEED " + smallBackPack.getPrice() + " GOLD COINS TO BUY THIS");
                }
                break;
            case "2":
            case "mediumbackpack":
                if (humanPlayer.getMoney() >= mediumBackPack.getPrice()) {
                    System.out.println("You just bought a " + mediumBackPack.getDescription() + "!\n"
                        + "It costs you " + mediumBackPack.getPrice() + " gold coins");
                    humanPlayer.boughtBackPack(mediumBackPack);
                } else {
                    System.out.println("YOU NEED " + mediumBackPack.getPrice() + " GOLD COINS TO BUY THIS.");
                }
                break;
            case "3":
            case "largebackpack":
                if (humanPlayer.getMoney() >= largeBackPack.getPrice()) {
                    System.out.println("You just bought a " + largeBackPack.getDescription() + "!\n"
                        + "It costs you " + largeBackPack.getPrice() + " gold coins");
                    humanPlayer.boughtBackPack(largeBackPack);
                } else {
                    System.out.println("YOU NEED " + largeBackPack.getPrice() + " GOLD COINS TO BUY THIS.");
                }
                break;
            default:
                System.out.println("I don't know what you mean");
                break;
        }

    }
}
