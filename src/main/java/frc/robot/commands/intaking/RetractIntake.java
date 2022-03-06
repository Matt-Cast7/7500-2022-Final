package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Deployer;

public class RetractIntake extends CommandBase{

    private Deployer m_deployer;

    public RetractIntake(Deployer m_deployer){
        this.m_deployer = m_deployer;
        addRequirements(m_deployer);
    }

    public void execute(){
        m_deployer.retractIntake();
    }

    public void end(boolean interrupted){
        m_deployer.setDeployerSpeed(0);
    }

    public boolean isFinished(){
        return true;
    }
    
}
