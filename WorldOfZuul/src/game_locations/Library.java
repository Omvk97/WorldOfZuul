package game_locations;

import game_functionality.Player;

public class Library extends Room {
    String libraryOwner = "Anna: \n";


    public Library(String description) {
        super(description);

    }

    @Override
    public String getLongDescription(Player humanplayer) {
        return libraryOwner + "Hi " + "You are standing " + getShortDescription() + "!\n"
                + "---------------------------------------------\n" +
                "If you pay I wil make you axe Stronger \n " +
                "------------------------------------\n" +
                "○ learn fsc    ➤ For learn about fsc\n" +
                "○ learn PFSC   ➤ For learn about PFSC";

    }

    @Override
    public void option1(Player humanPlayer) {
        System.out.println(libraryOwner + "So you want to learn about FSC");

    }

    @Override
    public void option2(Player humanPlayer) {
        System.out.println(libraryOwner + "So you want to learn about PEFC");
    }

}
