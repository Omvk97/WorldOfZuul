package game_locations;

import game_elements.NonCertifiedTree;
import game_functionality.Player;

public class LocalVillage extends Room {

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

        if (climatePoints < this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints > this.getNEGATIVE_SCENARIO_POINTS()[0]) {
            return "The local people from the village greet you a kind welcome\nand you observe a "
                + "healthy and vibrant wildlife";

        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[0] && climatePoints > 
                this.getNEGATIVE_SCENARIO_POINTS()[1]) {
            return "The local people from the village greet you welcome\nand you observe "
                + "the wildlife steadily decaying";

        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[1] && climatePoints > this.getNEGATIVE_SCENARIO_POINTS()[2]) {
            return "The local people from the village stopped giving you\nhospitality "
                + "and the wildlife is suffering visibly";

        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[2] && climatePoints > this.getNEGATIVE_SCENARIO_POINTS()[3]) {
            humanPlayer.setCurrentRoom(trailer);
            return "You cut too much wood! The local people from the village are enraged \n"
                + "and chase you out of the village, spitting and throwing rocks after you\n"
                + "wildlife is decimated \n"
                + "You now stand in your trailer with a black eye";

        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[3] && climatePoints > this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            humanPlayer.setCurrentRoom(trailer);
            return "Parts of the village have left due to lacking ressources, the remainders\n"
                + "chase you out of the village with guns \n"
                + "You now stand in your trailer";

        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            return "The village has been forsaken and the wildlife is completely gone.\n"
                + "Why did you do this? You mindlessly chopped down trees\n"
                + "and destroyed this village";

        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints < this.getPOSITIVE_SCENARIO_POINTS()[1]) {
            return "The local people from the village are happy about your"
                + " environmental considerations\nand wildlife is flourishing";

        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[1]) {
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
