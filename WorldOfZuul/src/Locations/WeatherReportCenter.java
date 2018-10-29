package Locations;

import gameFunctionality.Player;
import gameFunctionality.NPC_Player;

public class WeatherReportCenter extends Room {

    // import af NPC player og hund 
    NPC_Player WeatherReporter = new NPC_Player("Human", "Stine : ", "Hi I´m your reporter");
    NPC_Player dog = new NPC_Player("dog ", "Bent :", " wuff !! \nwuff !! \nwuff !! \nwuff !! \nwuff !! \nwuff !! \nwuff !! \nwuff !! \n");

    // clime points 
    private final int[] Scenatiepoint = new int[]{-249, -199, -149, -99, -49, 0, 49, 99, 149, 199, 249};

    public WeatherReportCenter(String description, Player player) {
        super(description, player);

    }

    @Override
    public String getLongDescription() {

        return "You are standing "
            + getShortDescription()
            + "!\n"
            + WeatherReporter.getNPC_name() + WeatherReporter.getNPC_drecription()
            + "\n----------------------------------\n"
            + "you have 3 options  \n"
            + "----------------------------------\n"
            + "option 1 - go global news\n"
            + "option 2 - go local news \n"
            + "option 3 - go score bord \n"
            + "----------------------------------\n";

    }

    @Override
    public void option1() {
        int climePoints = humanPlayer.getClimatePoints();
        System.out.printf("%syou chose global news. \n", WeatherReporter.getNPC_name());
        // nigative Globale clime point
        if (climePoints == Scenatiepoint[6]) {
            System.out.printf(" %sthe weather is fine \n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[5]) {
            System.out.printf("%sthe animals are leving the forest\n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[4] && climePoints < Scenatiepoint[5]) {
            System.out.printf("%sThere are a storm on it way \nI hope the Non Certified Forest will stop the wind\n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[3] && climePoints > Scenatiepoint[4]) {
            System.out.printf("%sbecours the forest is gong the animals is dead and the world are close to an end\n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[2] && climePoints > Scenatiepoint[3]) {
            System.out.printf("%sThe word had endet and you lost you doog !!\n", WeatherReporter.getNPC_name());
        } else {
            System.out.println("Erro I dont know");

        }
    }

    /**
     *
     */
    @Override
    public void option2() {
        int climePoints = humanPlayer.getClimatePoints();
        System.out.println(WeatherReporter.getNPC_name() + "The local news are");

        // nigative Globale clime point
        if (climePoints == Scenatiepoint[6]) {
            System.out.printf(" %the Village is saved \n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[5]) {
            System.out.printf("%sThe animals are leving the city \n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[4] && climePoints >Scenatiepoint[5]) {
            System.out.printf("%sThe people in the city are angry  \nI plizz stop!!! \n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[3] && climePoints > Scenatiepoint[4]) {
            System.out.printf("%sI hate you\n", WeatherReporter.getNPC_name());
        } else if (climePoints < Scenatiepoint[2] && climePoints > Scenatiepoint[3]) {
            System.out.printf("%sI will kill you for this \n", WeatherReporter.getNPC_name());
            // god  clime point
        } else if (climePoints< Scenatiepoint[7]){
           System.out.printf("%s great job you are saving the woods \n", WeatherReporter.getNPC_name());    
        }else if (climePoints< Scenatiepoint[8] && climePoints> Scenatiepoint[7]){
        System.out.printf("%sThe world industri are stopping cutting the notcertifietforest \n", WeatherReporter.getNPC_name());
        }else if (climePoints<Scenatiepoint[9]&& climePoints> Scenatiepoint[8]){
            System.out.printf("%sThe world animals are returning to the forest\n ", WeatherReporter.getNPC_name());
            
        }
        else {
            System.out.println("Erro I dont know ");

        }

    }

    @Override
    public void option3() {
        System.out.println(dog+ " it is comming soon !!");

    }
}
