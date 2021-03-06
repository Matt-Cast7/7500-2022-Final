package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

public class SpitBall extends CommandBase{

    private Index m_Index;
    private Shooter m_Shooter;
    private Timer cutOffTimer;

    public SpitBall(Index m_Index, Shooter m_Shooter){
        this.m_Index = m_Index;
        this.m_Shooter = m_Shooter;
        cutOffTimer = new Timer();
        addRequirements(m_Index, m_Shooter);
    }

    @Override
    public void initialize(){
        cutOffTimer.start();
        m_Shooter.setShooter(0.25);
    }

    @Override
    public void execute(){
        m_Index.setIndex(0.25);
    }

    @Override
    public boolean isFinished(){
        if(cutOffTimer.get() > 4){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void end(boolean interrupted){
        cutOffTimer.stop();
        cutOffTimer.reset();

        m_Index.stop();
        m_Shooter.stop();
    }


    
}
