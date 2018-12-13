package domain_layer.game_elements;

import domain_layer.game_functionality.Player;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author steffen
 * co-author: oliver
 * This class controlles the scenarios for the humansplayers climapoints.
 */
public class Radio {

    private final String weatherReporter = "Jensen: ";

    public String globalNews(Player humanPlayer) {
        LinkedHashMap<Integer, String> scenarios = new LinkedHashMap<>();
        scenarios.put(249, weatherReporter + "The atmosphere is very stable and optimal\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        scenarios.put(199, weatherReporter + "Forest areas are steadily increasing\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        scenarios.put(149, weatherReporter + "Air pollution is starting to thin out globally\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        scenarios.put(99, weatherReporter + "The CO2 atmospheric concentration is stagnating\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water Level: Stable\n");
        scenarios.put(49, weatherReporter + "Global atmospheric conditions are normal\n"
            + "Global Temperature: 21 degrees Celsius\n"
            + "Water level: Stable\n");
        scenarios.put(-49, weatherReporter + "The world is experiencing a global heat increase\n"
            + "Global Temperature: 23 degrees Celsius\n"
            + "Water level: Risen 1 meter\n");
        scenarios.put(-99, weatherReporter + "The polar ice caps are starting to melt\n"
            + "Global Temperature: 26 degrees Celsius\n"
            + "Water level: Risen 4 meters\n");
        scenarios.put(-149, weatherReporter + "Tropical storms are becoming more commonplace\n"
            + "Global Temperature: 26 degrees Celsius\n"
            + "Water level: Risen 6 meters\n");
        scenarios.put(-199, weatherReporter + "Large parts of the world are flooded. "
            + "The ice caps are gone\n"
            + "Global Temperature: 28 degrees Celsius\n"
            + "Water level: Risen 15 meters\n");
        scenarios.put(-249, weatherReporter + "The world is in anarchy. Civil unrest is at its most violent\n"
            + "Global Temperature: 31 degrees Celsius\n"
            + "Water Level: Risen 20 meters\n");

        return getStringOfscenarios(humanPlayer, scenarios);
    }
    public String localNews(Player humanPlayer) {
        LinkedHashMap<Integer, String> scenarios = new LinkedHashMap<>();
        scenarios.put(-199, weatherReporter + "The weather forecast indicates mild winds\n"
            + "Local Temperature: 32 degree Celsius\n"
            + "Wind Speeds: 5 m/sec\n");
        scenarios.put(-149, weatherReporter + "The weather forecast indicates no clouds and warm temperatures\n"
            + "Local Temperature: 35 degree Celsius\n"
            + "Wind speeds: 2 m/sec\n");
        scenarios.put(-99, weatherReporter + "The weather forecast indicates light rain\n"
            + "Local Temperature: 28 degrees Celsius\n"
            + "Wind Speeds: 7 m/sec\n");
        scenarios.put(49, weatherReporter + "The weather forecast indicates a calm weather\n"
            + "Local Temperature: 30 degrees Celsius\n"
            + "Wind Speeds: Windstill\n");
        scenarios.put(-49, weatherReporter + "The weather forecast indicates a heat wave\n"
            + "Local Temperature: 35 degrees Celsius\n"
            + "Wind Speeds: Windstill\n");
        scenarios.put(-99, weatherReporter + "The weather forecast indicates stronger winds and heavy rain\n"
            + "Local Temperature: 30 degrees Celsius\n"
            + "Wind Speeds: 20 m/sec\n");
        scenarios.put(-149, weatherReporter + "The weather forecast indicates a tropical storm\n"
            + "Local Temperature: 25 degrees Celsius\n"
            + "Wind Speeds: 32 m/sec\n");
        scenarios.put(-199, weatherReporter + "The weather forecast indicates a strong hurricane\n"
            + "Local Temperature: 25 degrees Celsius\n"
            + "Wind Speeds: 90 m/sec\n");
        scenarios.put(-249, weatherReporter + "MALFUNCTION weather station destroyed\n"
            + "Local Temperature: Error\n"
            + "Wind Speeds: Error\n");

        return getStringOfscenarios(humanPlayer, scenarios);
    }
    /**
     * Take the climaPoints from Humanplayer and finde the scenarios tha match the climepointe.
     * @param humanPlayer
     * @param scenarios
     * @return 
     */
    public String getStringOfscenarios(Player humanPlayer, LinkedHashMap<Integer, String> scenarios) {
        int climatePoints = humanPlayer.getClimatePointsValue();
        List<Integer> keySet = new ArrayList<>(scenarios.keySet());
        for (int i = 0; i < keySet.size(); i++) {
            if (climatePoints > keySet.get(0)) {
                return scenarios.get(keySet.get(0));
            } else if (climatePoints < keySet.get(keySet.size() - 1)) {
                return scenarios.get(keySet.get(keySet.size() - 1));
            } else if (climatePoints < keySet.get(i) && climatePoints > keySet.get(i + 1)) {
                return scenarios.get(keySet.get(i));
            }
        }
        return "";
    }
}
