package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Deployer;

public class RetractIntake extends CommandBase{

    private Deployer m_deployer;
    private Timer cutOffTimer;

    public RetractIntake(Deployer m_deployer){
        this.m_deployer = m_deployer;
        cutOffTimer = new Timer();

        addRequirements(m_deployer);
    }

    public void initialize(){
        cutOffTimer.start();
    }

    public void execute(){
        m_deployer.setDeployerSpeed(-0.3);
    }


    public void end(boolean interrupted){
        m_deployer.setDeployerSpeed(0);
    }

    public boolean isFinished(){
        if(cutOffTimer.get() > 1.0){
            m_deployer.setDeployState(false);
            cutOffTimer.stop();
            cutOffTimer.reset();
            return true;
        }else{
            return false;
        }
    }
    
}
