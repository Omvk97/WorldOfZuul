package domain_layer.game_functionality;

import domain_layer.game_locations.Room;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerInteraction {

    private final int MIN_CLIMATEPOINTS = -250;
    private boolean giftHasBeenGivenToday, axePickedUp, slept;
    private Room currentRoom = null;
    private Room previousRoom;
    private int numChoppedTreesWithoutPlantingSaplings;
    private String playerDirectionInWorld = "noDirection";
    private final SimpleIntegerProperty equippedAxeChange = new SimpleIntegerProperty();
    private static final PlayerInteraction instance = new PlayerInteraction();

    private PlayerInteraction() {
    }

    public static PlayerInteraction getInstanceOfSelf() {
        return instance;
    }

    /**
     * Used to determine whether or not the game should end. If the climatepoints goes over this
     * threshold, the game is over, the world has been destroyed.
     *
     * @return int max_climatepoints
     */
    public int getMIN_CLIMATEPOINTS() {
        return MIN_CLIMATEPOINTS;
    }

    public boolean isGiftHasBeenGivenToday() {
        return giftHasBeenGivenToday;
    }

    public void setGiftHasBeenGivenToday(boolean giftHasBeenGivenToday) {
        this.giftHasBeenGivenToday = giftHasBeenGivenToday;
    }

    public boolean isAxePickedUp() {
        return axePickedUp;
    }

    public void setAxePickedUp(boolean axePickedUp) {
        this.axePickedUp = axePickedUp;
    }

    public boolean isSlept() {
        return slept;
    }

    public void setSlept(boolean slept) {
        this.slept = slept;
    }

    /**
     * @return The room that the player currently resides in
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Used to move the player around the rooms.
     *
     * @param newRoom: The room that the player is moving to
     */
    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null) {
            previousRoom = currentRoom;
        }
        currentRoom = newRoom;
    }

    public Room getPreviousRoom() {
        return previousRoom;
    }

    public void setPreviousRoom(Room previousRoom) {
        this.previousRoom = previousRoom;
    }

    public void addChoppedTreesInCertifiedForest() {
        numChoppedTreesWithoutPlantingSaplings++;
    }

    public void reduceChoppedTreesInCertifiedForest() {
        numChoppedTreesWithoutPlantingSaplings--;
    }

    public int getNumChoppedTreesWithoutPlantingSaplings() {
        return numChoppedTreesWithoutPlantingSaplings;
    }

    public void setNumChoppedTreesWithoutPlantingSaplings(int numChoppedTreesWithoutPlantingSaplings) {
        this.numChoppedTreesWithoutPlantingSaplings = numChoppedTreesWithoutPlantingSaplings;
    }

    public SimpleIntegerProperty getEquippedAxeChange() {
        return equippedAxeChange;
    }

    public String getPlayerDirectionInWorld() {
        return playerDirectionInWorld;
    }

    public void setPlayerDirectionInWorld(String playerDirectionInWorld) {
        this.playerDirectionInWorld = playerDirectionInWorld;
    }
}
