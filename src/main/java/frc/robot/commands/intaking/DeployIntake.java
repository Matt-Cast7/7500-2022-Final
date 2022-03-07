package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Deployer;

public class DeployIntake extends CommandBase{

    private Deployer m_deployer;    
    private Timer timer;

    public DeployIntake(Deployer m_deployer){
        this.m_deployer = m_deployer;
        timer = new Timer();
        addRequirements(m_deployer);
    }

    public void initialize(){
        timer.start();
    }

    public void execute(){
        m_deployer.setDeployerSpeed(.18);
    }

    public void end(boolean interrupted){
        m_deployer.setDeployerSpeed(0);
    }

    public boolean isFinished(){
        if(timer.get() > 3.0){
            m_deployer.setDeployState(true);
            return true;
        }else{
            return false;
        }
    }
    
}
