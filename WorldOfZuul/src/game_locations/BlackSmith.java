package game_locations;


import game_elements.Axe;
import game_elements.AxeFactory;
import game_functionality.Player;

import java.util.Scanner;

public class BlackSmith extends Room {
    private final int grindPrise = 10;
    private String BlackSmith = " Smith:\n";
    private final Scanner userPurchaseChoice = new Scanner(System.in);

    public BlackSmith(String description) {
        super(description);
    }

    @Override
    public String getLongDescription(Player humanplayer) {
        return "Hi " + "You are standing " + getShortDescription() + "!\n"
                + "---------------------------------------------\n" +
                "If you pay I wil make you axe Stronger \n " +
                "------------------------------------\n" +
                "○ Repair  ➤ For repair your axe for you\n" +
                "○ Buy     ➤ For buy an axe";

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
            case "5":
            case "back":
                System.out.println("Se yo later ");
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

    private void grindAxe_menu(Player humanPlayer) {


        if (humanPlayer.getAxe() == null) {
            System.out.println("You dont have a Axe want to buy one ?");
            return;
        } else if (humanPlayer.getAxe().getDurability() == humanPlayer.getAxe().getStartDurability()) {
            System.out.println("You dont need to get your axe fixt");
        } else if (humanPlayer.getAxe().getDurability() < humanPlayer.getAxe().getStartDurability()) {
            System.out.println(BlackSmith + "I will grind you axe for you. plezz wait");
            int timeToWait = 6;
            try {
                for (int i = 0; i < timeToWait; i++) {
                    Thread.sleep(1000);
                    System.out.println("  ( ͡° ͜ʖ ͡°) Ding  ( ͡° ͜ʖ ͡°) ");
                }
                System.out.println(" (◣_◢) Your axe is done  (◣_◢)");

            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();

            }
            Scanner input = new Scanner(System.in);
            String grindOption = input.nextLine();
            switch (grindOption) {
                case "yes":
                    System.out.println(BlackSmith + "Okay my friend \n I will go to work");
                    humanPlayer.getAxe().grindAxe();
                    break;
                case "no":
                    System.out.println(BlackSmith + " Get out of her !!!!");
                    break;

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
