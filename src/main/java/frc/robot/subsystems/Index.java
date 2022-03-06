package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.GlobalCommandControl;
import frc.robot.MotorDirections;

public class Index extends SubsystemBase {

    private final CANSparkMax leftMotor = new CANSparkMax(Constants.Indexer[0], MotorType.kBrushless);
    private final CANSparkMax rightMotor = new CANSparkMax(Constants.Indexer[1], MotorType.kBrushless);

    private final ColorSensorV3 frontIndexColorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    private final ColorSensorV3 backIndexColorSensor = new ColorSensorV3(I2C.Port.kMXP);

    private final DigitalInput shooterEntry = new DigitalInput(Constants.IndexLimitSwitch);

    private boolean flipIndex;

    private final Color blueBall = new Color(0.136, 0.288, 0.218);
    private final Color redBall = new Color(0.266, 0.286, 0.139);
    private final Color greenBall = new Color(0.501, 0.1040, 0.260);

    private final ColorMatch colorMatcher = new ColorMatch();

    // private final Ball AllianceColor = (DriverStation.getAlliance() ==
    // DriverStation.Alliance.Red) ? Ball.RED : Ball.BLUE;

    private final Ball AllianceColor = Ball.RED;

    public enum Ball {
        RED,
        BLUE,
        GREEN,
        NONE
    }

    public Index() {

        colorMatcher.addColorMatch(blueBall);
        colorMatcher.addColorMatch(redBall);
        colorMatcher.addColorMatch(greenBall);

        flipIndex = MotorDirections.FlipIndex;

        leftMotor.setInverted(!flipIndex);
        rightMotor.setInverted(flipIndex);

        leftMotor.setIdleMode(IdleMode.kBrake);
        rightMotor.setIdleMode(IdleMode.kBrake);
    }



    public void periodic() {
        if (flipIndex != MotorDirections.FlipIndex) {
            flipIndex = MotorDirections.FlipIndex;
            leftMotor.setInverted(!flipIndex);
            rightMotor.setInverted(flipIndex);
        }
    }




    public void setIndex(double speed) {
        rightMotor.set(speed);
        leftMotor.set(speed);
    }
    
    public void stop() {
        rightMotor.set(0);
        leftMotor.set(0);
    }

    public void enableIndex() {
        setIndex(0.25);

    }

    public Thread TTenableIndex() {
        Thread t = new Thread(() -> {
            if (!(detectFrontIndexBalls() == Ball.NONE)) {

                if (detectFrontIndexBalls() == AllianceColor) {
                    if (detectBackIndexBalls() == Ball.NONE) {
                        while (detectBackIndexBalls() == Ball.NONE) {
                            setIndex(0.25);
                        }
                        setIndex(0);

                    } else {
                        while (detectFrontIndexBalls() != Ball.NONE) {
                            setIndex(0.25);
                        }
                        setIndex(0);

                    }
                } else {
                    if (detectBackIndexBalls() != Ball.NONE) {
                        GlobalCommandControl.spitCommand = true;
                        setIndex(0.25);
                        while(!shooterEntry.get()){

                        }
                        stop();
                        GlobalCommandControl.spitCommand = false;
                    } else {
                        MotorDirections.FlipIntake = true;
                        
                        Timer timer = new Timer();
                        timer.start();

                        while(timer.get() < 1.5){

                        }

                        MotorDirections.FlipIntake = false;

                    }

                }

            }

        });

        return t;
    }


    public Thread initFiring(){
        Thread t = new Thread( () -> {

            Timer timer = new Timer();
            timer.start();


            setIndex(0.25);
            while(timer.get() < 1.0){

            }
            stop();
            
            
            timer.stop();

            setIndex(-0.1);
            while(frontIndexColorSensor.getProximity() < 65){

            }
            stop();




        });

        return t;
    }

    public Thread fireBall(){
        Thread t = new Thread( () -> {
            
            setIndex(0.25);
            while(!shooterEntry.get()){

            }
            stop();
            
            Timer timer = new Timer();
            timer.start();
            while(timer.get() < 0.5){

            }
            timer.stop();

            setIndex(0.25);
            while(!shooterEntry.get()){

            }
            stop();

        });

        return t;
    }

    

    public Ball detectBackIndexBalls() {
        ColorMatchResult match = colorMatcher.matchClosestColor(backIndexColorSensor.getColor());

        if (backIndexColorSensor.getProximity() > 340) {
            if (match.color == blueBall) {
                return Ball.BLUE;
            } else if (match.color == redBall) {
                return Ball.RED;
            } else if (match.color == greenBall) {
                return Ball.GREEN;
            }
        }
        return Ball.NONE;
    }

    public Ball detectFrontIndexBalls() {
        ColorMatchResult match = colorMatcher.matchClosestColor(frontIndexColorSensor.getColor());

        if (frontIndexColorSensor.getProximity() > 75) {
            if (match.color == blueBall) {
                return Ball.BLUE;
            } else if (match.color == redBall) {

                return Ball.RED;
            } else if (match.color == greenBall) {
                return Ball.GREEN;
            }
        }
        return Ball.NONE;

    }

    public void frontSensorToString() {
        System.out.println("Blue " + frontIndexColorSensor.getBlue() + ": Red " +
                frontIndexColorSensor.getRed() + ": Green " + frontIndexColorSensor.getGreen()
                + " : " +
                frontIndexColorSensor.getProximity());
    }

    public void backSensorToString() {
        System.out.println("Blue " + backIndexColorSensor.getBlue() + ": Red " +
                backIndexColorSensor.getRed() + ": Green " + backIndexColorSensor.getGreen()
                + " : " +
                backIndexColorSensor.getProximity());
    }

}