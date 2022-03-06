package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class RunIndex extends CommandBase{

    private Index m_Index;
    private Thread runIndex;

    public RunIndex(Index m_Index){
        this.m_Index = m_Index;
        addRequirements(m_Index);
    }

    public void initialize(){
        runIndex = m_Index.TTenableIndex();
        runIndex.start();
    }
    
    public void execute(){

        if(runIndex.isAlive()){

        }else{
            runIndex.start();
        }
    }

    public boolean isFinished(){
        return false;
    }

    public void end(){
        runIndex.interrupt();
        m_Index.stop();
    }

}
