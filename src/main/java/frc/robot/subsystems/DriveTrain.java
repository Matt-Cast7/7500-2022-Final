package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Gyro;

public class DriveTrain extends SubsystemBase {

    private final CANSparkMax leftMaster = new CANSparkMax(Constants.Motor_Left_Back, MotorType.kBrushless);
    private final CANSparkMax leftSlave = new CANSparkMax(Constants.Motor_Left_Front, MotorType.kBrushless);
    private final CANSparkMax rightMaster = new CANSparkMax(Constants.Motor_Right_Back, MotorType.kBrushless);
    private final CANSparkMax rightSlave = new CANSparkMax(Constants.Motor_Right_Front, MotorType.kBrushless);

    private boolean lFlip = false;
    private boolean rFlip = true;

    private static double leftEncoderZero = 0;
    private static double rightEncoderZero = 0;

    private RelativeEncoder m_leftEncoder = leftMaster.getEncoder();
    private RelativeEncoder m_rightEncoder = rightMaster.getEncoder();


    private Gyro gyro = new Gyro();

    public DriveTrain() {

        

        leftMaster.setInverted(lFlip);
        leftSlave.setInverted(lFlip);
        rightMaster.setInverted(rFlip);
        rightSlave.setInverted(rFlip);

        leftMaster.setIdleMode(IdleMode.kCoast);
        leftSlave.setIdleMode(IdleMode.kCoast);
        rightMaster.setIdleMode(IdleMode.kCoast);
        rightSlave.setIdleMode(IdleMode.kCoast);

        resetEncoders();

    }

    /**
     * 
     * @param speed Speed of the DriveTrain
     */
    public void set(double speed) {
        leftMaster.set(speed);
        leftSlave.set(speed);
        rightMaster.set(speed);
        rightSlave.set(speed);
    }
    
    /**
     * 
     * @param lSpeed Left Side Speed
     * @param rSpeed Right Side Speed
     */
    public void set(double lSpeed, double rSpeed) {
        leftMaster.set(lSpeed);
        leftSlave.set(lSpeed);
        rightMaster.set(rSpeed);
        rightSlave.set(rSpeed);
    }

    /**
     * 
     * @param distance Distance to Travel in Inches
     * @param speed Speed to Travel At
     */
    public void driveDistance(double distance, double speed) {

        resetEncoders();

        if (distance < 0) {

            do {

                set(-speed, -speed);

            } while (getWheelAverage() < Units.inchesToMeters(distance - 0.25) ||
                    getWheelAverage() > Units.inchesToMeters(distance + 0.25));

        } else if (distance > 0) {

            do {

                set(speed, speed);

            } while (getWheelAverage() < Units.inchesToMeters(distance - 0.65) ||
                    getWheelAverage() > Units.inchesToMeters(distance + 0.65));

        }

        set(0, 0);

    }


    public void turnTo(double angle, double power){
        gyro.reset();

        if(angle < 0){
            set(power, -power);
            while(gyro.getAngle() > angle){

            }
            set(0);
        }else{
            set(-power, power);
            while(gyro.getAngle() < angle){

            }
            set(0);
        }
    
    }

    public double getLeftDistanceTraveled() {
        return (getLeftEncoderPos() * (Units.inchesToMeters(Constants.DriveTrainWheelRadius) * 2 * Math.PI));
    }

    public double getRightDistanceTraveled() {
        return (getRightEncoderPos() * (Units.inchesToMeters(Constants.DriveTrainWheelRadius) * 2 * Math.PI));
    }

    public double getLeftEncoderPos() {
        return (m_leftEncoder.getPosition() - leftEncoderZero) / Constants.DriveTrainGearRatio;
    }

    public double getRightEncoderPos() {
        return (m_rightEncoder.getPosition() - rightEncoderZero) / Constants.DriveTrainGearRatio;
    }

    public double getWheelAverage() {
        return (getLeftDistanceTraveled() + getRightDistanceTraveled()) / 2;
    }

    public void resetEncoders() {
        leftEncoderZero = m_leftEncoder.getPosition();
        rightEncoderZero = m_rightEncoder.getPosition();
    }

}
