
package Locations;

import gameFunctionality.Player;
import java.util.Scanner;


public class TutorialRoom extends Room{
    Scanner input = new Scanner(System.in);
    boolean answer;
    private final Trailer trailer;
    
    
    public TutorialRoom(String description, Player player, Trailer trailer){
        
        super(description, player);
        this.trailer = trailer;
    }
    
    public void getAnswer(){
        System.out.println("Would you like to take a tutorial? Y|N");
        String playerInput = input.nextLine();
        if(playerInput.toUpperCase().equals("Y")){
            answer = true;
        }else{
            answer = false;
            
        }
    }
    
    @Override
    public String getLongDescription() {
        getAnswer();
        if(answer){
            return 
               "Welcome to the turtorial room! \n"
            +  "Here youl you'll learn how the game world funktions and how to naivagte\n"
            + getExitString();
        }else{
            
            humanPlayer.setCurrentRoom(trailer);
            return "hello";
        }
        
        
    }
}
