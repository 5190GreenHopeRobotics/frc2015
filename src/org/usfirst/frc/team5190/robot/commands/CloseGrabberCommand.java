package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseGrabberCommand extends Command {

    public CloseGrabberCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.robotPawSubsystem);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.robotPawSubsystem.closeGrabber();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.robotPawSubsystem.pawUltrasonic.getRangeInches() == 0;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.robotPawSubsystem.stopGrabber();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.robotPawSubsystem.stopGrabber();
    }
}
