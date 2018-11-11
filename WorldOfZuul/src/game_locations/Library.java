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
                "○ learn fsc    ➤ For learn about fsc\n" +
                "○ learn pefc  ➤ For learn about PEFC";
    }

    @Override
    public void option1(Player humanPlayer) {
        System.out.println(libraryOwner +
                "So you want to learn about FSC\n" +
                "");
        Scanner input = new Scanner(System.in);
        String chose = input.nextLine();
        switch (chose) {
            case "1":
            case "what isFSC":
                fscIs();
                break;
            case "2":
            case "fsc numbers ":
                System.out.println(libraryOwner +
                        "201,7 mio ha forest are certified for FSC\n" +
                        "34,133 companys is CoC-certified (Chain oF Custody\n" +
                        "");
                break;
            default:
                System.out.println(libraryOwner + "I don't know what you mean");
                break;
        }

    }

    @Override
    public void option2(Player humanPlayer) {
        System.out.println(libraryOwner + "So you want to learn about PEFC");
    }

    private void fscIs() {
        System.out.println();
    }


}

