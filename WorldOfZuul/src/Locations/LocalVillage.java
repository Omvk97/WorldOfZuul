package Locations;

import gameFunctionality.Player;

public class LocalVillage extends Room {

    private final int CLIMATESCENARIO_2 = 19;
    private final int CLIMATESCENARIO_1 = 9;
    private final int CLIMATESCENARIODEFAULT = 0;
    private final int CLIMATESCENARIO1 = -9;
    private final int CLIMATESCENARIO2 = -19;
    private final int CLIMATESCENARIO3 = -29;
    private final int CLIMATESCENARIO4 = -39;
    private final int CLIMATESCENARIO5 = -49;

    public LocalVillage(String description, Player player) {
        super(description, player);
    }

    public String setValues() {
        int climatePoints = humanPlayer.getClimatePoints();

        if (climatePoints > CLIMATESCENARIO1 && climatePoints < CLIMATESCENARIO_1) {
            return "The local people from the village greet you a kind welcome\nand you observe a "
                + "healthy and vibrant wildlife";
        } else if (climatePoints > CLIMATESCENARIO2 && climatePoints < CLIMATESCENARIO1) {
            return "The local people from the village greet you welcome\nand you observe "
                + "the wildlife steadily decaying";
        } else if (climatePoints > CLIMATESCENARIO3 && climatePoints < CLIMATESCENARIO2) {
            return "The local people from the village stopped giving you\nhospitality"
                + "and the wildlife is suffering visibly";
        } else if (climatePoints > CLIMATESCENARIO4 && climatePoints < CLIMATESCENARIO3) {
//            TODO - Player thrown back to Trailer
            return "The local people from the village are enraged and chase you out of\n the village"
                + "spitting and throwing rocks after you, wildlife is decimated";
        } else if (climatePoints > CLIMATESCENARIO5 && climatePoints < CLIMATESCENARIO4) {
//            TODO - Player thrown back to Trailer
            return "Parts of the village have left due to lacking ressources, the remainders\n"
                + "chase you out of the village with guns";
        } else if (climatePoints < CLIMATESCENARIO5) {
            return "The village has been forsaken and the wildlife is completely gone";
        } else if (climatePoints > CLIMATESCENARIO_1 && climatePoints < CLIMATESCENARIO_2) {
            return "The local people from the village are happy about your"
                + " environmental considerations\nand wildlife is flourishing";
        } else if (climatePoints > CLIMATESCENARIO_2) {
//            TODO - Villagers help chop down trees (amountOfLogsCarrying increase)
            int moneyAmountGiven = (int) (Math.random() * 10) + 1;
            humanPlayer.addMoney(moneyAmountGiven);
            return "The villagers are very happy about your efforts and offer to donate "
                + moneyAmountGiven + " gold coins to you";
        }
        return "fisk";
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + setValues() + "\n" + getExitString();
    }

}
