package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Deployer;

public class RunDeployer extends CommandBase{
    
    private Deployer m_Deployer;

    public RunDeployer(Deployer m_Deployer){

        this.m_Deployer = m_Deployer;

        addRequirements(m_Deployer);
    }

    @Override
    public void execute(){
        m_Deployer.setDeployerSpeed(0.35);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        m_Deployer.setDeployerSpeed(0);
    }
}
