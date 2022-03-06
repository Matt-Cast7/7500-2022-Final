package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class Fireball extends CommandBase{

    private Index m_Index;

    private Thread fireBall;

    public Fireball(Index m_Index){
        this.m_Index = m_Index;
        addRequirements(m_Index);
    }

    public void initialize(){
        fireBall = m_Index.fireBall();
        fireBall.start();
    }

    public void execute(){

    }
    
    public boolean isFinished(){
        if(fireBall.isAlive()){
            return false;
        }else{
            return true;
        }
    }

    public void end(){
        m_Index.stop();
    }
    
}
