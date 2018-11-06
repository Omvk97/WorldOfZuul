package Locations;

import gameFunctionality.CertifiedTree;
import gameFunctionality.Player;
import java.util.ArrayList;

/**
 * Denne skov er anderledes fra en normal skov da man ikke kan blive ved med at fælde træer
 * Derudover gror træerne i skoven tilbage efterhånden som spilleren sover.
 *
 * @author oliver
 */
public class CertifiedForest extends Forest {

    private final static int MIN_AMOUNTOFTREESINFOREST = 85;
    private final static int FOREST_REGROW_RATE = 3;

    public CertifiedForest(String description) {
        super(description);
        trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new CertifiedTree());
        }
    }

    public void regrowTrees() {
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
            + "Option 1 - Cut down a tree and bring it with you \n"
            + "Option 2 - See how many trees are left in the forest \n"
            + "Option 3 - Replant trees";
    }

    @Override
    protected boolean thereIsMoreTreesToCut() {
        return trees.size() > 0 && trees.size() > MIN_AMOUNTOFTREESINFOREST;
    }

    @Override
    public void option1(Player humanPlayer) {
        chopWood(humanPlayer);
        humanPlayer.setHasChoppedTreesInCertifiedForest();
    }

    @Override
    public void option2(Player humanPlayer) {
        System.out.println("There are " + trees.size() + " trees left in the forest");
    }

    @Override
    public void option3(Player humanPlayer) {
        if (humanPlayer.getSaplingAmount() == 0) {
            System.out.println("You don't have any saplings in your backpack");
        } else {
            humanPlayer.plantSeeds();
            System.out.println("You just seeded this forest with saplings");
        }
    }

}
