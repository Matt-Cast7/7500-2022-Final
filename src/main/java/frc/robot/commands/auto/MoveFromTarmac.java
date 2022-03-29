package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class MoveFromTarmac extends CommandBase{

    private DriveTrain m_DriveTrain;

    public MoveFromTarmac(DriveTrain m_DriveTrain){
        this.m_DriveTrain = m_DriveTrain;
        addRequirements(m_DriveTrain);
    }

    public void execute(){
        m_DriveTrain.driveDistance(-24, 0.25);
    }

    public boolean isFinished(){
        return true;
    }

    public void end(boolean interrupted){
        m_DriveTrain.set(0);
    }
    
}
