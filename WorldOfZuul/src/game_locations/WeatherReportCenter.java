package game_locations;

import game_functionality.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class WeatherReportCenter extends Room {

    private final String weatherReporter = "Jensen: ";
    HashMap<Integer, HashMap<String, String>> tester = new HashMap();

    public WeatherReportCenter() {
    }

    @Override
    public String getLongDescription(Player humanPlayer) {
        return weatherReporter + "Hi I´m your reporter\n"
            + "----------------------------------\n"
            + "○ Global news ➤ Watch the global news\n"
            + "○ Local news  ➤ Watch the local news \n"
            + "----------------------------------";
    }

    @Override
    public void option1(Player humanPlayer) {
        LinkedHashMap<Integer, String> scenarios = new LinkedHashMap();
        scenarios.put(59, weatherReporter + "The atmosphere is very stable and optimal\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        
        scenarios.put(49, weatherReporter + "Forest areas are steadily increasing\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        
        scenarios.put(39, weatherReporter + "Air pollution is starting to thin out globally\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        
        scenarios.put(29, weatherReporter + "The CO2 atmospheric concentration is stagnating\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        
        scenarios.put(19, weatherReporter + "Global atmospheric conditions are normal\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water level: Stable\n");
        
        scenarios.put(-19, weatherReporter + "The world is experiencing a global heat increase\n"
            + "Global Temperature: 23 degrees Celsius\n"
            + "Water level: Risen 1 meter\n");
        
        scenarios.put(-29, weatherReporter + "The polar ice caps are starting to melt\n"
            + "Global Temperature: 26 degrees Celsius\n"
            + "Water level: Risen 4 meters\n");
        
        scenarios.put(-39, weatherReporter + "Tropical storms are becoming more commonplace\n"
            + "Global Temperature: 26 degrees Celsius\n"
            + "Water level: Risen 6 meters\n");
        
        scenarios.put(-49, weatherReporter + "Large parts of the world are flooded. "
            + "The ice caps are gone\n"
            + "Global Temperature: 28 degrees Celsius\n"
            + "Water level: Risen 15 meters\n");
        
        scenarios.put(-59, weatherReporter + "The world is in anarchy. Civil unrest is at its most violent\n"
            + "Global Temperature: 31 degrees Celsius\n"
            + "Water Level: Risen 20 meters\n");

        int climatePoints = humanPlayer.getClimatePoints();
        List<Integer> keySet = new ArrayList(scenarios.keySet());
        for (int i = 0; i < keySet.size(); i++) {
            if (climatePoints > keySet.get(0)) {
                System.out.print(scenarios.get(keySet.get(0)));
                break;
            } else if (climatePoints < keySet.get(keySet.size() - 1)) {
                System.out.print(scenarios.get(keySet.get(keySet.size() - 1)));
                break;
            } else if (climatePoints < keySet.get(i) && climatePoints > keySet.get(i + 1)) {
                System.out.print(scenarios.get(keySet.get(i)));
            }
        }
    }

    @Override
    public void option2(Player humanPlayer) {
        LinkedHashMap<Integer, String> scenarios = new LinkedHashMap();
        scenarios.put(49, weatherReporter + "The weather forecast indicates mild winds\n"
            + "Local Temperature: 32 degree Celsius\n"
            + "Wind Speeds: 5 m/sec\n");

        scenarios.put(39, weatherReporter + "The weather forecast indicates no clouds and warm temperatures\n"
            + "Local Temperature: 35 degree Celsius\n"
            + "Wind speeds: 2 m/sec\n");

        scenarios.put(29, weatherReporter + "The weather forecast indicates light rain\n"
            + "Local Temperature: 28 degrees Celsius\n"
            + "Wind Speeds: 7 m/sec\n");
        scenarios.put(19, weatherReporter + "The weather forecast indicates a calm weather\n"
            + "Local Temperature: 30 degrees Celsius\n"
            + "Wind Speeds: Windstill\n");

        scenarios.put(-19, weatherReporter + "The weather forecast indicates a heat wave\n"
            + "Local Temperature: 35 degrees Celsius\n"
            + "Wind Speeds: Windstill\n");

        scenarios.put(-29, weatherReporter + "The weather forecast indicates stronger winds and heavy rain\n"
            + "Local Temperature: 30 degrees Celsius\n"
            + "Wind Speeds: 20 m/sec\n");

        scenarios.put(-39, weatherReporter + "The weather forecast indicates a tropical storm\n"
            + "Local Temperature: 25 degrees Celsius\n"
            + "Wind Speeds: 32 m/sec\n");
        scenarios.put(-49, weatherReporter + "The weather forecast indicates a strong hurricane\n"
            + "Local Temperature: 25 degrees Celsius\n"
            + "Wind Speeds: 90 m/sec\n");
        scenarios.put(-59, weatherReporter + "MALFUNCTION weather station destroyed\n"
            + "Local Temperature: Error\n"
            + "Wind Speeds: Error\n");

        int climatePoints = humanPlayer.getClimatePoints();
        List<Integer> keySet = new ArrayList(scenarios.keySet());
        for (int i = 0; i < keySet.size(); i++) {
            if (climatePoints > keySet.get(0)) {
                System.out.print(scenarios.get(keySet.get(0)));
                break;
            } else if (climatePoints < keySet.get(keySet.size() - 1)) {
                System.out.print(scenarios.get(keySet.get(keySet.size() - 1)));
                break;
            } else if (climatePoints < keySet.get(i) && climatePoints > keySet.get(i + 1)) {
                System.out.print(scenarios.get(keySet.get(i)));
            }
        }
    }
}
