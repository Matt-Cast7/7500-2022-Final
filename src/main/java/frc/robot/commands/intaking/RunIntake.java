package frc.robot.commands.intaking;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase{

    private Intake m_Intake;

    public RunIntake(Intake m_Intake){
        this.m_Intake = m_Intake;
        addRequirements(m_Intake);
    }

    public void execute(){
        m_Intake.enableIntake();
    }

    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        m_Intake.stop();
    }
}
