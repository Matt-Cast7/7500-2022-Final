package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.MotorDirections;

public class Intake extends SubsystemBase {

    private final CANSparkMax intake = new CANSparkMax(Constants.Intake, MotorType.kBrushless);


    private static double intakeSpeed = 0.4;

    private boolean flipIntake;

    public Intake() {
        flipIntake = MotorDirections.FlipIntake;

        intake.setInverted(flipIntake);
        intake.setIdleMode(IdleMode.kCoast);



    }

    public void enableIntake() {
        intake.set(intakeSpeed);
    }

    public void setIntake(double speed) {
        intake.set(speed);
    }

    public void stop() {
        setIntake(0);
    }

    public void setDefaultIntakeSpeed(double speed) {
        intakeSpeed = speed;
    }

    public void periodic(){
        if(flipIntake != MotorDirections.FlipIntake){
            flipIntake = MotorDirections.FlipIntake;
            intake.setInverted(flipIntake);
        }


    }
}
