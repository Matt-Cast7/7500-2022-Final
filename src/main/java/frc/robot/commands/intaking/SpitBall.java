package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MotorDirections;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Index.Ball;

public class SpitBall extends CommandBase{

    private Index m_Index;
    private Shooter m_Shooter;
    private boolean firstBall;
    private Timer indexTimer;
    private Timer cutOffTimer;

    public SpitBall(Index m_Index, Shooter m_Shooter){
        this.m_Index = m_Index;
        this.m_Shooter = m_Shooter;
        indexTimer = new Timer();
        cutOffTimer = new Timer();
        addRequirements(m_Index, m_Shooter);
    }

    @Override
    public void initialize(){
      
        cutOffTimer.start();

        if (m_Index.ballInBack()) {
            firstBall = false;
            indexTimer.start();
        } else {
            firstBall = true;
            m_Index.setIndex(0.25);
            m_Shooter.spit();
        }
    }

    @Override
    public void execute(){

    }

    @Override
    public boolean isFinished(){
        if(cutOffTimer.get() > 8){
            return true;
        }else{
            if(firstBall){
                if(m_Index.getShooterEntry()){
                    return true;
                }else{
                    return false;
                }
            }else{
                MotorDirections.FlipIntake = true;
                if(indexTimer.get() < 5){
                    return false;
                }else{
                    return true;
                }
            }
        }
    }

    @Override
    public void end(boolean interrupted){
        MotorDirections.FlipIntake = false;
        m_Shooter.stop();
        m_Index.stop();
        m_Index.spitBall(false);
    }

    
}
