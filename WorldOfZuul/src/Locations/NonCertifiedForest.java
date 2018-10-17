package Locations;

import gameFunctionality.Tree;


public class NonCertifiedForest extends Room{
    
    Tree[] trees;

    public NonCertifiedForest(String description) {
        super(description);
        this.trees = new Tree[100];
    }

    @Override
    public String getShortDescription() {
        return super.getShortDescription();
    }
    
    
    
    
    
}