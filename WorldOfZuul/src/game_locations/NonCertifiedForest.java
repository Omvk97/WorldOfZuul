package game_locations;

import game_elements.NonCertifiedTree;
import game_functionality.Player;

import java.util.ArrayList;

public class NonCertifiedForest extends Forest {

    public NonCertifiedForest() {
        trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new NonCertifiedTree(12));
        }
    }

    @Override
    public String getLongDescription(Player humanPlayer) {
        return "You are standing in a non certified forest! \n"
            + "This forest will not regrow, there are " + trees.size() + " trees" + "\n"
            + "Your options are: \n"
            + "-----------------------------------------------------------\n"
            + "○ Chop Tree   ➤ Cut down a tree and bring it with you \n"
            + "○ Trees left  ➤ See how many trees are left in the forest\n"
            + "-----------------------------------------------------------";
    }

    @Override
    public void option1(Player humanPlayer) {
        this.chopWood(humanPlayer);
        if (humanPlayer.getClimatePoints() == Player.getMIN_CLIMATEPOINTS()) {
            System.out.println("YOU DESTROYED THE EARTH, YOU HAVE CUT WAY TOO MUCH \n"
                + "NON CERTIFIED WOOD.");
            System.exit(0);
        }
    }
}
