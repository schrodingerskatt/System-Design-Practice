package StrategyPattern;
import StrategyPattern.Strategy.SportsDriveStrategy;

public class OffRoadVehicle extends Vehicle {
OffRoadVehicle(){
        super(new SportsDriveStrategy());
    }
}
