package Locations;

import gameFunctionality.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NonCertifiedForest extends Room {

    private final static int MAX_AMOUNTOFTREESINFOREST = 100;
    private final List<Tree> trees;

    public NonCertifiedForest(String description, Player player) {
        super(description, player);
        this.trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new NonCertifiedTree());
        }
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This forest will not regrow, there are " + trees.size() + " trees" + "\n"
            + "Your options are: %n"
            + "cut - Cut down a tree and bring it with you \n"
            + "seetrees - See how many trees are left in the forest";
    }

    private boolean playerCanCarryMoreTree() {
        return humanPlayer.backPack().getAmountOfLogsInBackPack() < 
            humanPlayer.backPack().getBackpackCapacity();
    }

    private boolean thereIsMoreTreesToCut() {
        return trees.size() > 0;
    }

    private Tree lastTreeInArray() {
        return trees.get(trees.size() - 1);
    }

    @Override
    public void option1() {
        if (playerCanCarryMoreTree() && thereIsMoreTreesToCut()) {
            if (humanPlayer.canUseAxe()) {
                System.out.println("You swing your " + humanPlayer.axe().getDescription() + " at the tree!");
                while (lastTreeInArray().getTreeHealth() - humanPlayer.axe().getDamage() > 0) {
                    lastTreeInArray().reduceTreeHealth(humanPlayer.axe().getDamage());
                    System.out.println("**CHOP**");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NonCertifiedForest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                humanPlayer.backPack().addTreeToBackpack(trees.get(0));
                humanPlayer.addClimatePoints(trees.get(0).getTreeClimatePoints());
                trees.remove(trees.size() - 1);
                System.out.println("**CHOP**");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NonCertifiedForest.class.getName()).log(Level.SEVERE, null, ex);
                }
                humanPlayer.useAxe();
                System.out.println("You have cut down a tree! You are now carrying "
                    + humanPlayer.backPack().getAmountOfLogsInBackPack()
                    + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
            } else {
                System.out.println("You don't have an axe equipped!");
            }
        } else {
            if (playerCanCarryMoreTree() && !thereIsMoreTreesToCut()) {
                System.out.println("You have cut too much wood!! The forest has no more trees!");
            } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree()) {
                System.out.println("You are carrying too much wood!\n"
                    + "Go back to your trailer and sell or store your logs!");
            }
        }

        if (humanPlayer.getClimatePoints() == Player.getMIN_CLIMATEPOINTS()) {
            System.out.println("YOU DESTROYED THE EARTH, YOU HAVE CUT WAY TOO MUCH \n"
                + "NON CERTIFIED WOOD.");
            System.exit(0);
        }
    }

    @Override
    public void option2() {
        System.out.println("There are " + trees.size() + " trees left in the forest");
    }

}
