package frc.robot;

public final class Constants {


    //DriveTrain
    //------
    public static final int Motor_Left_Back = 3;
    public static final int Motor_Left_Front = 4;
    public static final int Motor_Right_Back = 5;
    public static final int Motor_Right_Front = 6;


    public static final double DriveTrainWheelRadius = 3.0;
    public static final double DriveTrainGearRatio = 10.71;

    public static final double turnkP = 0.03;
    public static final double turnkI = 0.00;
    public static final double turnkD = 0.00;
    //------

    
    //Deployer
    //------
    public static final int Deployer = 7;
    public static final int DeployerLimitSwitch = 8;
    //------


    //Intake
    //------
    public static final int Intake = 8;
    //------


    //Index
    //------
    public static final int[] Indexer = {9, 10};
    public static final int IndexLimitSwitch = 9;
    //------

    //Shooter
    //------
    public static final int[] Shooter = {11, 12};

    public static final double shooterGearing = 32.0/30.0;

    public static final double shooterKp = 0.0000001;
    public static final double shooterKi = 0.0003;
    public static final double shooterKd = 0;
    //------

    //Climber
    //------
    public static final int leftClimber = 17;
    public static final int rightClimber = 18;

    public static final double stringLength = 24;
    public static final double climberGearRatio = 1.0/20.0;

    public static final double pullyWitdh = 0.75;
    //------


    //Limelight
    //------
    public static final double a1 = 31;
    public static final double h1 = 23.625;
    public static final double h2 = 104;
    //------


    //Controllers
    //------
    public static final double ControllerDeadzone = 0.1;
    
    public static final int LeftJoyStick = 0;
    public static final int RightJoyStick = 1;
    public static final int ButtonBox = 2;
    //------


}
