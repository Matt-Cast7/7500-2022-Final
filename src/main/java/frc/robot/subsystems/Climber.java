package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

    private CANSparkMax leftClimber = new CANSparkMax(Constants.leftClimber,
    MotorType.kBrushless);
    private CANSparkMax rightClimber = new CANSparkMax(Constants.rightClimber, MotorType.kBrushless);

    private boolean flip = false;

    static double leftEncoderZero;
    static double rightEncoderZero;

    private RelativeEncoder leftEncoder = leftClimber.getEncoder();
    private RelativeEncoder rightEncoder = rightClimber.getEncoder();

    public Climber() {

       leftClimber.setIdleMode(IdleMode.kBrake);
       rightClimber.setIdleMode(IdleMode.kBrake);

        leftClimber.setInverted(flip);
        rightClimber.setInverted(!flip);

        leftEncoderZero = leftEncoder.getPosition();
        rightEncoderZero = rightEncoder.getPosition();

    }

    public void setClimber(double power){
        leftClimber.set(power);
        rightClimber.set(power);
    }

    public void setClimber(double lPower, double rPower){
        
        leftClimber.set(lPower);
        rightClimber.set(rPower);
    }

    public void setLeftClimber(double power) {
        leftClimber.set(power);
    }

    public void setRightClimber(double power) {
        rightClimber.set(power);
    }

    @Override
    public void periodic() {
        //System.out.println(rightClimber.get());
    }

    public double getLeftDistanceTraveled() {
        return (getLeftEncoderPos() * (Constants.pullyWitdh * 2 * Math.PI));
    }

    public double getRightDistanceTraveled() {
        return (getRightEncoderPos() * (Constants.pullyWitdh * 2 * Math.PI));
    }

    public double getLeftEncoderPos() {
        // return (leftEncoder.getPosition() - leftEncoderZero) /
        // Constants.climberGearRatio;
        return 0;
    }

    public double getRightEncoderPos() {
        return (rightEncoder.getPosition() - rightEncoderZero) / Constants.climberGearRatio;
    }

    public void resetEncoders() {
        // leftEncoderZero = leftEncoder.getPosition();
        rightEncoderZero = rightEncoder.getPosition();
    }
}
