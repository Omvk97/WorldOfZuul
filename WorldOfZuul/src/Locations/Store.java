package Locations;

import gameFunctionality.*;

public class Store extends Room {

    private final Trailer trailer;
    private final Axe axe;

    public Store(String description, Player player, Trailer trailer, Axe axe) {
        super(description, player);
        this.trailer = trailer;
        this.axe = axe;
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "Here you can sell your logs and purchase new equipment \n"
            + "Option 1 - Sell logs stored in your trailer \n"
            + "Option 2 - Buy an axe or refresh your old axe \n"
            + "Option 3 - Buy a truck";
    }

    @Override
    public void option1() {
        if (trailer.getLogsInStorage().isEmpty()) {
            System.out.println("You have no logs in your storage to sell!");
            return;
        }
        for (Tree tree : trailer.getLogsInStorage()) {
            humanPlayer.addMoney(tree.getTreePrice());
        }
        trailer.loadOffLogsInStorage();
        System.out.println("You have sold all the logs in your storage!");
    }

    @Override
    public void option2() {
        if (humanPlayer.getMoney() >= axe.getPrice()) {
            System.out.println("You just bought a " + axe.getDescription() + "!\n"
                + "It costs you " + axe.getPrice() + " gold coins"
                + "\nEnjoy it while it lasts!");
            humanPlayer.boughtAxe(axe.getDamage(), axe.getPrice());
        } else {
            System.out.println("YOU NEED " + axe.getPrice() + " GOLD COINS TO BUY THIS. CHOPSUEY");
        }
    }
}
