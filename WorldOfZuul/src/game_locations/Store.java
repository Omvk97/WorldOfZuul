package game_locations;

import game_elements.BackPack;
import game_elements.BackPackFactory;
import game_elements.Tree;
import game_functionality.Player;
import java.io.IOException;

import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
            + "You have " + humanPlayer.getMoney() + " gold coins\n"
            + "----------------------------------------------------\n"
            + "○ Sell logs ➤ Sell logs you are carrying and stored\n"
            + "○ Buy items ➤ Buy backpacks & saplings\n"
            + "----------------------------------------------------";
    }

    @Override
    public void option1(Player humanPlayer, Label textArea) {
        if (humanPlayer.getLogsInStorage().isEmpty()
            && humanPlayer.backPack().getLogsInBackPack().isEmpty()) {
            textArea.setText(StoreOwner + "You have no logs to sell!");
            return;
        }
        if (!humanPlayer.backPack().getLogsInBackPack().isEmpty()) {
            for (Tree tree : humanPlayer.backPack().getLogsInBackPack()) {
                humanPlayer.addMoney(tree.getTreePrice());
            }
            humanPlayer.backPack().emptyBackpack();
            textArea.setText("You have sold all the logs in your backpack!\n");

        }
        if (!humanPlayer.getLogsInStorage().isEmpty()) {
            humanPlayer.getLogsInStorage().forEach((tree)
                -> humanPlayer.addMoney(tree.getTreePrice()));
            humanPlayer.loadOffLogsInStorage();
            textArea.setText("You have sold all the logs in your storage!");
        }
        textArea.setText("You now have " + humanPlayer.getMoney() + " gold coins");

    }

    @Override
    public void option2(Player humanPlayer, Label textArea) {
        first_menu(humanPlayer, textArea);
    }

    private void sapling_menu(Player humanPlayer, Label textArea) {
        textArea.setText(StoreOwner + "These saplings are cheap and make your trees grow! \n"
            + "Only " + SAPLING_PRICE + " gold coins per sapling!\n"
            + "How many would you like to buy, friend?");
        String saplingAmountString = userPurchaseChoice.nextLine();
        try {
            int saplingAmountInt = Integer.parseInt(saplingAmountString);
            int saplingCost = saplingAmountInt * SAPLING_PRICE;
            if (humanPlayer.buySaplingBundle(saplingAmountInt, saplingCost)) {
                textArea.setText(StoreOwner + "You have bought " + saplingAmountInt
                    + " saplings for " + saplingCost + " gold coins");
            } else {
                textArea.setText("You don't have enough money for that friend");
                first_menu(humanPlayer, textArea);
            }
        } catch (NumberFormatException e) {
            textArea.setText("I don't know you mean, friend");
            first_menu(humanPlayer, textArea);
        }
    }

    private void BackPack_menu(Player humanPlayer, Label textArea) {
        BackPack smallBackPack = BackPackFactory.createSmallBackPack();
        BackPack mediumBackPack = BackPackFactory.createMediumBackPack();
        BackPack largeBackPack = BackPackFactory.createLargeBackPack();

        textArea.setText(StoreOwner + "Here you see the backpacks I can offer you! \n"
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
                getBackPackInfo(humanPlayer, smallBackPack, textArea);
                break;
            case "2":
            case "mediumbackpack":
            case "medium":
                getBackPackInfo(humanPlayer, mediumBackPack, textArea);
                break;
            case "3":
            case "largebackpack":
            case "large":
                getBackPackInfo(humanPlayer, largeBackPack, textArea);
                break;
            default:
                textArea.setText("I don't know what you mean");
                first_menu(humanPlayer, textArea);
                break;
        }
    }

    private void getBackPackInfo(Player humanPlayer, BackPack backPack, Label textArea) {
        if (humanPlayer.getMoney() >= backPack.getPrice()) {
            textArea.setText(StoreOwner + "You just bought a " + backPack.getDescription() + "!\n"
                + "It costs you " + backPack.getPrice() + " gold coins");
            humanPlayer.boughtBackPack(backPack);
        } else {
            textArea.setText(StoreOwner + "You don't have enough money ");
            first_menu(humanPlayer, textArea);
        }
    }

    private void first_menu(Player humanPlayer, Label textArea) {
        textArea.setText(StoreOwner + "What would you like to buy?\n"
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
                BackPack_menu(humanPlayer, textArea);
                break;
            case "sapling":
            case "buy sapling":
                sapling_menu(humanPlayer, textArea);
                break;
            default:
                break;
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
