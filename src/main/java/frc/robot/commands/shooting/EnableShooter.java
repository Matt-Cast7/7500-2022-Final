package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class EnableShooter extends CommandBase{

    private Shooter m_Shooter;

    public EnableShooter(Shooter m_shooter){
        this.m_Shooter = m_shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize(){
        m_Shooter.resetPID();
    }

    @Override
    public void execute(){

        m_Shooter.enable();
    }


    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        m_Shooter.stop();
    }
    
}
