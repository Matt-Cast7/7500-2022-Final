package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase{

    private Shooter m_shooter;

    public RunShooter(Shooter m_shooter){
        this.m_shooter = m_shooter;
        addRequirements(m_shooter);
    }

    public void initialize(){
        m_shooter.resetPID();
    }

    public void execute(){
        m_shooter.enable();
    }


    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        m_shooter.stop();
    }
    
}
