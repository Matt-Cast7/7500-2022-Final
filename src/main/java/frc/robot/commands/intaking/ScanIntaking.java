package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Index.Ball;

public class ScanIntaking extends CommandBase{

    private Index m_Index;

    public ScanIntaking(Index m_Index){
        this.m_Index = m_Index;
    }

    public void execute(){

    }

    public boolean isFinished(){
        if(m_Index.detectFrontIndexBalls()  != Ball.NONE){
            return true;
        }else{
            return false;
        }
    }

    
}
