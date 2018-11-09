package game_locations;

import game_functionality.Player;
import game_elements.Tree;
import game_elements.BackPack;
import game_elements.Axe;
import game_elements.AxeFactory;
import game_elements.BackPackFactory;

import java.util.Scanner;

public class Store extends Room {

    private String StoreOwner = "Reginald: ";
    private final int SAPLING_BUNDLE_PRICE = 12;
    private final Scanner userPurchaseChoice = new Scanner(System.in);

    public Store(String description) {
        super(description);
    }
    @Override
    public String getLongDescription() {
        return StoreOwner + "Hi " + "You are standing " + getShortDescription() + "!\n"
                + "Here you can sell your logs and purchase new equipment \n"
                + " you have 3 options \n"
                + "---------------------------------------------\n"
                + "○ Sell - Sell logs\n"
                + "○ Buy - Buy items\n"
                + "○ go back - to get to the localvillage \n"
                + "---------------------------------------------";

    }
    private void sapling_menu() {
        System.out.println(StoreOwner + "you can now Buy saplings");
        System.out.println(StoreOwner + "Yes, you see here my friend, these saplings are cheap \n and make your trees grow quickly! \n"
                + "Only " + SAPLING_BUNDLE_PRICE + " gold coins per bundle!\n"
                + "How many bundles would you like to buy, friend?");
        String saplingAmountString = userPurchaseChoice.nextLine();
        try {
            int saplingAmountInt = Integer.parseInt(saplingAmountString);
            int saplingCost = saplingAmountInt * SAPLING_BUNDLE_PRICE;
            if (humanPlayer.buySaplingBundle(saplingAmountInt, saplingCost)) {
                System.out.println(StoreOwner + "You have bought " + saplingAmountInt + " for " + saplingCost + " gold coins \n"
                        + "You have " + humanPlayer.getMoney() + " gold coins left");
            } else {
                System.out.println(StoreOwner + "You don't have enough money for that friend");

            }
        } catch (NumberFormatException e) {
            System.out.println(StoreOwner + "I don't know you mean, friend");
        }
    }
    private void BackPack_menu() {
        BackPack smallBackPack = BackPackFactory.createSmallBackPack();
        BackPack mediumBackPack = BackPackFactory.createMediumBackPack();
        BackPack largeBackPack = BackPackFactory.createLargeBackPack();
        System.out.println(StoreOwner + "you can look at my Backpacks");
        System.out.println(StoreOwner + "Mnyess! I have 4 different backpacks for you!\n"
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
                    System.out.println(StoreOwner + "You just bought a " + smallBackPack.getDescription() + "!\n"
                            + "It costs you " + smallBackPack.getPrice() + " gold coins");
                    humanPlayer.boughtBackPack(smallBackPack);
                } else {
                    System.out.println(StoreOwner + "YOU NEED " + smallBackPack.getPrice() + " GOLD COINS TO BUY THIS");
                }
                break;
            case "2":
            case "mediumbackpack":
                if (humanPlayer.getMoney() >= mediumBackPack.getPrice()) {
                    System.out.println(StoreOwner + "You just bought a " + mediumBackPack.getDescription() + "!\n"
                            + "It costs you " + mediumBackPack.getPrice() + " gold coins");
                    humanPlayer.boughtBackPack(mediumBackPack);
                } else {
                    System.out.println(StoreOwner + "YOU NEED " + mediumBackPack.getPrice() + " GOLD COINS TO BUY THIS.");
                }
                break;
            case "3":
            case "largebackpack":
                if (humanPlayer.getMoney() >= largeBackPack.getPrice()) {
                    System.out.println(StoreOwner + "You just bought a " + largeBackPack.getDescription() + "!\n"
                            + "It costs you " + largeBackPack.getPrice() + " gold coins");
                    humanPlayer.boughtBackPack(largeBackPack);
                } else {
                    System.out.println(StoreOwner + "YOU NEED " + largeBackPack.getPrice() + " GOLD COINS TO BUY THIS.");
                }
                break;
            default:
                System.out.println(StoreOwner + "I don't know what you mean");
                break;
        }
    }
    private void Axe_menu() {
        Axe ironAxe = AxeFactory.createIronAxe();
        Axe steelAxe = AxeFactory.createSteelAxe();
        Axe diamondAxe = AxeFactory.createDiamondAxe();
        Axe fireAxe = AxeFactory.createFireAxe();
        System.out.println(StoreOwner + "you Can now Buy Axes");
        System.out.println(StoreOwner + "You see here my good friend! 4 different axes, sharp as an arrowtip.\n"
                + ironAxe + "\n"
                + steelAxe + "\n"
                + diamondAxe + "\n"
                + fireAxe);

        System.out.println(StoreOwner + "Which axe would you like to buy?");
        String userAxeChoice = userPurchaseChoice.nextLine();
        String userChoiceWithoutBloat = userAxeChoice.toLowerCase().replaceAll("\\s+", "");
        switch (userChoiceWithoutBloat) {
            case "1":
            case "ironaxe":
                if (humanPlayer.getMoney() >= ironAxe.getPrice()) {
                    System.out.println(StoreOwner + "You just bought a " + ironAxe.getDescription() + "!\n"
                            + "It costs you " + ironAxe.getPrice() + " gold coins"
                            + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(ironAxe);
                } else {
                    System.out.println(StoreOwner + "YOU NEED " + ironAxe.getPrice() + " GOLD COINS TO BUY THIS AXE");
                }
                break;
            case "2":
            case "steelaxe":
                if (humanPlayer.getMoney() >= steelAxe.getPrice()) {
                    System.out.println(StoreOwner + "You just bought a " + steelAxe.getDescription() + "!\n"
                            + "It costs you " + steelAxe.getPrice() + " gold coins"
                            + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(steelAxe);
                } else {
                    System.out.println(StoreOwner + "YOU NEED " + steelAxe.getPrice() + " GOLD COINS TO BUY THIS AXE");
                }
                break;
            case "3":
            case "diamondaxe":
                if (humanPlayer.getMoney() >= diamondAxe.getPrice()) {
                    System.out.println(StoreOwner + "You just bought a " + diamondAxe.getDescription() + "!\n"
                            + "It costs you " + diamondAxe.getPrice() + " gold coins"
                            + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(diamondAxe);
                } else {
                    System.out.println(StoreOwner + "YOU NEED " + diamondAxe.getPrice() + " GOLD COINS TO BUY THIS AXE");
                }
                break;
            case "4":
            case "fireaxe":
                if (humanPlayer.getMoney() >= fireAxe.getPrice()) {
                    System.out.println(StoreOwner + "You just bought a " + fireAxe.getDescription() + "!\n"
                            + "It costs you " + fireAxe.getPrice() + " gold coins"
                            + "\nEnjoy it while it lasts!");
                    humanPlayer.boughtAxe(fireAxe);
                } else {
                    System.out.println(StoreOwner + "YOU NEED " + fireAxe.getPrice() + " GOLD COINS TO BUY THIS AXE");
                }
                break;
            default:
                System.out.println(StoreOwner + "I don't know what you mean");
                break;
        }
    }


    private void first_menu() {
        System.out.println(StoreOwner + " you choose to buy \n " + " we have Axe or sapling and backpack \n");
        System.out.println("---------------------------");
        System.out.println("○ axe - for buying axe");
        System.out.println("○ backpack - for buying Backpacks");
        System.out.println("○ sapling - for buying saplings");
        System.out.println("---------------------------");
        Scanner in = new Scanner(System.in);
        switch (in.nextLine()) {
            case "backpack":
                System.out.println(StoreOwner + "You picked Backpack menu ");
                BackPack_menu();
                break;
            case "sapling":
                System.out.println(StoreOwner + "You picked sapling menu");
                sapling_menu();
                break;
            case "axe":
                System.out.println(StoreOwner + "You picked Axe menu");
                Axe_menu();
            case "back":
                first_menu();
                break;
            default:
                System.out.println(StoreOwner + "you are back");
                break;
        }
    }

    /**
     * Denne metode benytter sig af information fra 'trailer' og 'player' Den sælger træerne som
     * spilleren enten går rundt med, eller træer som spilleren har i sit storage. Eller begge dele
     * på en gang.
     */
    @Override
    public void option1() {
        if (trailer.getLogsInStorage().isEmpty() && humanPlayer.backPack().getLogsInBackPack().isEmpty()) {
            System.out.println(StoreOwner + "You have no logs to sell!");
            return;
        }
        if (!humanPlayer.backPack().getLogsInBackPack().isEmpty()) {
            for (Tree tree : humanPlayer.backPack().getLogsInBackPack()) {
                humanPlayer.addMoney(tree.getTreePrice());

            }
            humanPlayer.backPack().emptyBackpack();
            System.out.println(StoreOwner + "You have sold all the logs in your backpack!");
        }

        if (!trailer.getLogsInStorage().isEmpty()) {
            trailer.getLogsInStorage().forEach((tree) -> {
                humanPlayer.addMoney(tree.getTreePrice());
            });
            trailer.loadOffLogsInStorage();
            System.out.println(StoreOwner + "You have sold all the logs in your storage!");
        }
    }

    @Override
    public void option2() {
        first_menu();

    }


}
