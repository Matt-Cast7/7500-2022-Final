package frc.robot.commands.shooting;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase{

    private Shooter m_Shooter;
    private BooleanSupplier toggle;

    public RunShooter(Shooter m_shooter, BooleanSupplier toggle){
        this.m_Shooter = m_shooter;
        this.toggle = toggle;
        addRequirements(m_shooter);
    }

    public void initialize(){
        m_Shooter.resetPID();
    }

    public void execute(){
       
       if(toggle.getAsBoolean()){
        m_Shooter.enableAutoAdjust();
       }else{
        m_Shooter.enable();
       }
    }


    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        m_Shooter.stop();
    }
    
}
