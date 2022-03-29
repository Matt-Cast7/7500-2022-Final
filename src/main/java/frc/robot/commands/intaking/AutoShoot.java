package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class AutoShoot extends CommandBase{

    private Index m_Index;
    private boolean end;
    private Timer timer;

    public AutoShoot(Index m_Index){
        this.m_Index = m_Index;
        timer = new Timer();
        addRequirements(m_Index);
    }

    @Override
    public void initialize(){
        end = false;
        timer.stop();
        timer.reset();
        m_Index.resetEncoders();
        timer.start();
    }

    @Override
    public void execute(){
        if(timer.get() > 1.5){
            if(indexDistance(8, 0.5)){
                end = true;
            }else{
    
            }
        }
    }

    @Override
    public boolean isFinished(){
        return end;
    }

    @Override
    public void end(boolean interrupted){
        m_Index.stop();
    }

    public boolean indexDistance(double distance, double power){
        boolean end = false;
        if(distance > 0){
            if(m_Index.getLeftDistanceTraveled() > distance){
                m_Index.setLeft(0);
                end = true;
            }else{
                m_Index.setLeft(power);
                end = false;
            }
            if(m_Index.getRightDistanceTraveled() > distance){
                m_Index.setRight(0);
                end = true;
            }else{
                m_Index.setRight(power);
                end = false;

            }

        }else{
            if(m_Index.getLeftDistanceTraveled() < distance){
                m_Index.setLeft(0);
                end = true;
            }else{
                m_Index.setLeft(-power);
                end = false;
            }
            if(m_Index.getRightDistanceTraveled() < distance){
                m_Index.setRight(0);
                end = true;
            }else{
                m_Index.setRight(-power);
                end = false;
            }
        }
        return end;
    }


}
