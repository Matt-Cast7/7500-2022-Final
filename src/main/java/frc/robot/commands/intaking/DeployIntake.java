package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Deployer;

public class DeployIntake extends CommandBase{

    private Deployer m_deployer;    
    private Timer cutOffTimer;

    public DeployIntake(Deployer m_deployer){
        this.m_deployer = m_deployer;
        cutOffTimer = new Timer();
        addRequirements(m_deployer);
    }

    public void initialize(){
        cutOffTimer.start();
    }

    public void execute(){
        m_deployer.setDeployerSpeed(.18);
    }

    public void end(boolean interrupted){
        m_deployer.setDeployerSpeed(0);
    }

    public boolean isFinished(){
        if(cutOffTimer.get() > 1.5){
            m_deployer.setDeployState(true);
            cutOffTimer.stop();
            cutOffTimer.reset();
            return true;
        }else{
            return false;
        }
    }
    
}
