package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Deployer extends SubsystemBase {

    private final CANSparkMax deployer = new CANSparkMax(Constants.Deployer, MotorType.kBrushed);

    private DigitalInput intakeLimitSwitch = new DigitalInput(Constants.DeployerLimitSwitch);

    private final boolean flipDeployer = false;
            
    public Deployer() {
        deployer.setInverted(flipDeployer);
        deployer.setIdleMode(IdleMode.kCoast);
        
    }




    public void toggle(){
        if(intakeLimitSwitch.get()){
            retractIntake();
        }else{
            deployIntake();
        }
    }



    public void deployIntake() {
        new Thread(() ->{

        if(!intakeLimitSwitch.get()){
            Timer time = new Timer();
            time.start();
            while(time.get() < 3){
                setDeployerSpeed(0.18);
            }
            stopDeployer();
            time.stop();
            time = null;
        }}).start();

    }

    public void retractIntake() {
        new Thread(() ->{

            Timer time = new Timer();
            time.start();
            while(time.get() < 1){
                setDeployerSpeed(-0.3);
            }
            stopDeployer();
            time.stop();
            time = null;
        }).start();
    }

    
    public void setDeployerSpeed(double speed) {
        deployer.set(speed);
    }

    public void stopDeployer() {
        setDeployerSpeed(0);
    }



}
