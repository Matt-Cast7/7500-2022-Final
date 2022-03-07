package frc.robot.commands.climbing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimbUp extends CommandBase{

    private Climber m_Climber;

    public ClimbUp(Climber m_Climber){
        addRequirements(m_Climber);
        this.m_Climber = m_Climber;
    }

    public void execute(){
        m_Climber.setClimber(0.5, 0.5);
    }

    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        m_Climber.setClimber(0);
    }


    
}
