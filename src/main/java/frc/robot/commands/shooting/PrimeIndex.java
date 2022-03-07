package frc.robot.commands.shooting;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class PrimeIndex extends CommandBase{

    private Index m_Index;
    private Timer cutOffTimer;

    public PrimeIndex(Index m_Index){
        this.m_Index = m_Index;
        cutOffTimer = new Timer();
        addRequirements(m_Index);
    }

    @Override
    public void initialize(){
        cutOffTimer.start();

    }

    @Override
    public void execute(){
        m_Index.setIndex(-0.1);
    }

    @Override
    public boolean isFinished(){
        if(cutOffTimer.get() > 1.0){
            return true;
        }else{
            if(m_Index.getBackProximity() < 60){
                return true;
            }else{
                return false;
            }
        }
    }
    
    @Override
    public void end(boolean interrupted){
        m_Index.stop();
    }
}
