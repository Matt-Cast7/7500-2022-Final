package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Index.Ball;

public class AutoIndexing extends CommandBase {

    private Index m_Index;
    private Shooter m_Shooter;
    private Timer cutOffTimer;

    private boolean indexing;

    private Ball detectedBall;
    private Ball ballInBack;

    public AutoIndexing(Index m_Index, Shooter m_Shooter) {
        this.m_Index = m_Index;
        this.m_Shooter = m_Shooter;
        cutOffTimer = new Timer();
        indexing = false;
        addRequirements(m_Index, m_Shooter);
    }

    @Override
    public void initialize() {
        indexing = false;
    }

    @Override
    public void execute() {

        if (!indexing) {
            if (m_Index.ballInFront()) {
                indexing = true;
                detectedBall = m_Index.detectFrontIndexBalls();
                ballInBack = m_Index.detectBackIndexBalls();
            }
        } else {
            if (detectedBall == m_Index.AllianceColor) {
                cutOffTimer.start();
                indexBall();

            } else {
                //spitBall();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_Index.stop();
        m_Shooter.stop();
    }

    public void spitBall() {
        if(cutOffTimer.get() > 4){
            cutOffTimer.stop();
            cutOffTimer.reset();
            indexing = false;
        }else{

        }
    }

    public void indexBall() {
        if (cutOffTimer.get() > 4) {
            cutOffTimer.stop();
            cutOffTimer.reset();
            indexing = false;
        } else {
            if (ballInBack == Ball.NONE) {

                m_Index.setIndex(0.25);

                if (m_Index.ballInBack()) {
                    cutOffTimer.stop();
                    cutOffTimer.reset();
                    indexing = false;
                    m_Index.stop();
                }
            } else {

                m_Index.setIndex(0.20);

                if (!m_Index.ballInFront()) {
                    cutOffTimer.stop();
                    cutOffTimer.reset();
                    indexing = false;
                    m_Index.stop();
                }
            }
        }
    }

}
