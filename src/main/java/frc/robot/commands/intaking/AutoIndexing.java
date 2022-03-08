package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

public class AutoIndexing extends CommandBase{

    private Index m_Index;
    private Shooter m_Shooter;
    private Timer cutOffTimer;

    public AutoIndexing(Index m_Index, Shooter m_Shooter){
        this.m_Index = m_Index;
        this.m_Shooter = m_Shooter;
        addRequirements(m_Index, m_Shooter);
    }
    
    

    

    
}
