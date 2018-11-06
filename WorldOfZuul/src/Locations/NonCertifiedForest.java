package Locations;

import gameFunctionality.*;
import java.util.ArrayList;

/**
 * Denne klasse minder meget om superklassen, bortset fra at den tjekker om man har opnået for mange
 * klimapoint hver gang man fælder et træ, hvis dette er tilfældet så sluttes spillet.
 *
 * @author oliver
 */
public class NonCertifiedForest extends Forest {

    public NonCertifiedForest(String description, Player player) {
        super(description, player);
        trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new NonCertifiedTree());
        }
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This forest will not regrow, there are " + trees.size() + " trees" + "\n"
            + "Your options are: \n"
            + "----------------------------------\n"
            + "○ Chop Tree - Cut down a tree and bring it with you \n"
            + "○ See tree - See how many trees are left in the forest\n"
            + "----------------------------------\n";
    }

    @Override
    public void option1() {
        this.chopWood();
        if (humanPlayer.getClimatePoints() == Player.getMIN_CLIMATEPOINTS()) {
            System.out.println("YOU DESTROYED THE EARTH, YOU HAVE CUT WAY TOO MUCH \n"
                + "NON CERTIFIED WOOD.");
            System.exit(0);
        }
    }
}
