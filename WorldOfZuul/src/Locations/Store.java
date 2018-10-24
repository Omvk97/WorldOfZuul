package Locations;

import gameFunctionality.Player;
import gameFunctionality.Tree;

public class Store extends Room {
    private final Trailer trailer; 

    public Store(String description, Player player, Trailer trailer) {
        super(description, player);
        this.trailer = trailer;
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "Here you can sell your logs and purchase new equipment \n"
            + "Option 1 - Sell logs stored in your trailer \n"
            + "Option 2 - Buy a new axe \n"
            + "Option 3 - Increase storage space";
    }
    @Override
      public void option1() {
        if (trailer.getStorage().isEmpty()) {
            System.out.println("You have no logs in your storage to sell!");
            return;
        }
        for (Tree tree : trailer.getStorage()) {
            humanPlayer.addMoney(tree.getTreePrice());
        }
        trailer.loadOffStorage();
        System.out.println("You have sold all the logs in your storage!");
    }
    
    

//    Skal kunne købe træerne fra spilleren
//    Spilleren skal kunne købe upgrades til: storage, treeCarry, økse
//    
    

    
}