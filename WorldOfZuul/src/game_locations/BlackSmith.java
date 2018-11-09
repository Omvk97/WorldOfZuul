package game_locations;


import game_elements.Axe;
import game_elements.AxeFactory;
import game_functionality.Player;

import java.util.Scanner;

public class BlackSmith extends Room {
    private String BlackSmith= " Smith";
    private final Scanner userPurchaseChoice = new Scanner(System.in);

    public BlackSmith(String description) {
        super(description);
    }

    @Override
    public String getLongDescription(Player humanplayer) {
        return "Hi " + "You are standing " + getShortDescription() + "!\n"
                + "---------------------------------------------";

    }
    private void Axe_menu(Player humanPlayer) {

        Axe ironAxe = AxeFactory.createIronAxe();
        Axe steelAxe = AxeFactory.createSteelAxe();
        Axe diamondAxe = AxeFactory.createDiamondAxe();
        Axe fireAxe = AxeFactory.createFireAxe();

        System.out.println(BlackSmith + "You see here my good friend! Many different axes, sharp as an arrowtip.\n"
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
                System.out.println(BlackSmith + "I don't know what you mean");
                break;
        }
    }

    private void getAxeInfo(Player humanPlayer, Axe axe) {
        if (humanPlayer.getMoney() >= axe.getPrice()) {
            System.out.println(BlackSmith + "You just bought a " + axe.getDescription() + "!\n"
                    + "It costs you " + axe.getPrice() + " gold coins"
                    + "\nEnjoy it while it lasts!");
            humanPlayer.boughtAxe(axe);
        } else {
            System.out.println(BlackSmith + "YOU NEED " + axe.getPrice() + " GOLD COINS TO BUY THIS AXE");

        }
    }

    @Override
    public void option1(Player humanPlayer) {


    }

    @Override
    public void option2(Player humanPlayer) {
    Axe_menu(humanPlayer);

        }
}
