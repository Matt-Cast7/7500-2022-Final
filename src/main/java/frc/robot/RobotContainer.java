package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.driving.*;
import frc.robot.commands.intaking.*;
import frc.robot.commands.shooting.*;
import frc.robot.commands.ClimbDown;
import frc.robot.commands.ClimbUp;
import frc.robot.commands.auto.*;
import frc.robot.subsystems.*;

public class RobotContainer {

    private DriveTrain m_DriveTrain;
    private Index m_Index;
    private Intake m_Intake;
    private Deployer m_Deployer;
    private Shooter m_Shooter;
    private Climber m_Climber;

    private Command tankDrive,
            arcadeDrive,

            runIntake,
            runIndex,

            deployIntake,
            retractIntake,
            toggleDeployer,

            toggleGoal,
            initFiring,
            fireBall,

            runShooter,
            
            spitBall,
            
            moveFromTarmac,
            
            climbUp,
            climbDown,
            
            indexBall,
            scanIntaking;

    private Joystick m_leftJoystick, m_rightJoystick, buttonBox;

    private JoystickButton Ltrigger,
            Rtrigger,

            toggle,
            green,
            red,
            black,
            yellow,
            blue;

    private Trigger spit;

    public RobotContainer() {
        m_leftJoystick = new Joystick(Constants.LeftJoyStick);
        m_rightJoystick = new Joystick(Constants.RightJoyStick);

        buttonBox = new Joystick(Constants.ButtonBox);

        m_DriveTrain = new DriveTrain();

        m_Intake = new Intake();
        m_Index = new Index();
        m_Shooter = new Shooter();
        m_Deployer = new Deployer();
        m_Climber = new Climber();

        configureButtons();
    }




    public void teleOpCommands() {
        getArcadeDrive().schedule();

        

        //Black : Toggle Goal
        //------
        toggleGoal = new ToggleGoal(m_Shooter);
        //black.whenPressed(toggleGoal);
        //------

        //Blue : Toggle Deployer
        //------
        Trigger deployed =  new Trigger(() -> m_Deployer.getDeployState());
        deployIntake = new DeployIntake(m_Deployer);
        retractIntake = new RetractIntake(m_Deployer);
        
        //toggleDeployer = new ToggleDeployer(m_Deployer);
        deployed.and(blue).whenActive(retractIntake);
        deployed.negate().and(blue).whenActive(deployIntake);
        //------

        //Green : init Firing
        //------
        //initFiring = new InitFiring(m_Index);
        runShooter = new RunShooter(m_Shooter);
        //initFiring.andThen(runShooter);
        green.toggleWhenPressed(runShooter);
        //------

        //Red : Fire Ball
        //------
        fireBall = new Fireball(m_Index);
        red.whenPressed(fireBall);

        //------

        //Shooter Spit Trigger
        //------
        spitBall = new SpitBall(m_Shooter);
        spit = new Trigger(() -> GlobalCommandControl.spit());
        spit.whenActive(spitBall);
        //------


        //Trigger : Run Intake/Index
        //------
        runIntake = new RunIntake(m_Intake);
        scanIntaking = new ScanIntaking(m_Index);
        indexBall = new IndexBall(m_Index);
        runIndex = new RunIndex(m_Index);
       // Rtrigger.cancelWhenPressed(initFiring).whenHeld(runIntake);
       Rtrigger.cancelWhenPressed(runShooter).whenHeld(runIndex).whenHeld(runIntake); 
       //------

        climbUp = new ClimbUp(m_Climber);
        climbDown = new ClimbDown(m_Climber);

        yellow.whenHeld(climbUp);
        black.whenHeld(climbDown);
        
        
    }

    public SequentialCommandGroup simpleAuto(){
        moveFromTarmac = new MoveFromTarmac(m_DriveTrain);
        deployIntake = new DeployIntake(m_Deployer);

        return new SequentialCommandGroup(moveFromTarmac, deployIntake);
    }




    public Command getArcadeDrive() {
        arcadeDrive = new ArcadeDrive(m_DriveTrain,
                () -> getLeftYAdjusted(),
                () -> getRightXAdjusted());
        return arcadeDrive;

    }

    public Command getTankDrive(){
        tankDrive = new TankDrive(m_DriveTrain,
        () -> getLeftYAdjusted(), 
        () -> getRightYAdjusted());
    
        return tankDrive;
      }


    public double getLeftYAdjusted() {
        return -getLeftY() * getSensitvity();
    }

    public double getRightYAdjusted() {
        return -getRightY() * getSensitvity();
    }

    public double getRightXAdjusted() {
        return getRightX() * getSensitvity();
    }

    public double getLeftY() {
        double val = m_leftJoystick.getY();
        if (Math.abs(val) <= Constants.ControllerDeadzone) {
            return 0;
        } else {
            return val;
        }
    }

    public double getRightY() {
        double val = m_rightJoystick.getY();
        if (Math.abs(val) <= Constants.ControllerDeadzone) {
            return 0;
        } else {
            return val;
        }
    }

    public double getRightX() {
        double val = m_rightJoystick.getX();
        if (Math.abs(val) <= Constants.ControllerDeadzone) {
            return 0;
        } else {
            return val;
        }
    }

    public double getSensitvity() {
        return MathUtil.clamp((((-m_leftJoystick.getThrottle()) + 1) / 2) + 0.1, 0.1, 1);
    }

    public void configureButtons() {
        Ltrigger = new JoystickButton(m_leftJoystick, 1);
        Rtrigger = new JoystickButton(m_rightJoystick, 1);

        toggle = new JoystickButton(buttonBox, 1);
        green = new JoystickButton(buttonBox, 2);
        red = new JoystickButton(buttonBox, 3);
        black = new JoystickButton(buttonBox, 4);
        yellow = new JoystickButton(buttonBox, 5);
        blue = new JoystickButton(buttonBox, 6);

    }

}
