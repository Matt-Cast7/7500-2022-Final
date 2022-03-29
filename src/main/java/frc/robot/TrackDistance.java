package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class TrackDistance {

    private static NetworkTableEntry a2Entry;

    
    public TrackDistance() {

        a2Entry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
        Shuffleboard.getTab("TeleOp").addNumber("distance", () -> getDistance());

    }



    public static double getDistance(){
        double a2 = a2Entry.getDouble(0);
        double a1 = Constants.a1;
        double h1 = Constants.h1;
        double h2 = Constants.h2;


        double hDiff = h2 - h1;
        double aSum = a1 + a2;

        return hDiff / Math.tan(Math.toRadians(aSum));
       
    }
    
}
