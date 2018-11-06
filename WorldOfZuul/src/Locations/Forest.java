package Locations;

import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * abstract klasse som de to skove kan arve fra for at mindske duplikation af kode.
 *
 * @author oliver
 */
public abstract class Forest extends Room {

    protected final static int MAX_AMOUNTOFTREESINFOREST = 100;
    protected List<Tree> trees = null;

    public Forest(String description) {
        super(description);
    }

    protected boolean playerCanCarryMoreTree() {
        return humanPlayer.backPack().getAmountOfLogsInBackPack()
            < humanPlayer.backPack().getBackpackCapacity();
    }

    protected boolean thereIsMoreTreesToCut() {
        return trees.size() > 0;
    }

    protected Tree lastTreeInArray() {
        return trees.get(trees.size() - 1);
    }

    /**
     * hugger træ, tilføjer træet til spillerens rygsæk, fjerner træet fra skoven og giver spilleren
     * klima points.
     */
    protected void chopWood() {
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
                System.out.println("You have cut too much wood!! \n"
                    + (this instanceof CertifiedForest ? "You have to wait for the forest to regrow!"
                        : "The forest has no more trees!"));
            } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree()) {
                System.out.println("You are carrying too much wood!\n"
                    + "Go back to your trailer and sell or store your logs!");
            }
        }
    }

    @Override
    public void option2() {
        System.out.println("There are " + trees.size() + " trees left in the forest");
    }
}
