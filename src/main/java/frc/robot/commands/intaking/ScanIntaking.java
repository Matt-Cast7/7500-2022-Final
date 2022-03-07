package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Index.Ball;

public class ScanIntaking extends CommandBase {

    private Index m_Index;
    private Ball ballColor;

    public ScanIntaking(Index m_Index) {
        this.m_Index = m_Index;
        addRequirements(m_Index);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        if (m_Index.detectFrontIndexBalls() != Ball.NONE) {
            ballColor = m_Index.detectFrontIndexBalls();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            m_Index.indexBall(false);
            m_Index.spitBall(false);

        } else {
            if (ballColor == m_Index.AllianceColor) {
                /**
                 * Index Ball
                 */
                m_Index.indexBall(true);
            } else {
                /**
                 * Spit Ball
                 */
                m_Index.spitBall(true);
            }
        }
    }

}
