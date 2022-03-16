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

    @Override
    public void initialize(){
        cutOffTimer.reset();
        cutOffTimer.start();
    }

    @Override
    public void execute(){
        m_Index.setIndex(0.25);
    }
    
    @Override
    public boolean isFinished(){
        if(cutOffTimer.get() > 3){
            cutOffTimer.stop();
            cutOffTimer.reset();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void end(boolean interrupted){
        m_Index.stop();
        cutOffTimer.stop();
            cutOffTimer.reset();
    }
    
}
