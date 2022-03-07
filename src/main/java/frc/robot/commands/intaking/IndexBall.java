package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Index.Ball;

public class IndexBall extends CommandBase {

    private Index m_Index;
    private boolean firstBall;
    private Timer cutOffTimer;

    public IndexBall(Index m_Index) {
        this.m_Index = m_Index;
        cutOffTimer = new Timer();
        addRequirements(m_Index);
    }

    public void initialize() {
     
        cutOffTimer.start();
     
        if (m_Index.ballInBack()) {
            firstBall = false;
        } else {
            firstBall = true;
        }

    }

    public void execute() {
        m_Index.setIndex(0.25);
    }

    public boolean isFinished() {
        if (cutOffTimer.get() > 8) {
            return true;
        }else{
            if (firstBall) {
                if(m_Index.ballInBack()){
                    return true;
                }else{
                    return false;
                }
            } else {
                if(!m_Index.ballInFront()){
                    return true;
                }else{
                    return false;
                }
            }
        }

    }

    public void end() {
        m_Index.indexBall(false);
        m_Index.setIndex(0);
    }

}
