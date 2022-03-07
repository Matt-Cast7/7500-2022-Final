package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TrackDistance;
import frc.robot.subsystems.DriveTrain;

public class AdjustRange extends CommandBase {

    private DriveTrain m_DriveTrain;
    private Timer cutOffTimer;
    private double targetDistance = 110;
    private double distanceKP = 0.1;
    private TrackDistance track;

    public AdjustRange(DriveTrain m_DriveTrain) {
        this.m_DriveTrain = m_DriveTrain;
        cutOffTimer = new Timer();
        track = new TrackDistance();
        addRequirements(m_DriveTrain);
    }

    @Override
    public void initialize() {
        cutOffTimer.start();
    }

    @Override
    public void execute() {
        m_DriveTrain.set(adjustmentPower());
    }

    public double adjustmentPower() {
        double power = 0;
        if (track.getDistance() > targetDistance) {
            power = (track.getDistance() - targetDistance) * distanceKP;

        } else if (track.getDistance() < targetDistance) {
            power = -((targetDistance - track.getDistance()) * distanceKP);

        }
        return power;
    }

    @Override
    public boolean isFinished() {
        if (cutOffTimer.get() > 4) {
            return true;
        } else {
            if (Math.abs(track.getDistance()) < 4.0) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void end(boolean interrupted){
        m_DriveTrain.set(0);
    }

}
