package Locations;

import gameFunctionality.CertifiedTree;
import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertifiedForest extends Room {

    private final static int MAX_AMOUNTOFTREESINFOREST = 100;
    private final static int MIN_AMOUNTOFTREESINFOREST = 70;
    private static List<Tree> trees;
    private final static int FOREST_REGROW_RATE = 3;

    public CertifiedForest(String description, Player player) {
        super(description, player);
        CertifiedForest.trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new CertifiedTree());
        }
    }

    public static void regrowTrees() {
        int counter = 0;
        while (trees.size() < MAX_AMOUNTOFTREESINFOREST) {
            trees.add(new CertifiedTree());
            counter++;
            if (counter >= FOREST_REGROW_RATE) {
                break;
            }
        }
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "In this forest you can plant new trees, there currently are " + trees.size() + " trees" + "\n"
            + "ALERT If you don't seed the forest after felling trees you will be fined the next day! \n"
            + "Your options are: \n"
            + "Cut - Cut down a tree and bring it with you \n"
            + "trees left - See how many trees are left in the forest \n"
            + "Replant trees - Replant trees";
    }

    private boolean playerCanCarryMoreTree() {
        return humanPlayer.backPack().getAmountOfLogsInBackPack()
            < humanPlayer.backPack().getBackpackCapacity();
    }

    private boolean thereIsMoreTreesToCut() {
        return trees.size() > MIN_AMOUNTOFTREESINFOREST;
    }

    private Tree lastTreeInArray() {
        return trees.get(trees.size() - 1);
    }

    @Override
    public void option1() {
        if (playerCanCarryMoreTree() && thereIsMoreTreesToCut()) {
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
            System.out.println("You have cut down a tree! You are now carrying "
                + humanPlayer.backPack().getAmountOfLogsInBackPack() + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
        } else {
            if (playerCanCarryMoreTree() && !thereIsMoreTreesToCut()) {
                System.out.println("You have cut too much wood!! Wait for the trees to regrow!"); // Her skal vi overveje hvad der skal stå
            } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree()) {
                System.out.println("You are carrying too much wood! "
                    + "Go back to your trailer and sell or store your logs!");
            }
        }
    }

    @Override
    public void option2() {
        System.out.println("There are " + trees.size() + " trees left in the forest");
    }

    @Override
    public void option3() {
         {
            if (humanPlayer.getSaplingAmount() == 0) {
                System.out.println("You don't have any saplings in your backpack");
            } else {
                humanPlayer.plantSeeds();
                System.out.println("You just seeded this forest with saplings");
            }

        }
    }
}
