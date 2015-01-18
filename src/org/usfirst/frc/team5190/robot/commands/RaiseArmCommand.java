package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command requires the armSubsystem.
 */
public class RaiseArmCommand extends Command {

    public RaiseArmCommand() {
        requires(Robot.armSubsystem);
        setTimeout(1.0);
    	
    }

    /**
     * This starts raising the arm.
     */
    protected void initialize() {
    	Robot.armSubsystem.raiseArm();
    	
    }

    /**
     * Called repeatedly when this Command is scheduled to run
     */
    protected void execute() {
    }

    /**
     * This is returned when the set time is up.
     */
    protected boolean isFinished() {
        return isTimedOut();
    }

    /**
     * This stops the arm from rising when the time ends.
     */
    protected void end() {
    	Robot.armSubsystem.stopArmAngleChange();
    }

    /**
     * This stops the arm from rising when the code is interrupted.
     */
    protected void interrupted() {
    	Robot.armSubsystem.stopArmAngleChange();
    	
    }
}
