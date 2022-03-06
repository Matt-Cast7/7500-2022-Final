package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Deployer;

public class ToggleDeployer extends CommandBase{
    
    private Deployer m_deployer;

    public ToggleDeployer(Deployer m_deployer){
        this.m_deployer = m_deployer;
        addRequirements(m_deployer);
    }

    public void execute(){
        m_deployer.toggle();
    }
    

    public boolean isFinihsed(){
        return true;
    }

    public void end(){
        m_deployer.stopDeployer();
    }
}
