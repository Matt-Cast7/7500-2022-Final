package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ToggleGoal extends CommandBase{

    private Shooter m_Shooter;

    public ToggleGoal(Shooter m_Shooter){
        this.m_Shooter = m_Shooter;
    }

    public void execute(){
        m_Shooter.toggleGoal();
    }


    @Override
    public boolean isFinished(){
        return true;
    }
    
}
