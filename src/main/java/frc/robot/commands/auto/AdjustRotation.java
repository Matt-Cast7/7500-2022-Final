package frc.robot.commands.auto;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AdjustRotation extends CommandBase {

    private DriveTrain m_DriveTrain;
    private Timer cutOffTimer;
    private DoubleSupplier tx;

    private BooleanSupplier toggle;

    private double trackKP = 0.05;
    private double minCommand = 0.1;

    public AdjustRotation(DriveTrain m_DriveTrain, BooleanSupplier toggle) {
        this.m_DriveTrain = m_DriveTrain;
        this.toggle = toggle;
        cutOffTimer = new Timer();
        tx = () -> {
            return -NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        };
        addRequirements(m_DriveTrain);
    }

    @Override
    public void initialize() {
        cutOffTimer.start();
    }

    @Override
    public void execute() {
        if(toggle.getAsBoolean()){
            m_DriveTrain.set(-adjustmentPower(), adjustmentPower());
        }
        
    }

    public double adjustmentPower() {
        double power = 0;

        double txVal = tx.getAsDouble() - 2;


        if (txVal > 1.0) {
            power = trackKP * txVal - minCommand;
        } else if (txVal < 1.0) {
            power = trackKP * txVal + minCommand;
        }

        return power;

    }

    @Override
    public boolean isFinished() {
        if (cutOffTimer.get() > 1) {
            return true;
        } else {
            if (Math.abs(tx.getAsDouble()) < 1.0) {
                return false;
            } else {
                return false;
            }
        }
    }

    @Override
    public void end(boolean interrupted){
        cutOffTimer.stop();
            cutOffTimer.reset();
        m_DriveTrain.set(0);
    }

}
