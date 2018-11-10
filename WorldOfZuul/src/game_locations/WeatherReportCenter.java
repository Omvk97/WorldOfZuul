package game_locations;

import game_functionality.Player;
import java.util.HashMap;

public class WeatherReportCenter extends Room {

    private final String weatherReporter = "Jensen: ";
    HashMap<Integer, HashMap<String, String>> tester = new HashMap();

    public WeatherReportCenter(String description) {
        super(description);
    }

    @Override
    public String getLongDescription(Player humanPlayer) {
        return "You are standing " + getShortDescription() + "!\n"
            + weatherReporter + "Hi I´m your reporter\n"
            + "you have 3 options\n"
            + "----------------------------------\n"
            + "○ Global news - Watch the global news\n"
            + "○ Local news- Watch the local news \n"
            + "----------------------------------";
    }

    @Override
    public void option1(Player humanPlayer) {        
        int climatePoints = humanPlayer.getClimatePoints();

        if (climatePoints < this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[0]) {
            System.out.println(weatherReporter + "Global atmospheric conditions are normal\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water level: Stable\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[1]) {
            System.out.println(weatherReporter + "The world is experiencing a global heat increase\n"
                + "Global Temperature: 23 degrees Celsius\n"
                + "Water level: Risen 1 meter\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[1] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[2]) {
            System.out.println(weatherReporter + "The polar ice caps are starting to melt\n"
                + "Global Temperature: 26 degrees Celsius\n"
                + "Water level: Risen 4 meters\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[2] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[3]) {
            System.out.println(weatherReporter + "Tropical storms are becoming more commonplace\n"
                + "Global Temperature: 26 degrees Celsius\n"
                + "Water level: Risen 6 meters\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[3] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.println(weatherReporter + "Large parts of the world are flooded."
                + "The ice caps are gone\n"
                + "Global Temperature: 28 degrees Celsius\n"
                + "Water level: Risen 15 meters\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.println(weatherReporter + "The world is in anarchy. Civil unrest is at its most violent\n"
                + "Global Temperature: 31 degrees Celsius\n"
                + "Water Level: Risen 20 meters\n");
            // positive climatepoint
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[1]) {
            System.out.println(weatherReporter + "The CO2 atmospheric concentration is stagnating\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n");
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[1] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[2]) {
            System.out.println(weatherReporter + "Air pollution is starting to thin out globally\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n");
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[2] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[3]) {
            System.out.println(weatherReporter + "Forest areas are steadily increasing\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n");
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[3] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[4]) {
            System.out.println(weatherReporter + "The atmosphere is very stable and optimal\n"
                + "Global Temperature: 21 degrees Celsius\n"
                + "Water Level: Stable\n");
        }
    }

    @Override
    public void option2(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        // negative local clime point
        if (climatePoints < this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[0]) {
            System.out.println(weatherReporter + "The weather forecast indicates a calm weather\n"
                + "Local Temperature: 30 degrees Celsius\n"
                + "Wind Speeds: Windstill\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[0] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[1]) {
            System.out.println(weatherReporter + "The weather forecast indicates a heat wave\n"
                + "Local Temperature: 35 degrees Celsius\n"
                + "Wind Speeds: Windstill\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[1] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[2]) {
            System.out.println (weatherReporter + "The weather forecast indicates stronger winds and heavy rain\n"
                + "Local Temperature: 30 degrees Celsius\n"
                + "Wind Speeds: 20 m/sec\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[2] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[3]) {
            System.out.println(weatherReporter + "The weather forecast indicates a tropical storm\n"
                + "Local Temperature: 25 degrees Celsius\n"
                + "Wind Speeds: 32 m/sec\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[3] && climatePoints
            > this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.println(weatherReporter + "The weather forecast indicates a strong hurricane\n"
                + "Local Temperature: 25 degrees Celsius\n"
                + "Wind Speeds: 90 m/sec\n");
        } else if (climatePoints < this.getNEGATIVE_SCENARIO_POINTS()[4]) {
            System.out.println(weatherReporter + "MALFUNCTION weather station destroyed\n"
                + "Local Temperature: Error\n"
                + "Wind Speeds: Error\n");
            // positive local climatepoints
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[0] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[1]) {
            System.out.println(weatherReporter + "The weather forecast indicates a calm weather\n"
                + "Local Temperature: 30 degrees Celsius\n"
                + "Wind speeds: Windstill\n");
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[1] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[2]) {
            System.out.println(weatherReporter + "The weather forecast indicates light rain\n"
                + "Local Temperature: 28 degrees Celsius\n"
                + "Wind Speeds: 7 m/sec\n");
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[2] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[3]) {
            System.out.println(weatherReporter + "The weather forecast indicates no clouds and warm temperatures\n"
                + "Local Temperature: 35 degree Celsius\n"
                + "Wind speeds: 2 m/sec\n");
        } else if (climatePoints > this.getPOSITIVE_SCENARIO_POINTS()[3] && climatePoints
            < this.getPOSITIVE_SCENARIO_POINTS()[4]) {
            System.out.println(weatherReporter + "The weather forecast indicates mild winds\n"
                + "Local Temperature: 32 degree Celsius\n"
                + "Wind Speeds: 5 m/sec\n");
        }
    }
}
