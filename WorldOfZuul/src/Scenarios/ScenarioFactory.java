package Scenarios;

public class ScenarioFactory {

    private ScenarioFactory() {
    }

    public static Scenario createStandardScenario() {
        return new StandardScenario(19, -19);
    }
    
    public static Scenario createPositiveScenario1() {
        return new PositiveScenario1(29, 19);
    }
    
    public static Scenario createPositiveScenario2() {
        return new PositiveScenario2(39, 29);
    }
    
    public static Scenario createPositiveScenario3() {
        return new PositiveScenario3(49, 39);
    }
    
    public static Scenario createPositiveScenario4() {
        return new PositiveScenario4(59, 49);
    }
    
    public static Scenario createNegativeScenario1() {
        return new NegativeScenario1(-19, -29);
    }
    
    public static Scenario createNegativeScenario2() {
        return new NegativeScenario2(-29, -39);
    }
    
    public static Scenario createNegativeScenario3() {
        return new NegativeScenario3(-39, -49);
    }
    
    public static Scenario createNegativeScenario4() {
        return new NegativeScenario4(-49, -59);
    }
    
    public static Scenario createNegativeScenario5() {
        return new NegativeScenario1(-59, 0);
    }
    
}
