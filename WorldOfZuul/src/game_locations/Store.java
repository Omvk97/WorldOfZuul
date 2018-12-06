package game_locations;

import game_elements.BackPack;
import game_elements.BackPackFactory;
import game_elements.Tree;
import game_functionality.Player;
import java.io.IOException;
import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Store extends Room {

    private static final int SAPLING_PRICE = 4;
    private final String StoreOwner = "Reginald:\n";
    private final Scanner userPurchaseChoice = new Scanner(System.in);

    public Store() {
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return StoreOwner + "Hi " + "Welcome to my store!\n"
            + "Here you can sell your logs and purchase new equipment \n"
            + "You have " + humanPlayer.getMoney() + " gold coins\n";
    }

    @Override
    public String option1(Player humanPlayer) {
        if (humanPlayer.getLogsInStorage().isEmpty()
            && humanPlayer.backPack().getLogsInBackPack().isEmpty()) {
            return StoreOwner + "You have no logs to sell!";
        }
        if (!humanPlayer.backPack().getLogsInBackPack().isEmpty()) {
            for (Tree tree : humanPlayer.backPack().getLogsInBackPack()) {
                humanPlayer.addMoney(tree.getTreePrice());
            }
            humanPlayer.backPack().emptyBackpack();
        }
        if (!humanPlayer.getLogsInStorage().isEmpty()) {
            humanPlayer.getLogsInStorage().forEach((tree)
                -> humanPlayer.addMoney(tree.getTreePrice()));
            humanPlayer.loadOffLogsInStorage();
        }
        return "You have sold all your logs!\n You now have " + humanPlayer.getMoney() + " gold coins";

    }

    @Override
    public String option2(Player humanPlayer) {
        return first_menu(humanPlayer);
    }

    private String sapling_menu(Player humanPlayer) {
        System.out.println(StoreOwner + "These saplings are cheap and make your trees grow! \n"
            + "Only " + SAPLING_PRICE + " gold coins per sapling!\n"
            + "How many would you like to buy, friend?");
        String saplingAmountString = userPurchaseChoice.nextLine();
        try {
            int saplingAmountInt = Integer.parseInt(saplingAmountString);
            int saplingCost = saplingAmountInt * SAPLING_PRICE;
            if (humanPlayer.buySaplingBundle(saplingAmountInt, saplingCost)) {
                return StoreOwner + "You have bought " + saplingAmountInt
                    + " saplings for " + saplingCost + " gold coins";
            } else {
                System.out.println("You don't have enough money for that friend");
                first_menu(humanPlayer);
            }
        } catch (NumberFormatException e) {
            return ("I don't know you mean, friend");
        }
        return ("I don't know you mean, friend");
    }

    private String BackPack_menu(Player humanPlayer) {
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
            case "small":
                return getBackPackInfo(humanPlayer, smallBackPack);
            case "2":
            case "mediumbackpack":
            case "medium":
                return getBackPackInfo(humanPlayer, mediumBackPack);
            case "3":
            case "largebackpack":
            case "large":
                return getBackPackInfo(humanPlayer, largeBackPack);
            default:
                System.out.println("I don't know what you mean");
                first_menu(humanPlayer);
                return "";
        }
    }

    private String getBackPackInfo(Player humanPlayer, BackPack backPack) {
        if (humanPlayer.getMoney() >= backPack.getPrice()) {
            humanPlayer.boughtBackPack(backPack);
            return (StoreOwner + "You just bought a " + backPack.getDescription() + "!\n"
                + "It costs you " + backPack.getPrice() + " gold coins");
        } else {
            System.out.println(StoreOwner + "You don't have enough money ");
            first_menu(humanPlayer);
            return "";
        }
    }

    private String first_menu(Player humanPlayer) {
        System.out.println(StoreOwner + "What would you like to buy?\n"
            + "Your wallet contains " + humanPlayer.getMoney() + " gold coins \n"
            + "----------------------------------------------\n"
            + "○ Backpack ➤ Choose a new and better backpack \n"
            + "○ Sapling  ➤ Buy saplings to grow new trees \n"
            + "----------------------------------------------");
        String userBackPackChoice = userPurchaseChoice.nextLine();
        String userChoiceWithoutBloat = userBackPackChoice.toLowerCase().replaceAll("\\s+", "");
        switch (userChoiceWithoutBloat) {
            case "backpack":
            case "buy backpack":
                return BackPack_menu(humanPlayer);
            case "sapling":
            case "buy sapling":
                return sapling_menu(humanPlayer);
            default:
                return "";
        }
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/room_fxml/Store.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

}
