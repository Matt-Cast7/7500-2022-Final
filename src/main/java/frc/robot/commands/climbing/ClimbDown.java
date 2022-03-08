package frc.robot.commands.climbing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimbDown extends CommandBase{

    private Climber m_Climber;

    public ClimbDown(Climber m_Climber){
        this.m_Climber = m_Climber;
        addRequirements(m_Climber);
    }

    @Override
    public void execute(){
        m_Climber.setClimber( -0.35, -0.35);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        m_Climber.setClimber(0);
    }


    
}
