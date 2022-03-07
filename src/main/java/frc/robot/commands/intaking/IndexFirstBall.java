package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Index.Ball;

public class IndexFirstBall extends CommandBase{

    private Index m_Index;

    public IndexFirstBall(Index m_Index){
        this.m_Index = m_Index;
    }

    public void execute(){
        m_Index.setIndex(0.25);
    }

    public boolean isFinished(){
        if(m_Index.detectBackIndexBalls() != Ball.NONE){
            return true;
        }else{
            return false;
        }
    }

    public void end(){
        m_Index.setIndex(0);
    }
    
}
