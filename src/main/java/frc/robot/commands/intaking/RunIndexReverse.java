package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class RunIndexReverse extends CommandBase{

    private Index m_Index;

    public RunIndexReverse(Index m_Index){
        this.m_Index = m_Index;
        addRequirements(m_Index);
    }

    public void initialize(){
        
    }
    
    public void execute(){
        m_Index.setIndex(-0.15);
    }

    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        m_Index.setIndex(0);
    }

}
