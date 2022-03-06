package frc.robot.commands.driving;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDrive extends CommandBase{
    
    private DriveTrain m_DriveTrain;

    private DoubleSupplier m_forwardPower;
    private DoubleSupplier m_turnPower;

    public ArcadeDrive(DriveTrain m_DriveTrain, DoubleSupplier m_leftPower, DoubleSupplier m_rightPower){
        this.m_DriveTrain = m_DriveTrain;
        this.m_forwardPower = m_leftPower;
        this.m_turnPower = m_rightPower;
        addRequirements(m_DriveTrain);
    }

    @Override
    public void execute(){
        m_DriveTrain.set(m_forwardPower.getAsDouble() + m_turnPower.getAsDouble(), m_forwardPower.getAsDouble() - m_turnPower.getAsDouble());
    }

    @Override
    public void end(boolean interrupted){
        m_DriveTrain.set(0);
    }

    public boolean isFinished() {
        return false;
    }
    

}
