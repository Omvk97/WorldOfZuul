package Locations;

import gameFunctionality.Tree;

public class Trailer extends Room {

    int amountOfLogsInStorage;
    final int MAX_TREESTORAGEAMOUNT = 50;
    int climatePoints;
    final int MAX_CLIMATEPOINTS = 50;

    public Trailer(String description) {
        super(description);
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This is your home, your options are: \n"
            + "1 - Store logs you are carrying here \n"
            + "2 - Sell the logs you have in your storage \n"
            + "3 - Sleep \n"
            + getExitString();
    }

    @Override
    public void option1() {
        if (player.getAmountOfLogsCarrying() == 0) {
            System.out.println("You are not carrying any logs!");
            return;
        }

        amountOfLogsInStorage += player.getAmountOfLogsCarrying();
        player.loadLogsToStorage();
        System.out.println("You now have " + amountOfLogsInStorage
            + (amountOfLogsInStorage > 1 ? " logs" : " log") + " stored!");
    }

    @Override
    public void option2() {

    }

    @Override
    public void option3() {
        System.out.println("The sun goes down and you sleep tight \n"
            + "ZzzzZzzzZzzzZzzzZzzzZzzzZzzzZzzzZzzz");
        System.out.println("The sun rises and you are ready to tackle the day!");
        int counter = 0; // der skal bruges nogle mere officielle tal her
        while (CertifiedForest.trees.size() < 100) { // der skal bruges nogle mere officielle tal her
            CertifiedForest.trees.add(new Tree());
            counter++;
            if (counter >= 3) { // der skal bruges nogle mere officielle tal her
                break;
            }
        }
    }

}
