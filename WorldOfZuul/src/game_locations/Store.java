package game_locations;

import game_elements.BackPack;
import game_elements.BackPackFactory;
import game_elements.Tree;
import game_functionality.Player;

import java.util.Scanner;

public class Store extends Room {
    private static final int SAPLING_BUNDLE_PRICE = 10 ;
    private final String StoreOwner = "Reginald:\n";
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
                + "○ Sell    ➤ Sell logs\n"
                + "○ Buy     ➤ Buy items\n"
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
                getBackPackInfo(humanPlayer, smallBackPack);
                break;
            case "2":
            case "mediumbackpack":
                getBackPackInfo(humanPlayer, mediumBackPack);
                break;
            case "3":
            case "largebackpack":
                getBackPackInfo(humanPlayer, largeBackPack);
                break;
            default:
                System.out.println(StoreOwner + "I don't know what you mean");
                first_menu(humanPlayer);
                break;
        }
    }
    private void getBackPackInfo(Player humanPlayer, BackPack backPack) {
        if (humanPlayer.getMoney() >= backPack.getPrice()) {
            System.out.println(StoreOwner + "You just bought a " + backPack.getDescription() + "!\n"
                + "It costs you " + backPack.getPrice() + " gold coins");
            humanPlayer.boughtBackPack(backPack);
        } else {
            System.out.println(StoreOwner + "YOU NEED " + backPack.getPrice() + " GOLD COINS TO BUY THIS.");
            first_menu(humanPlayer);
        }
    }
    private void first_menu(Player humanPlayer) {
        System.out.println(StoreOwner + "What would you like to buy?\n"
            + "--------------------------- \n"
                + "○ Backpack ➨ For buy Backpacks \n"
                + "○ Sapling  ➨ For buy saplings \n"
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
            humanPlayer.getTrailer().getLogsInStorage().forEach((tree) -> humanPlayer.addMoney(tree.getTreePrice()));
            humanPlayer.getTrailer().loadOffLogsInStorage();
            System.out.println(StoreOwner + "You have sold all the logs in your storage!");
        }
    }
    @Override
    public void option2(Player humanPlayer) {
        first_menu(humanPlayer);

    }

}
