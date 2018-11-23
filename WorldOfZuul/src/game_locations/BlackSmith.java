package game_locations;

import game_elements.Axe;
import game_elements.AxeFactory;
import game_functionality.Player;
import java.io.IOException;

import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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

    private void Axe_menu(Player humanPlayer, Label textArea) {
        Axe ironAxe = AxeFactory.createIronAxe();
        Axe steelAxe = AxeFactory.createSteelAxe();
        Axe diamondAxe = AxeFactory.createDiamondAxe();
        Axe fireAxe = AxeFactory.createFireAxe();
        textArea.setText(blackSmith + "I have many different axes, all "
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
                getAxeInfo(humanPlayer, ironAxe, textArea);
                break;
            case "2":
            case "steelaxe":
                getAxeInfo(humanPlayer, steelAxe, textArea);
                break;
            case "3":
            case "diamondaxe":
                getAxeInfo(humanPlayer, diamondAxe, textArea);
                break;
            case "4":
            case "fireaxe":
                getAxeInfo(humanPlayer, fireAxe, textArea);
                break;
            default:
                break;
        }
    }

    private void getAxeInfo(Player humanPlayer, Axe axe, Label textArea) {
        if (humanPlayer.getMoney() >= axe.getPrice()) {
            textArea.setText(blackSmith + "You just bought a " + axe.getDescription() + "!\n"
                + "It costs you " + axe.getPrice() + " gold coins"
                + "\nEnjoy it while it lasts!");
            humanPlayer.boughtAxe(axe);
        } else {
            textArea.setText(blackSmith + "YOU NEED " + axe.getPrice() + " GOLD COINS TO BUY THIS AXE");
        }
    }

    private void grindAxe_menu(Player humanPlayer, Label textArea) {
        final int pricePerAxeDurability = 2;

        if (humanPlayer.getAxe() == null) {
            textArea.setText(blackSmith + "You don't have an axe equipped");

        } else if (humanPlayer.getAxe().getDurability() == humanPlayer.getAxe().getStartDurability()) {
            textArea.setText("Your axe is fine! Come back if it ever gets dull");

        } else if (humanPlayer.getAxe().getDurability() < humanPlayer.getAxe().getStartDurability()) {
            int durabilityLostOnAxe = humanPlayer.getAxe().getStartDurability() - humanPlayer.getAxe().getDurability();
            int fixAxePrice = pricePerAxeDurability * durabilityLostOnAxe;
            if (humanPlayer.getMoney() >= fixAxePrice) {
                textArea.setText(blackSmith + "I will grind your axe for you. Please wait");
                int timeToWait = 6;
                try {
                    for (int i = 0; i < timeToWait; i++) {
                        Thread.sleep(1000);
                        textArea.setText("**Ding**");
                    }
                    humanPlayer.grindedAxe(fixAxePrice);
                    textArea.setText("Your axe is done");
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } else {
                textArea.setText("You do not have enough money");
            }
        }
    }

    @Override
    public void option1(Player humanPlayer, Label textArea) {
        grindAxe_menu(humanPlayer, textArea);
    }

    @Override
    public void option2(Player humanPlayer, Label textArea) {
        Axe_menu(humanPlayer, textArea);
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
