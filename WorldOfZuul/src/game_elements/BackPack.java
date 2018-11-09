package game_elements;

import java.util.ArrayList;

public class BackPack extends Item {

    private ArrayList<Tree> logsInBackPack;
    private final int backpackCapacity;

    public BackPack(String name, int price, int backpackCapacity) {
        super(name, price);
        this.backpackCapacity = backpackCapacity;
        this.logsInBackPack = new ArrayList();
    }

    /**
     * For at se hvor mange logs der kan være i rygsækken
     *
     * @return mængden af pladser i rygsækken
     */
    public int getBackpackCapacity() {
        return backpackCapacity;
    }

    /**
     * for at kunne bruge selve arraylisten til noget
     *
     * @return arraylist med de logs der er i arraylisten
     */
    public ArrayList<Tree> getLogsInBackPack() {
        return logsInBackPack;
    }

    /**
     * Finder hvor mange logs der er på nuværende tidspunkt i rygsækken
     *
     * @return størrelsen af arraylisten med logs
     */
    public int getAmountOfLogsInBackPack() {
        return this.logsInBackPack.size();
    }

    /**
     * Denne metode benyttes til at få information om hvilken træ type som spilleren bærer rundt på.
     *
     * @param treePosition det er indexet i arrayListen med alle træerne
     * @return arraylist med træer som spilleren bærer rundt på.
     */
    public Tree getTreeTypeInBackpack(int treePosition) {
        return this.logsInBackPack.get(treePosition);
    }

    /**
     * Bruges til at tilføje træer en af gangen til rygsækken
     *
     * @param tree det træ som skal tilføjes til rygsækken
     */
    public void addTreeToBackpack(Tree tree) {
        if (tree instanceof CertifiedTree) {
            this.logsInBackPack.add(new CertifiedTree());
        } else {
            this.logsInBackPack.add(new NonCertifiedTree());
        }
    }

    /**
     * Metoden her bruges til når spilleren enten sælger eller opbevarer deres logs, så tømmes rygsækken
     */
    public void emptyBackpack() {
        this.logsInBackPack = new ArrayList();
    }

    /**
     * Bruges til at fjerne træer fra rygsækken en af gangen, starter fra det første index i arraylisten.
     */
    public void removeLogFromBackpack() {
        this.logsInBackPack.remove(0);
    }

    @Override
    public String toString() {
        return getDescription() + " with a capacity of " + getBackpackCapacity()
            + " | " + getPrice() + " gold coins";
    }

}
