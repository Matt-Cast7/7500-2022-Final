package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class AutoIndexAdjust extends CommandBase{

    private Index m_Index;
    private boolean end;

    public AutoIndexAdjust(Index m_Index){
        this.m_Index = m_Index;
        addRequirements(m_Index);
    }

    @Override
    public void initialize(){
        end = false;
        m_Index.resetEncoders();
    }

    @Override
    public void execute(){
        if(indexDistance(-0.5, 0.15)){
            end = true;
        }else{

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
