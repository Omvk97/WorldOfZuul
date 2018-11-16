package game_locations;

import game_functionality.Player;

public class Library extends Room {

    String libraryOwner = "Anna: \n";

    public Library() {
    }

    @Override
    public String roomEntrance(Player humanplayer) {
        return libraryOwner + "Hi, which book do you want to read?\n"
            + "---------------------------------------------\n"
            + "○ Read book 1 ➤ \"The Felling of Trees\"\n"
            + "○ Read book 2 ➤ \"The Story of FSC\"\n"
            + "○ Read book 3 ➤ \"The Story of PEFC\"\n"
            + "------------------------------------";
    }

    @Override
    public void option1(Player humanPlayer) {
        System.out.println("\"The Felling of Trees\" - written by professors.\n"
            + "Around the globe 7 million hectare of forest area disappear each year \n"
            + "due to deforestation.\n"
            + "The decreased forest areas causes several species to become endangered.\n"
            + "It is estimated that 15% of all greenhouse gas emissions are the result"
            + "of deforestation.\n"
            + "This is very bad");
    }

    @Override
    public void option2(Player humanPlayer) {
        System.out.println("\"The Story of FSC\" - written by FSC.\n"
            + "FSC, Forest Stewardship Council, is one of several large organizations\n"
            + "dedicated to combat deforestation.\n"
            + "Their main function involves certifying and regulating\n"
            + "forest areas to reduce reckless deforestation.\n"
            + "They cover over 200 million hectare certified forest areas globally.");
    }

    @Override
    public void option3(Player humanPlayer) {
        System.out.println("\"The Story of PEFC\" - written by PEFC.\n"
            + "PEFC, Programme for the Endorsement of Forest Certification, is the largest\n"
            + "organization dedicated to combat deforestation.\n"
            + "Their main function involves certifying and regulating\n"
            + "forest areas to reduce reckless deforestation.\n"
            + "They cover over 300 million hectare certified forest areas globally.");
    }

}
