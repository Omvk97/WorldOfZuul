package domain_layer.game_functionality;

import domain_layer.game_elements.BackPack;
import domain_layer.game_locations.Room;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class has the responsibility of changing and keeping information about all the things the
 * player interacts with during playtime.
 *
 * @author oliver
 */
public class PlayerInteraction {

    private final int MIN_CLIMATEPOINTS = -250;
    private boolean giftHasBeenGivenToday, axePickedUp, slept;
    private Room currentRoom = null;
    private Room previousRoom;
    private int numChoppedTreesWithoutPlantingSaplings;
    private String playerDirectionInWorld = "noDirection";
    private final SimpleIntegerProperty equippedAxeChange = new SimpleIntegerProperty();
    private final SimpleIntegerProperty logsInBackPack = new SimpleIntegerProperty();
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

    /**
     * @return If the player has gotten a gift from local village today.
     */
    public boolean isGiftHasBeenGivenToday() {
        return giftHasBeenGivenToday;
    }

    public void setGiftHasBeenGivenToday(boolean giftHasBeenGivenToday) {
        this.giftHasBeenGivenToday = giftHasBeenGivenToday;
    }

    /**
     * @return if the player has picked up the starter Axe from Trailer.
     */
    public boolean isAxePickedUp() {
        return axePickedUp;
    }

    public void setAxePickedUp(boolean axePickedUp) {
        this.axePickedUp = axePickedUp;
    }

    /**
     * @return if the player has slept, this is used to determine different actions in game world.
     */
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

    /**
     * Keeps a record of how many trees has been chopped in certified forest without planting them
     * again.
     *
     * @return how many chopped trees has been fell so far.
     */
    public int getNumChoppedTreesWithoutPlantingSaplings() {
        return numChoppedTreesWithoutPlantingSaplings;
    }

    /**
     * Used to reset number of chopped trees without planting them again if the player gets a fine
     * They shouldn't be punshed again the next day for the same mistake again.
     *
     * @param numChoppedTreesWithoutPlantingSaplings
     */
    public void setNumChoppedTreesWithoutPlantingSaplings(int numChoppedTreesWithoutPlantingSaplings) {
        this.numChoppedTreesWithoutPlantingSaplings = numChoppedTreesWithoutPlantingSaplings;
    }

    public void addChoppedTreesInCertifiedForest() {
        numChoppedTreesWithoutPlantingSaplings++;
    }

    public void reduceChoppedTreesInCertifiedForest() {
        numChoppedTreesWithoutPlantingSaplings--;
    }

    /**
     * What direction the player has come from. This is used to determine what animations is to be
     * run when entering and leaving rooms.
     *
     * @return
     */
    public String getPlayerDirectionInWorld() {
        return playerDirectionInWorld;
    }

    public void setPlayerDirectionInWorld(String playerDirectionInWorld) {
        this.playerDirectionInWorld = playerDirectionInWorld;
    }

    /**
     * Used to update whenever the player gets a new axe, so the topMenu axe icon can be updated to
     * the correct icon representing the right type of axe.
     *
     * @return simple integer property in order to set a listener to it in topMenu controller
     */
    public SimpleIntegerProperty getEquippedAxeChange() {
        return equippedAxeChange;
    }

    public SimpleIntegerProperty getLogsInBackPack() {
        return logsInBackPack;
    }

    /**
     * Used to update whenever the player logs in backpack is altered so the listener in topMenu
     * gets notified.
     * @param equippedBackPack the backpack that the player is wearing.
     */
    public void updateLogsInBackPack(BackPack equippedBackPack) {
        logsInBackPack.setValue(equippedBackPack.getLogsInBackPack().size());
    }
}
