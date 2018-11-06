package Locations;

import gameFunctionality.Player;

public class WeatherReportCenter extends Room {

    private final String weatherReporter = "Jensen: ";

    // clime points = 0.-50,-100.-150.-200.50.10.150.200.250
    int[] Scenatiepoint = new int[]{0, -50, -100, -150, -200, 50, 100, 150, 200, 250};

    public WeatherReportCenter(String description, Player player) {
        super(description, player);

    }

    @Override
      public String getLongDescription() {

        return "You are standing "
            + getShortDescription()
            + "!\n"
            + weatherReporter + "Hi I´m your reporter"
            + "you have 3 options  \n"
            + "----------------------------------\n"
            + "globalnews - go global news\n"
            + "localnews- go local news \n"
            + "scorebord- go score bord \n"
            + "----------------------------------\n";

    }

    @Override
    public void option1() {
        int climePoints = humanPlayer.getClimatePoints();
        // nigative Globale clime point
        if (climePoints == Scenatiepoint[0]) {
            System.out.printf("%sInfo :The weather is fine\n"
                + "temp : 30\n"
                + "water level: normal\n"
                ,weatherReporter);
        } else if (climePoints < Scenatiepoint[0] && climePoints >= Scenatiepoint[1]) {
            System.out.printf("$sInfo: heat wave over the hell world \n"
                + "Temp: 35 \n"
                + "water level: +1 \n"
                , weatherReporter);
        } else if (climePoints < Scenatiepoint[1] && climePoints >= Scenatiepoint[2]) {
            System.out.printf("$sInfo: the snow is melting\n"
                + "Temp: 40 \n"
                + "water level: +4\n"
                , weatherReporter);
        } else if (climePoints < Scenatiepoint[2] && climePoints >= Scenatiepoint[3]) {
            System.out.printf("$sInfo: giant storms threaten the world\n"
                + "Temp: 45\n"
                + "water level: +6\n", weatherReporter);
        } else if (climePoints < Scenatiepoint[3] && climePoints >= Scenatiepoint[4]) {
            System.out.printf("$sInfo: Large parts of the world are flooded\n"
                + "Temp: 50\n"
                + "water level: +15\n"
                , weatherReporter);
        } else if (climePoints < Scenatiepoint[4]) {
            System.out.printf("$sInfo: The world is in war. more countries conquer others to get space\n"
                + "Temp: 55\n"
                + "water level: +20\n"
                , weatherReporter);
            // positive climepoint
        } else if (climePoints > Scenatiepoint[0] && climePoints <= Scenatiepoint[5]) {
            System.out.printf( "$sInfo: The amount of co2 begins to disappear\n"
                + "Temp: 30 \n"
                + "water level: 0\n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[5] && climePoints <= Scenatiepoint[6]) {
            System.out.printf("$sInfo: China can remove their masks\n"
                + "Temp: 30\n"
                + "water level: 0\n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[6] && climePoints <= Scenatiepoint[7]) {
            System.out.printf("$sInfo: new forests appear all over the world\n"
                + "Temp: 30\n"
                + "water level: 0\n", weatherReporter);
        } else if (climePoints > Scenatiepoint[7] && climePoints <= Scenatiepoint[8]) {
            System.out.printf("$sInfo: animals return to the forests\n"
                + "Temp: 30\n"
                + "water level: 0\n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[8] && climePoints <= Scenatiepoint[9]) {
            System.out.printf("$sInfo: people live longer\n"
                + "Temp: 30\n"
                + "water level: 0\n", weatherReporter);
        } else if (climePoints > Scenatiepoint[9]) {
            System.out.printf("$sInfo: a meteor heading towards the world\n"
                + "Temp: 30\n"
                + "water level: 0\n"
                , weatherReporter);
        }
    }

    @Override
    public void option2() {
        int climePoints = humanPlayer.getClimatePoints();
        // nigative Globale clime point
        if (climePoints == Scenatiepoint[0]) {
            System.out.printf("%s info: The weather is god\n"
                + "temp: 30\n"
                + "wind: \n"
                , weatherReporter);
        } else if (climePoints < Scenatiepoint[0] && climePoints >= Scenatiepoint[1]) {
            System.out.printf("%s info: heat wave on our way\n"
                + "temp: 40\n"
                + "wind: \n"
                , weatherReporter);
        } else if (climePoints < Scenatiepoint[1] && climePoints >= Scenatiepoint[2]) {
            System.out.printf("%s"
                + "info: giant rainways\n"
                + "temp: 30\n"
                + "wind: 20m/sec\n", weatherReporter);
        } else if (climePoints < Scenatiepoint[2] && climePoints >= Scenatiepoint[3]) {
            System.out.printf("%s info: Storm is comming\n"
                + "temp: 30\n"
                + "wind: 32m/sec\n"
                , weatherReporter);
        } else if (climePoints < Scenatiepoint[3] && climePoints >= Scenatiepoint[4]) {
            System.out.printf("%s info: a tonado destroys the city\n"
                + "temp: 30\n"
                + "wind: 150m/sec\n", weatherReporter);
        } else if (climePoints < Scenatiepoint[4]) {
            System.out.printf("$s is dead\n"
                + "info:  Erro\n"
                + "temp:  Erro\n"
                + "vwind: Erro\n"
                , weatherReporter);
            // positive climepoint
        } else if (climePoints > Scenatiepoint[0] && climePoints <= Scenatiepoint[5]) {
            System.out.printf("%sinfo: The weather is god\n"
                + "temp: 30"
                + "wind: \n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[5] && climePoints <= Scenatiepoint[6]) {
            System.out.printf("%sinfo: a littel rain\n"
                + "temp: 25\n"
                + "wind: 7m/sec\n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[6] && climePoints <= Scenatiepoint[7]) {
            System.out.printf("%sinfo: the son shines\n"
                + "temp: 35\n"
                + "wind: 2m/sec\n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[7] && climePoints <= Scenatiepoint[8]) {
            System.out.printf("%sinfo: good wind\n"
                + "temp: 32\n"
                + "wind: 10M/sek\n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[8] && climePoints <= Scenatiepoint[9]) {
            System.out.printf("%sinfo: son all day\n"
                + "temp: 35\n"
                + "wind: 2m/sek\n"
                , weatherReporter);
        } else if (climePoints > Scenatiepoint[9]) {
            System.out.printf(
                 "%s\n"
                + "info: coold day\n"
                + "temp: 20\n"
                + "wind: 6m/sek\n"
                , weatherReporter);

        }
    }

    @Override
    public void option3() {

        System.out.println("not yet !!");
    }
}
