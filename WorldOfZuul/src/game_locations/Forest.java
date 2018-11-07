package game_locations;

import game_functionality.Player;
import game_elements.Tree;
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

    public Forest(String description, Player player) {
        super(description, player);
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

    protected void chopWood() {
        if (humanPlayer.getAxe() != null) {
            chopWoodWithAxe();
        } else {
            chopWoodWithHands();
        }
    }

    /**
     * hugger træ, tilføjer træet til spillerens rygsæk, fjerner træet fra skoven og giver spilleren klima points.
     */
    protected void chopWoodWithAxe() {
        if (playerCanCarryMoreTree() && thereIsMoreTreesToCut()) {
            System.out.println("You swing your " + humanPlayer.getAxe().getDescription() + " at the tree!");
            while (lastTreeInArray().getTreeHealth() - humanPlayer.getAxe().getDamage() > 0) {
                lastTreeInArray().reduceTreeHealth(humanPlayer.getAxe().getDamage());
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

    /**
     * If player doesn't have an getAxe equipped they can instead use their hands to chop down a tree with a damage of 2
     */
    protected void chopWoodWithHands() {
        if (playerCanCarryMoreTree() && thereIsMoreTreesToCut()) {
            System.out.println("You punch the tree!");
            while (lastTreeInArray().getTreeHealth() - 2 > 0) {
                lastTreeInArray().reduceTreeHealth(2);
                System.out.println("**POW**");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NonCertifiedForest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            humanPlayer.backPack().addTreeToBackpack(trees.get(0));
            humanPlayer.addClimatePoints(trees.get(0).getTreeClimatePoints());
            trees.remove(trees.size() - 1);
            System.out.println("**POW**");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(NonCertifiedForest.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("You have punched down a tree! You are now carrying "
                + humanPlayer.backPack().getAmountOfLogsInBackPack()
                + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
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