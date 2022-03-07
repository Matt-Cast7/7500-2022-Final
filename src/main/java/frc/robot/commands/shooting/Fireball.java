package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class Fireball extends CommandBase{

    private Index m_Index;
    private Timer cutOffTimer;

    public Fireball(Index m_Index){
        this.m_Index = m_Index;
        cutOffTimer = new Timer();
        addRequirements(m_Index);
    }

    public void initialize(){
        cutOffTimer.start();
    }

    public void execute(){
        m_Index.setIndex(0.45);
    }
    
    public boolean isFinished(){
        if(cutOffTimer.get() > 5){
            return true;
        }else{
            return false;
        }
    }

    public void end(){
        m_Index.stop();
    }
    
}
