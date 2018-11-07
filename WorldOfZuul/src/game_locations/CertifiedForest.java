package game_locations;

import game_elements.CertifiedTree;
import game_functionality.Player;
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

    public CertifiedForest(String description, Player player) {
        super(description, player);
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
            + "----------------------------------\n"
            + "○ Chop Tree - Cut down a tree and bring it with you \n"
            + "○ Trees left - See how many trees are left in the forest \n"
            + "○ Replant trees - Replant trees\n"
            + "----------------------------------\n";
    }

    @Override
    protected boolean thereIsMoreTreesToCut() {
        return trees.size() > 0 && trees.size() > MIN_AMOUNTOFTREESINFOREST;
    }

    @Override
    public void option1() {
        chopWood();
        humanPlayer.setHasChoppedTreesInCertifiedForest();
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