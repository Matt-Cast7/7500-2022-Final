package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase{

    private Shooter m_Shooter;

    public RunShooter(Shooter m_shooter){
        this.m_Shooter = m_shooter;
        addRequirements(m_shooter);
    }

    public void initialize(){
        m_Shooter.resetPID();
    }

    public void execute(){
        m_Shooter.enableAutoAdjust();
        //System.out.println("aa");
    }


    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        m_Shooter.stop();
    }
    
}
