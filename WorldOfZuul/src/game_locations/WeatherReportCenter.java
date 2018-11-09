package game_locations;

import game_functionality.Player;

public class WeatherReportCenter extends Room {

    private final String weatherReporter = "Jensen: ";

    public WeatherReportCenter(String description) {
        super(description);
    }

    @Override
    public String getLongDescription(Player humanPlayer) {

        return "You are standing "
            + getShortDescription()
            + "!\n"
            + weatherReporter + "Hi I´m your reporter\n"
            + "you have 3 options\n"
            + "----------------------------------\n"
            + "○ Global news - Watch the global news\n"
            + "○ Local news- Watch the local news \n"
            + "○ Scorebord- Access the scoreboard \n"
            + "----------------------------------";
    }

    @Override
    public void option1(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        // negative Globale climate point
        if (climatePoints < this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[0]) {
            System.out.printf("%sInfo: Global atmospheric conditions are normal\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water level: Stable\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[1]) {
            System.out.printf("$sInfo: The world is experiencing a global heat increase\n"
                + "Global Temperature: 23 degrees Celsius\n"
                + "Water level: Risen 1 meter\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[1] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[2]) {
            System.out.printf("$sInfo: The polar ice caps are starting to melt\n"
                + "Global Temperature: 26 degrees Celsius\n"
                + "Water level: Risen 4 meters\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[2] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[3]) {
            System.out.printf("$sInfo: Tropical storms are becoming more commonplace\n"
                + "Global Temperature: 26 degrees Celsius\n"
                + "Water level: Risen 6 meters\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[3] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.printf("$sInfo: Large parts of the world are flooded."
                + "The ice caps are gone\n"
                + "Global Temperature: 28 degrees Celsius\n"
                + "Water level: Risen 15 meters\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.printf("$sInfo: The world is in anarchy. Civil unrest is at its most violent\n"
                + "Global Temperature: 31 degrees Celsius\n"
                + "Water Level: Risen 20 meters\n",
                weatherReporter);
            // positive climepoint
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[1]) {
            System.out.printf("$sInfo: The CO2 atmospheric concentration is stagnating\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n",
                weatherReporter);
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[1] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[2]) {
            System.out.printf("$sInfo: Air pollution is starting to thin out globally\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n",
                weatherReporter);
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[2] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[3]) {
            System.out.printf("$sInfo: Forest areas are steadily increasing\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n",
                weatherReporter);
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[3] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[4]) {
            System.out.printf("$sInfo: The atmosphere is very stable and optimal\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n",
                weatherReporter);
        }
    }

    @Override
    public void option2(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        // negative local clime point
        if (climatePoints < this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[0]) {
            System.out.printf("%sinfo: The weather forecast indicates a calm weather\n"
                + "Local Temperature: 30 degrees Celsius\n"
                + "Wind Speeds: Windstill\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[1]) {
            System.out.printf("%sinfo: The weather forecast indicates a heat wave\n"
                + "Local Temperature: 35 degrees Celsius\n"
                + "Wind Speeds: Windstill\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[1] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[2]) {
            System.out.printf("%sinfo: The weather forecast indicates stronger winds and heavy rain\n"
                + "Local Temperature: 30 degrees Celsius\n"
                + "Wind Speeds: 20 m/sec\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[2] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[3]) {
            System.out.printf("%sinfo: The weather forecast indicates a tropical storm\n"
                + "Local Temperature: 25 degrees Celsius\n"
                + "Wind Speeds: 32 m/sec\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[3] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.printf("%sinfo: The weather forecast indicates a strong hurricane\n"
                + "Local Temperature: 25 degrees Celsius\n"
                + "Wind Speeds: 90 m/sec\n",
                weatherReporter);
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.printf("$sinfo: MALFUNCTION weather station destroyed\n"
                + "Local Temperature: Error\n"
                + "Wind Speeds: Error\n",
                weatherReporter);
            // positive local climatepoints
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[1]) {
            System.out.printf("%sinfo: The weather forecast indicates a calm weather\n"
                + "Local Temperature: 30 degrees Celsius\n"
                + "Wind speeds: Windstill\n",
                weatherReporter);
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[1] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[2]) {
            System.out.printf("%sinfo: The weather forecast indicates light rain\n"
                + "Local Temperature: 28 degrees Celsius\n"
                + "Wind Speeds: 7 m/sec\n",
                weatherReporter);
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[2] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[3]) {
            System.out.printf("%sinfo: The weather forecast indicates no clouds and warm temperatures\n"
                + "Local Temperature: 35 degree Celsius\n"
                + "Wind speeds: 2 m/sec\n",
                weatherReporter);
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[3] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[4]) {
            System.out.printf("%sinfo: The weather forecast indicates mild winds\n"
                + "Local Temperature: 32 degree Celsius\n"
                + "Wind Speeds: 5 m/sec\n",
                weatherReporter);
        }
    }
}
