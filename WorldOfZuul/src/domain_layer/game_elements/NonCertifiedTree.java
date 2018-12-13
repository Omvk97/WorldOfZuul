package domain_layer.game_elements;

/**
 * Non-Certified Forest is made up of these trees.
 * @author oliver
 */
public class NonCertifiedTree extends Tree {

    public NonCertifiedTree(int treeHealth) {
        super(treeHealth, -10, 1.5);
    }    
}
