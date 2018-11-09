package game_locations;

import game_functionality.Player;
import game_elements.Tree;
import game_elements.BackPack;
import game_elements.Axe;
import game_elements.AxeFactory;
import game_elements.BackPackFactory;

import java.util.Scanner;

public class Store extends Room {

    private final String StoreOwner = "Reginald:\n";
    private final int SAPLING_BUNDLE_PRICE = 12;
    private final Scanner userPurchaseChoice = new Scanner(System.in);

    public Store(String description) {
        super(description);
    }

    @Override
    public String getLongDescription(Player humanPlayer) {
        return StoreOwner + "Hi " + "You are standing " + getShortDescription() + "!\n"
            + "Here you can sell your logs and purchase new equipment \n"
            + "you have 3 options \n"
            + "---------------------------------------------\n"
            + "○ Sell - Sell logs\n"
            + "○ Buy - Buy items\n"
            + "○ Go back - Go to the localvillage \n"
            + "---------------------------------------------";

    }

    private void sapling_menu(Player humanPlayer) {
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
                first_menu(humanPlayer);
            }
        } catch (NumberFormatException e) {
            System.out.println(StoreOwner + "I don't know you mean, friend");
            first_menu(humanPlayer);
        }
    }

    private void BackPack_menu(Player humanPlayer) {
        BackPack smallBackPack = BackPackFactory.createSmallBackPack();
        BackPack mediumBackPack = BackPackFactory.createMediumBackPack();
        BackPack largeBackPack = BackPackFactory.createLargeBackPack();

        System.out.println(StoreOwner + "Here you see the backpacks I can offer you! \n"
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
                    first_menu(humanPlayer);
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
                    first_menu(humanPlayer);
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
                    first_menu(humanPlayer);
                }
                break;
            default:
                System.out.println(StoreOwner + "I don't know what you mean");
                first_menu(humanPlayer);
                break;
        }
    }

    private void Axe_menu(Player humanPlayer) {

        Axe ironAxe = AxeFactory.createIronAxe();
        Axe steelAxe = AxeFactory.createSteelAxe();
        Axe diamondAxe = AxeFactory.createDiamondAxe();
        Axe fireAxe = AxeFactory.createFireAxe();

        System.out.println(StoreOwner + "You see here my good friend! Many different axes, sharp as an arrowtip.\n"
            + ironAxe + "\n"
            + steelAxe + "\n"
            + diamondAxe + "\n"
            + fireAxe);

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
                    System.out.println(StoreOwner + "YOU NEED " + ironAxe.getPrice() + " GOLD COINS TO BUY THIS AXE");
                    first_menu(humanPlayer);
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
                    first_menu(humanPlayer);
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
                    first_menu(humanPlayer);
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
                    first_menu(humanPlayer);
                }
                break;
            default:
                System.out.println(StoreOwner + "I don't know what you mean");
                first_menu(humanPlayer);
                break;
        }
    }

    private void first_menu(Player humanPlayer) {
        System.out.println(StoreOwner + "What would you like to buy?\n"
            + "--------------------------- \n"
            + "○ Axe - Buy axes \n"
            + "○ Backpack - Buy Backpacks \n"
            + "○ Sapling - Buy saplings \n"
            + "---------------------------");
        Scanner in = new Scanner(System.in);
        switch (in.nextLine()) {
            case "backpack":
            case "buy backpack":
                BackPack_menu(humanPlayer);
                break;
            case "sapling":
            case "buy sapling":
                sapling_menu(humanPlayer);
                break;
            case "axe":
            case "buy axes":
                Axe_menu(humanPlayer);
            default:
                System.out.println(StoreOwner + "I'm not sure what you mean friend");
                break;
        }
    }

    @Override
    public void option1(Player humanPlayer) {
        if (humanPlayer.getTrailer().getLogsInStorage().isEmpty() && humanPlayer.backPack().getLogsInBackPack().isEmpty()) {
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

        if (!humanPlayer.getTrailer().getLogsInStorage().isEmpty()) {
            humanPlayer.getTrailer().getLogsInStorage().forEach((tree) -> {
                humanPlayer.addMoney(tree.getTreePrice());
            });
            humanPlayer.getTrailer().loadOffLogsInStorage();
            System.out.println(StoreOwner + "You have sold all the logs in your storage!");
        }
    }

    @Override
    public void option2(Player humanPlayer) {
        first_menu(humanPlayer);

    }

}
