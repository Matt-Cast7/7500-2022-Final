package frc.robot.subsystems;

import java.util.ArrayList;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.MotorDirections;

public class Index extends SubsystemBase {

    private final CANSparkMax leftMotor = new CANSparkMax(Constants.Indexer[0], MotorType.kBrushless);
    private final CANSparkMax rightMotor = new CANSparkMax(Constants.Indexer[1], MotorType.kBrushless);

    private final RelativeEncoder m_leftEncoder = leftMotor.getEncoder();
    private final RelativeEncoder m_rightEncoder = rightMotor.getEncoder();

    
    private static double leftEncoderZero = 0;
    private static double rightEncoderZero = 0;

    private final ColorSensorV3 frontIndexColorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    private final ColorSensorV3 backIndexColorSensor = new ColorSensorV3(I2C.Port.kMXP);

    private final DigitalInput shooterEntry = new DigitalInput(Constants.IndexLimitSwitch);

    private final Servo servo = new Servo(0);

    private boolean flipIndex;

    private final Color blueBall = new Color(0.136, 0.288, 0.218);
    private final Color redBall = new Color(0.266, 0.286, 0.139);
    private final Color greenBall = new Color(0.501, 0.1040, 0.260);

    private final ColorMatch colorMatcher = new ColorMatch();

    // private final Ball AllianceColor = (DriverStation.getAlliance() ==
    // DriverStation.Alliance.Red) ? Ball.RED : Ball.BLUE;

   public final Ball AllianceColor = Ball.RED;

    private static ArrayList<Ball> indexedBalls = new ArrayList<Ball>();

    public ArrayList<Ball> getIndexBalls(){
        return indexedBalls;
    }
    public void updateIndexedBalls(Ball ball1, Ball ball2){
        indexedBalls.set(0, ball1);
        indexedBalls.set(1, ball2);
    }

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
       //System.out.println(detectBackIndexBalls().toString());
       //backSensorToString();
       //frontSensorToString();
       //backSensorToString();
       //System.out.println(ballInBack());
       //System.out.println(servo.get());
    }

    public void closeServo(){
        servo.set(1);
    }


    public void openServo(){
        servo.set(0);
    }

    



    public void setIndex(double speed) {
        rightMotor.set(speed);
        leftMotor.set(speed);
    }
    public void setIndex(double lspeed, double rspeed) {
        rightMotor.set(rspeed);
        leftMotor.set(lspeed);
    }

    public void setLeft(double speed){
        leftMotor.set(speed);
    }

    public void setRight(double speed){
        rightMotor.set(speed);
    }
    
    public void stop() {
        rightMotor.set(0);
        leftMotor.set(0);
    }

    public void enableIndex() {
        
        setIndex(0.45);

    }

    boolean indexBallBoolean = false;
    public boolean indexBall(){
        return indexBallBoolean;
    }

    public void indexBall(boolean index){
        indexBallBoolean = index;
    }
    


    boolean spitBallBoolean = false;
    public boolean spitBall(){
        return indexBallBoolean;
    }

    public void spitBall(boolean index){
        spitBallBoolean = index;
    }
    

    public boolean getShooterEntry(){
        return shooterEntry.get();
    }

    public double getFrontProximity(){
        return frontIndexColorSensor.getProximity();
    }

    public double getBackProximity(){
        return backIndexColorSensor.getProximity();
    }

    public boolean ballInBack(){
        if(detectBackIndexBalls() != Ball.NONE){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean ballInFront(){
        if(detectFrontIndexBalls() != Ball.NONE){
            return true;
        }else{
            return false;
        }
    }

    public Ball detectBackIndexBalls() {
        ColorMatchResult match = colorMatcher.matchClosestColor(backIndexColorSensor.getColor());

        if (backIndexColorSensor.getProximity() > 230) {
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

        if (frontIndexColorSensor.getProximity() > 65) {
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

    public double getLeftDistanceTraveled() {
        return (getLeftEncoderPos() * (Constants.indexRadius * 2 * Math.PI));
    }

    public double getRightDistanceTraveled() {
        return (getRightEncoderPos() * (Constants.indexRadius * 2 * Math.PI));
    }

    public double getLeftEncoderPos() {
        return (m_leftEncoder.getPosition() - leftEncoderZero) / Constants.indexGearRatio;
    }

    public double getRightEncoderPos() {
        return (m_rightEncoder.getPosition() - rightEncoderZero) / Constants.indexGearRatio;
    }

    public void resetEncoders() {
        leftEncoderZero = m_leftEncoder.getPosition();
        rightEncoderZero = m_rightEncoder.getPosition();
    }


}

// public Thread TTenableIndex() {
    //     Thread t = new Thread(() -> {
    //         if (!(detectFrontIndexBalls() == Ball.NONE)) {

    //             if (detectFrontIndexBalls() == AllianceColor) {
    //                 if (detectBackIndexBalls() == Ball.NONE) {
    //                     while (detectBackIndexBalls() == Ball.NONE) {
    //                         setIndex(0.25);
    //                     }
    //                     setIndex(0);

    //                 } else {
    //                     while (detectFrontIndexBalls() != Ball.NONE) {
    //                         setIndex(0.25);
    //                     }
    //                     setIndex(0);

    //                 }
    //             } else {
    //                 if (detectBackIndexBalls() != Ball.NONE) {
    //                     GlobalCommandControl.spitCommand = true;
    //                     setIndex(0.25);
    //                     while(!shooterEntry.get()){

    //                     }
    //                     stop();
    //                     GlobalCommandControl.spitCommand = false;
    //                 } else {
    //                     MotorDirections.FlipIntake = true;
                        
    //                     Timer timer = new Timer();
    //                     timer.start();

    //                     while(timer.get() < 1.5){

    //                     }

    //                     MotorDirections.FlipIntake = false;

    //                 }

    //             }

    //         }

    //     });

    //     return t;
    // }


    // public Thread initFiring(){
    //     Thread t = new Thread( () -> {

    //         Timer timer = new Timer();
    //         timer.start();


    //         setIndex(0.25);
    //         while(timer.get() < 1.0){

    //         }
    //         stop();
            
            
    //         timer.stop();

    //         setIndex(-0.1);
    //         while(frontIndexColorSensor.getProximity() < 65){

    //         }
    //         stop();




    //     });

    //     return t;
    // }

    // public Thread fireBall(){
    //     Thread t = new Thread( () -> {
            
    //         setIndex(0.25);
    //         while(!shooterEntry.get()){

    //         }
    //         stop();
            
    //         Timer timer = new Timer();
    //         timer.start();
    //         while(timer.get() < 0.5){

    //         }
    //         timer.stop();

    //         setIndex(0.25);
    //         while(!shooterEntry.get()){

    //         }
    //         stop();

    //     });

    //     return t;
    // }