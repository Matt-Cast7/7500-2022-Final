package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.MotorDirections;

public class Shooter extends SubsystemBase {

    private final CANSparkMax shooterMaster = new CANSparkMax(Constants.Shooter[0], MotorType.kBrushless);
    private final CANSparkMax shooterSlave = new CANSparkMax(Constants.Shooter[1], MotorType.kBrushless);

    private PIDController pid = new PIDController(Constants.shooterKp, Constants.shooterKi, Constants.shooterKd);

    
    private boolean flipShooter;

    
    private boolean targetGoal = true;
    private double targetSpeed = 4500;

    private double shooterPower = 65;


    private NetworkTableEntry wheelSpeed = Shuffleboard.getTab("TeleOp")
            .add("Shooter Wheel RPM", 0)
            .withWidget(BuiltInWidgets.kDial)
            .getEntry();

    public Shooter() {

        flipShooter = MotorDirections.FlipShooter;

        shooterMaster.setInverted(flipShooter);
        shooterSlave.setInverted(!flipShooter);

        shooterMaster.setIdleMode(IdleMode.kCoast);
        shooterSlave.setIdleMode(IdleMode.kCoast);

        shooterMaster.getEncoder().setVelocityConversionFactor(Constants.shooterGearing);

        Shuffleboard.getTab("TeleOp")
                .addBoolean("Shooter Ready to Fire", () -> isShooterUptoSpeed());

        Shuffleboard.getTab("TeleOp")
                .addBoolean("High or Low Goal", () -> getTargetGoal());

    }

    @Override
    public void periodic() {

        wheelSpeed.setDouble(shooterMaster.getEncoder().getVelocity());

    }

    public void resetPID() {
        pid.reset();
    }

    public void enablae(){
        shooterMaster.set(0.65);
        shooterSlave.set(0.65);
    }

    public void enable() {
        // double power = MathUtil.clamp((pid.calculate(wheelSpeed.getDouble(0) +
        // feedforward.calculate(targetSpeed), targetSpeed)), -1, 1);
        double power = MathUtil.clamp((pid.calculate(Math.abs(wheelSpeed.getDouble(0)), targetSpeed)), -1, 1);

        shooterMaster.set(power);
        shooterSlave.set(power);
    }

    public void enable(double rpm) {
        // double power = MathUtil.clamp((pid.calculate(wheelSpeed.getDouble(0) +
        // feedforward.calculate(targetSpeed), targetSpeed)), -1, 1);
        double power = MathUtil.clamp((pid.calculate(Math.abs(wheelSpeed.getDouble(0)), rpm)), -1, 1);

        shooterMaster.set(power);
        shooterSlave.set(power);
    }

    public void spit() {
        setShooter(0.25);
    }

    public void stop() {
        shooterMaster.set(0);
        shooterSlave.set(0);
    }

    public void setShooter(double speed) {

        shooterMaster.set(speed);
        shooterSlave.set(speed);
    }

    public boolean isShooterUptoSpeed() {
        if ((wheelSpeed.getDouble(0) < (targetSpeed - 50)) || (wheelSpeed.getDouble(0) > (targetSpeed + 50))) {
            return true;
        } else {
            return false;
        }
    }

    public void toggleGoal() {
        if (targetGoal == true) {
            setLowGoal();
        } else {
            setHighGoal();
        }
    }

    public void setHighGoal() {
        targetSpeed = 4500;
        targetGoal = true;
        shooterPower = 65;

        System.out.println("High Goal");
    }

    public void setLowGoal() {
        targetSpeed = 2100;
        targetGoal = false;
        shooterPower = 45;
        System.out.println("Low Goal");

    }

    public boolean getTargetGoal() {
        return targetGoal;
    }

    public void runBackwards() {
        setShooter(0.1);
    }

    public double getAngularVelocity() {
        return ((2 * Math.PI) / 60) * wheelSpeed.getDouble(0);
    }
}
