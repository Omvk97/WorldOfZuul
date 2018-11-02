package Locations;

import gameFunctionality.NonCertifiedTree;
import gameFunctionality.Player;

public class LocalVillage extends Room {

    /**
     * Der bliver her sat nogle klima scenarier op som alle er placeholders indtil spillet er færdigudviklet
     */
    private final int CLIMATESCENARIO_2 = 19;
    private final int CLIMATESCENARIO_1 = 9;
    private final int CLIMATESCENARIO1 = -19;
    private final int CLIMATESCENARIO2 = -29;
    private final int CLIMATESCENARIO3 = -39;
    private final int CLIMATESCENARIO4 = -49;
    private final int CLIMATESCENARIO5 = -59;
    private final Trailer trailer;

    public LocalVillage(String description, Player player, Trailer trailer) {
        super(description, player);
        this.trailer = trailer;
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + getScenario();
    }

    public String getScenario() {
        int climatePoints = humanPlayer.getClimatePoints();

        if (climatePoints > CLIMATESCENARIO1 && climatePoints < CLIMATESCENARIO_1) {
            return "The local people from the village greet you a kind welcome\nand you observe a "
                + "healthy and vibrant wildlife";
            
        } else if (climatePoints > CLIMATESCENARIO2 && climatePoints < CLIMATESCENARIO1) {
            return "The local people from the village greet you welcome\nand you observe "
                + "the wildlife steadily decaying";
            
        } else if (climatePoints > CLIMATESCENARIO3 && climatePoints < CLIMATESCENARIO2) {
            return "The local people from the village stopped giving you\nhospitality "
                + "and the wildlife is suffering visibly";
            
        } else if (climatePoints > CLIMATESCENARIO4 && climatePoints < CLIMATESCENARIO3) {
            humanPlayer.setCurrentRoom(trailer);
            return "You cut too much wood! The local people from the village are enraged \n"
                + "and chase you out of the village, spitting and throwing rocks after you\n"
                + "wildlife is decimated \n"
                + "You now stand in your trailer with a black eye";
            
        } else if (climatePoints > CLIMATESCENARIO5 && climatePoints < CLIMATESCENARIO4) {
            humanPlayer.setCurrentRoom(trailer);
            return "Parts of the village have left due to lacking ressources, the remainders\n"
                + "chase you out of the village with guns \n"
                + "You now stand in your trailer";
            
        } else if (climatePoints < CLIMATESCENARIO5) {
            return "The village has been forsaken and the wildlife is completely gone.\n"
                + "Why did you do this? You mindlessly chopped down trees\n"
                + "and destroyed this village";
            
            
        } else if (climatePoints > CLIMATESCENARIO_1 && climatePoints < CLIMATESCENARIO_2) {
            return "The local people from the village are happy about your"
                + " environmental considerations\nand wildlife is flourishing";
            
        } else if (climatePoints > CLIMATESCENARIO_2) {
            return giftScenario();
        }
        
        return "You successfully broke the game";
    }

    public String giftScenario() {
        if (!humanPlayer.isGiftHasBeenGivenToday()) {
            if (!trailer.isStorageFull()) {
                trailer.getLogsInStorage().add(new NonCertifiedTree());
                int moneyAmountGiven = (int) (Math.random() * 10) + 1;
                humanPlayer.addMoney(moneyAmountGiven);
                humanPlayer.giftHasBeenGiven();

                return "The villagers are very happy about your enviromental efforts\n"
                    + "and offer to donate " + moneyAmountGiven + " gold coins and 1 tree to you";
            } else {
                int moneyAmountGiven = (int) (Math.random() * 10) + 1;
                humanPlayer.addMoney(moneyAmountGiven);
                humanPlayer.giftHasBeenGiven();
                return "The villagers are very happy about your enviromental efforts\n"
                    + "and offer to donate " + moneyAmountGiven + " gold coins to you";
            }
        } else {
            return "The villagers are very happy about your envriomental efforts\n"
                + "but they don't have any more gifts for you today";
        }
    }
}
