package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenGrabberCommand extends Command {

    public OpenGrabberCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.robotPawSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.robotPawSubsystem.openGrabber();
    }

    // Called repeatedly when this Command is scheduled to run(every 0.2 seconds)
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Line below is for if we use an ultrasonic sensor
       // return Robot.robotPawSubsystem.pawUltrasonic.getRangeInches() < 35;
    	return true;
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
