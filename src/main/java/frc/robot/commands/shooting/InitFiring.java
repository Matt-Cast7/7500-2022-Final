package frc.robot.commands.shooting;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class InitFiring extends CommandBase{

    private Index m_Index;

    private Thread initThread;

    public InitFiring(Index m_Index){
        this.m_Index = m_Index;
        addRequirements(m_Index);
    }

    public void initialize(){
        initThread = m_Index.initFiring();
        initThread.start();
    }

    public void execute(){

    }

    public boolean isFinished(){
        if(initThread.isAlive()){
            return false;
        }else{
            return true;
        }
    }
    
    public void end(){
        m_Index.stop();
    }
}
