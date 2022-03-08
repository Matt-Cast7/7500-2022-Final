package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Deployer extends SubsystemBase {

    private final CANSparkMax deployer = new CANSparkMax(Constants.Deployer, MotorType.kBrushed);

    private DigitalInput intakeLimitSwitch = new DigitalInput(Constants.DeployerLimitSwitch);

    private final boolean flipDeployer = false;

    private static  boolean deployState = false;
            
    public Deployer() {
        deployer.setInverted(flipDeployer);
        deployer.setIdleMode(IdleMode.kCoast);
        
    }

    public void setDeployState(boolean state){
        deployState = state;
    }

    public boolean isDeployed(){
        return deployState;
    }

    
    public void setDeployerSpeed(double speed) {
        deployer.set(speed);
    }

    public void stop() {
        setDeployerSpeed(0);
    }



}
