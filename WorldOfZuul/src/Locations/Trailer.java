package Locations;

import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.ArrayList;

public class Trailer extends Room {

    private ArrayList<Tree> amountOfLogsInStorage;
    private final static int MAX_TREESTORAGEAMOUNT = 15;

    public Trailer(String description, Player player) {
        super(description, player);
        this.amountOfLogsInStorage = new ArrayList<>();
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This is your home, you have " + humanPlayer.getClimatePoints() + " climate points,"
            + " your options are: \n"
            + "1 - Store logs you are carrying here \n"
            + "2 - Sell the logs you have in your storage \n"
            + "3 - Sleep \n"
            + getExitString();
    }

    @Override
    public void option1() {
        if (humanPlayer.getAmountOfLogsCarrying() == 0) {
            System.out.println("You are not carrying any logs!");
            return;
        }
        for (int i = 0; i < humanPlayer.getAmountOfLogsCarrying(); i++) {
            if (this.amountOfLogsInStorage.size() + 1 <= MAX_TREESTORAGEAMOUNT) {
                this.amountOfLogsInStorage.add(humanPlayer.getTreeType(i));
            } else {
                System.out.println("You have too many logs in your storage!");
                return;
            }
        }

        humanPlayer.loadLogsToStorage();
        System.out.println("You now have " + this.amountOfLogsInStorage.size()
            + (this.amountOfLogsInStorage.size() > 1 ? " logs" : " log") + " stored!");
    }

    @Override
    public void option2() {
        if (this.amountOfLogsInStorage.isEmpty()) {
            System.out.println("You have no logs in your storage to sell!");
            return;
        }
        for (Tree tree : this.amountOfLogsInStorage) {
            humanPlayer.addMoney(tree.getTreePrice());
        }
        this.amountOfLogsInStorage = new ArrayList<>();
        System.out.println("You have sold all your logs, you now have " + humanPlayer.getMoney() + " gold");
    }

    @Override
    public void option3() {
        System.out.println("The sun goes down and you sleep tight \n"
            + "ZzzzZzzzZzzzZzzz");
        System.out.println("The sun rises and you are ready to tackle the day!");
        CertifiedForest.regrowTrees();
    }

}
