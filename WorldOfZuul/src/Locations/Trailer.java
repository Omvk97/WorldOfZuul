package Locations;

import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.ArrayList;

public class Trailer extends Room {

    private ArrayList<Tree> amountOfLogsInStorage;
    private final static int MAX_TREESTORAGEAMOUNT = 5;

    public Trailer(String description, Player player) {
        super(description, player);
        this.amountOfLogsInStorage = new ArrayList();
    }
    
    public ArrayList<Tree> getStorage() {
        return this.amountOfLogsInStorage;
    }

    public boolean isStorageFull() {
        return amountOfLogsInStorage.size() == MAX_TREESTORAGEAMOUNT;
    }
    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This is your home, you have " + humanPlayer.getClimatePoints() + " climate points,"
            + " your options are: \n"
            + "1 - Store logs you are carrying here \n"
            + "2 - Sleep \n"
            + getExitString();
    }
    
    public void loadOffStorage() {
        amountOfLogsInStorage = new ArrayList();
    }

    @Override
    public void option1() {
        if (humanPlayer.getAmountOfLogsCarrying() == 0) {
            System.out.println("You are not carrying any logs!");
            return;
        }
        for (int i = 0; i < humanPlayer.getAmountOfLogsCarrying(); i++) {
            if (this.amountOfLogsInStorage.size() < MAX_TREESTORAGEAMOUNT) {
                this.amountOfLogsInStorage.add(humanPlayer.getTreeType(i));
                humanPlayer.decreaseAmountOfTreeCarrying();
            } else {
                System.out.println("You have too many logs in your storage!");
                break;
            }
        }

        System.out.println("You now have " + this.amountOfLogsInStorage.size()
            + (this.amountOfLogsInStorage.size() > 1 ? " logs" : " log") + " stored!");
    }
    

  

    @Override
    public void option2() {
//        System.out.println("The sun goes down and you sleep tight \n"
//            + "ZzzzZzzzZzzzZzzz");
//        System.out.println("The sun rises and you are ready to tackle the day!");
//        CertifiedForest.regrowTrees();
        System.out.println(humanPlayer.getMoney());
    }

}
