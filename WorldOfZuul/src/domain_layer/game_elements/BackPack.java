package domain_layer.game_elements;

import java.util.ArrayList;

/**
 * Has the responsibility of storing trees and makes sure that the trees the player fells
 * is saved no matter where player moves.
 * @author oliver
 */
public class BackPack extends Item {

    private final ArrayList<Tree> logsInBackPack;
    private final int backpackCapacity;

    public BackPack(String name, int price, int backpackCapacity) {
        super(name, price);
        this.backpackCapacity = backpackCapacity;
        this.logsInBackPack = new ArrayList<>();
    }

    /**
     * @return max capacity of the backpack
     */
    public int getBackpackCapacity() {
        return backpackCapacity;
    }

    /**
     * @return ArrayList with Trees currently in the backpack
     */
    public ArrayList<Tree> getLogsInBackPack() {
        return logsInBackPack;
    }

    /**
     * @return Amount of trees in the backpack currently
     */
    public int getAmountOfLogsInBackPack() {
        return logsInBackPack.size();
    }

    /**
     * @param tree that is to be added to the backpack
     */
    public void addTreeToBackpack(Tree tree) {
        logsInBackPack.add(tree);
    }

    /**
     * Whenever the backpack's contents is sold.
     */
    public void emptyBackpack() {
        logsInBackPack.clear();
    }

    /**
     * Used to remove logs from backpack one by one
     */
    public void removeLogFromBackpack() {
        logsInBackPack.remove(0);
    }

    /**
     * Information about the backpack so the player can make a decision on whether or not to buy
     * the backpack.
     * @return all the information about the backpack.
     */
    @Override
    public String toString() {
        return getDescription() + " with a capacity of " + getBackpackCapacity()
            + " | " + getPrice() + " gold coins";
    }

}
