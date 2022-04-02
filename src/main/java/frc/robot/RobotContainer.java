package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.driving.*;
import frc.robot.commands.intaking.*;
import frc.robot.commands.shooting.*;
import frc.robot.commands.auto.*;
import frc.robot.commands.climbing.ClimbDown;
import frc.robot.commands.climbing.ClimbUp;
import frc.robot.subsystems.*;

public class RobotContainer {

    private DriveTrain m_DriveTrain;
    private Index m_Index;
    private Intake m_Intake;
    private Deployer m_Deployer;
    private Shooter m_Shooter;
    private Climber m_Climber;

    private Trigger manualControlTrigger;

    private Command tankDrive,
            arcadeDrive,

            runIntake,
            runIndex,
            runIndexReverse,

            deployIntake,
            retractIntake,

            toggleGoal,
            fireBall,

            primeIndex,

            runShooter,
            spitBall,

            moveFromTarmac,

            climbUp,
            climbDown,

            autoIndex, 
            
            autoAim, 
            autoAdjust,
            
            autoShootBall,
            autoShoot,
            
            runDeployer,
            
            autoIndexAdjust,
            toggleShooter,
            
            enableShooter;

    private Joystick m_leftJoystick, m_rightJoystick, buttonBox;

    private JoystickButton
            Rtrigger,
            Rbutton2,
            Rbutton4,
            Rbutton5,
            Rbutton6,

            Lbutton7,
            Lbutton9,
            Lbutton10,
            Lbutton11,
            Lbutton12, 

            toggle,
            green,
            red,
            black,
            yellow,
            blue;


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

        manualControlTrigger = toggle.negate();

        

        Shuffleboard.getTab("TeleOp").addBoolean("Driving Mode", () -> manualControlTrigger.getAsBoolean());


    }

    public void testCommands(){

    }

    public void teleOpCommands() {
        getArcadeDrive().schedule();


        
        // Commands
        // ------
        runIntake = new RunIntake(m_Intake);

        deployIntake = new DeployIntake(m_Deployer);
        retractIntake = new RetractIntake(m_Deployer);

        runShooter = new RunShooter(m_Shooter, () -> toggle.get());
        primeIndex = new PrimeIndex(m_Index);

        fireBall = new Fireball(m_Index);

        runIndex = new RunIndex(m_Index);
        runIndexReverse = new RunIndexReverse(m_Index);

        toggleGoal = new ToggleGoal(m_Shooter);

        climbUp = new ClimbUp(m_Climber);
        climbDown = new ClimbDown(m_Climber);

        autoIndex = new AutoIndexing(m_Index, m_Shooter);

        autoAim = new AdjustRotation(m_DriveTrain, () -> toggle.get());
        autoAdjust = new AdjustRange(m_DriveTrain);

        spitBall = new SpitBall(m_Index, m_Shooter);

        runDeployer = new RunDeployer(m_Deployer);

        autoIndexAdjust = new AutoIndexAdjust(m_Index);
        autoShoot = new AutoShoot(m_Index);

        toggleShooter = new ToggleShooter(m_Shooter);

        m_Index.setDefaultCommand(autoIndex);

        enableShooter = new EnableShooter(m_Shooter);

        //ParallelCommandGroup highGoal = new ParallelCommandGroup(autoAim, new SequentialCommandGroup(autoIndexAdjust, autoShoot), runShooter);
       // ParallelCommandGroup lowGoal = new ParallelCommandGroup(new SequentialCommandGroup(autoIndexAdjust, autoShoot), runShooter);
      //  Trigger goal = new Trigger(() -> m_Shooter.getTargetGoal());
        // ------

        // Deploy/Retract intake with black button
        // ------
        Trigger deployedStateTrigger = new Trigger(() -> m_Deployer.isDeployed());

        black.and(deployedStateTrigger).whenActive(retractIntake);
        black.and(deployedStateTrigger.negate()).whenActive(deployIntake);
        // ------

        Rtrigger.whenHeld(runIntake).whenHeld(runDeployer).cancelWhenPressed(runShooter).whenReleased(retractIntake);

        //Rbutton2.cancelWhenPressed(arcadeDrive).whenHeld(new SequentialCommandGroup(new ParallelCommandGroup(autoAim, autoIndexAdjust, runShooter), autoShoot)).whenReleased(arcadeDrive).whenReleased(toggleShooter);
       Rbutton2.cancelWhenPressed(arcadeDrive).whenHeld(new ParallelCommandGroup(autoAim, new SequentialCommandGroup(autoIndexAdjust, autoShoot), runShooter)).whenReleased(arcadeDrive);
      
       //Rbutton2.and(goal).cancelWhenActive(arcadeDrive).whileActiveOnce(highGoal).whenInactive(arcadeDrive);
       //Rbutton2.and(goal).negate().whileActiveOnce(lowGoal);

        Rbutton5.whenPressed(toggleGoal);

        Lbutton9.whenPressed(autoIndexAdjust);  
        
        Lbutton11.or(Rbutton4).whileActiveOnce(climbDown);
        Lbutton12.or(Rbutton6).whileActiveOnce(climbUp);

        //SequentialCommandGroup primmingGroup = new SequentialCommandGroup(primeIndex, runShooter);
        green.toggleWhenPressed(enableShooter);

        red.whenPressed(primeIndex);

        blue.and(manualControlTrigger.negate()).toggleWhenActive(autoIndex);

        blue.and(manualControlTrigger).whileActiveOnce(runIndex);
        yellow.and(manualControlTrigger).whileActiveOnce(runIndexReverse);

    }

   

    public SequentialCommandGroup simpleAuto() {
        moveFromTarmac = new MoveFromTarmac(m_DriveTrain);
        //deployIntake = new DeployIntake(m_Deployer);

        autoShootBall = new AutoShootBall(m_Shooter, m_Index);

        return new SequentialCommandGroup(moveFromTarmac, autoShootBall);
    }

    public Command getArcadeDrive() {
        arcadeDrive = new ArcadeDrive(m_DriveTrain,
                () -> getLeftYAdjusted(),
                () -> getRightXAdjusted());
        return arcadeDrive;

    }

    public Command getTankDrive() {
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
        Rtrigger = new JoystickButton(m_rightJoystick, 1);
        Rbutton2 = new JoystickButton(m_rightJoystick, 2);
        Rbutton4 = new JoystickButton(m_rightJoystick, 4);
        Rbutton5 = new JoystickButton(m_rightJoystick, 5);
        Rbutton6 = new JoystickButton(m_rightJoystick, 6);

        Lbutton7 = new JoystickButton(m_leftJoystick, 7);
        Lbutton9 = new JoystickButton(m_leftJoystick, 9);
        Lbutton10 = new JoystickButton(m_leftJoystick, 10);
        Lbutton11 = new JoystickButton(m_leftJoystick, 11);
        Lbutton12 = new JoystickButton(m_leftJoystick, 12);
        
        toggle = new JoystickButton(buttonBox, 1);
        green = new JoystickButton(buttonBox, 2);
        red = new JoystickButton(buttonBox, 3);
        black = new JoystickButton(buttonBox, 4);
        yellow = new JoystickButton(buttonBox, 5);
        blue = new JoystickButton(buttonBox, 6);

    }

}
