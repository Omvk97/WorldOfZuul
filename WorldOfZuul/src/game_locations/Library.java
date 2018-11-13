package game_locations;

import game_functionality.Player;

import java.util.Scanner;

public class Library extends Room {
    String libraryOwner = "Anna: \n";


    public Library(String description) {
        super(description);

    }

    @Override
    public String getLongDescription(Player humanplayer) {
        return libraryOwner + "Hi " + "You are standing " + getShortDescription() + "!\n"
                + "---------------------------------------------\n" +
                "If you want to learn more about fsc FSC and PEFC \n " +
                "------------------------------------\n" +
                "○ learn fsc   ➤ For learning about FSC\n" +
                "○ learn pefc  ➤ For learning about PEFC";
    }

    @Override
    public void option1(Player humanPlayer) {
        System.out.println(libraryOwner +
                "So you want to learn about FSC\n" +
                "Options\n" +
                "what is PEFC\n" +
                "PEFC facts");
        Scanner input = new Scanner(System.in);
        String chose = input.nextLine();
        switch (chose) {
            case "what is FSC":
                fscIs();
                break;
            case "fsc facts":
                System.out.println(libraryOwner +"tekst kommer sender");
                break;
            default:
                System.out.println(libraryOwner + "I don't know what you mean");
                break;
        }

    }
    @Override
    public void option2(Player humanPlayer) {
        System.out.println(libraryOwner + "So you want to learn about PEFC \n" +
                "Options\n" +
                "what is PEFC\n" +
                "PEFC facts");
        Scanner input = new Scanner(System.in);
        String chose = input.nextLine();
        switch (chose) {
            case "what is PEFC":
                PEFCis();
                break;
            case "PEFC facts":
                System.out.println(libraryOwner +"tekst kommer sender");
                break;
            default:
                System.out.println(libraryOwner + "I don't know what you mean");
                break;
        }
    }

    private void fscIs() {
        System.out.println(libraryOwner + "The Forest Stewardship Council (FSC) is an international non-profit organization\n" +
                "established in 1993 to promote responsible management of the world’s forests. The FSC does this by \n" +
                "setting standards on forest products, along with certifying and labeling them as eco-friendly.\n\n" +
                "The FSC’s stated mission is to promote environmentally appropriate, socially beneficial and economically \n" +
                "viable management of the world's forests. To this end the body has published a global strategy with five \n");
    }

    private void PEFCis(){
        System.out.println(libraryOwner + "tekst kommer her");


    }


}

