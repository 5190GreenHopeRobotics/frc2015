package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendArmCommand extends Command {
	/**
	 * This requires the armSubsystem and sets the operation segment time as 1 second.
	 */
    public ExtendArmCommand() {
        requires(Robot.armSubsystem);
        setTimeout(1.0);
    }

    /**
     * This part starts the actual process of the extending of the arm.
     */
    protected void initialize() {
    	Robot.armSubsystem.extendArm();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
    
    // Returns it when the amount of time is finished.
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Stops the Arm when the Time is out
    protected void end() {
    	Robot.armSubsystem.stopArm();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.armSubsystem.stopArm();
    }
}
