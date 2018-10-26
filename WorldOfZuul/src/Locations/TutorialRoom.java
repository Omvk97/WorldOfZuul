
package Locations;

import gameFunctionality.Player;
import java.util.Scanner;

public class TutorialRoom extends Room{
    Scanner input;
    
    public TutorialRoom(String description, Player player){
        
        super(description, player);
    }
    
    @Override
    public String getLongDescription() {
        return 
               "Welcome to the turtorial room! \n"
            +  "Here youl you'll learn how the game world funktions and how to naivagte"
            + getExitString();
        
    }
}
