package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Rotate180 extends CommandBase{

    private DriveTrain m_DriveTrain;

    public Rotate180(DriveTrain m_DriveTrain){
        this.m_DriveTrain = m_DriveTrain;
        addRequirements(m_DriveTrain);
    }

    public void execute(){
        
    }
    
}
