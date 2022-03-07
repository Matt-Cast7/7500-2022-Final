package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Gyro {

    private static AHRS ahrs;

    

    public Gyro(){
        try {
            ahrs = new AHRS(SPI.Port.kMXP); 
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

        ahrs.calibrate();

       // Shuffleboard.getTab("TeleOp").addNumber("Angle", () -> getAngle());

    }


    public void reset(){
        ahrs.reset();
    }

    public double getAngle(){
        return ahrs.getAngle();
    }
    
}
