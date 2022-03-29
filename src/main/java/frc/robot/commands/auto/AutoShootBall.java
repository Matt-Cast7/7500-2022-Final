package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

public class AutoShootBall extends CommandBase{

    private Shooter m_Shooter;
    private Index m_Index;
    private Timer cutOffTimer;

    private boolean finished = false;
    private Timer timer;

    public AutoShootBall(Shooter m_Shooter, Index m_Index){
        this.m_Shooter = m_Shooter;
        this.m_Index = m_Index;
        cutOffTimer = new Timer();
        timer = new Timer();
        addRequirements(m_Index, m_Shooter);
    }


    @Override
    public void initialize(){
        cutOffTimer.reset();
        timer.reset();
        cutOffTimer.start();
        timer.start();
        m_Shooter.resetPID();
    }

    @Override
    public void execute(){
        m_Shooter.enable();


        if(timer.get() > 2){
            m_Index.setIndex(0.3);;
        }

        if(timer.get() > 4){
            finished = true;
        }

    }

    @Override
    public boolean isFinished(){
        return finished;
    }

    @Override
    public void end(boolean interrupted){
        m_Shooter.stop();
        m_Index.stop();
    }
    
}
