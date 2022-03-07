package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.GlobalCommandControl;
import frc.robot.MotorDirections;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Index.Ball;

public class IndexBall extends CommandBase {

    private Index m_Index;
    private boolean firstBall;
    private Timer timer;

    public IndexBall(Index m_Index) {
        this.m_Index = m_Index;
        timer = new Timer();
    }

    public void initialize() {
        if (m_Index.detectBackIndexBalls() != Ball.NONE) {
            firstBall = false;
        } else {
            firstBall = true;
        }
    }

    public void execute() {
        m_Index.setIndex(0.25);
    }

    public boolean isFinished() {
        if (m_Index.detectFrontIndexBalls() != m_Index.AllianceColor) {
            MotorDirections.FlipIntake = true;
            timer.start();
            if(timer.get() > 2.0){
                MotorDirections.FlipIntake = false;
                timer.stop();
                timer.reset();
            }
            return false;
        } else {
            if (firstBall) {
                if (m_Index.detectBackIndexBalls() != Ball.NONE) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (m_Index.detectFrontIndexBalls() != Ball.NONE) {
                    return true;
                } else {
                    return false;
                }
            }

        }
        

    }

    public void end() {
        m_Index.setIndex(0);
    }

}
