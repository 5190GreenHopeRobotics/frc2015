package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command requires the armSubsystem.
 */
public class LowerArmCommand extends Command {

    public LowerArmCommand() {
    	requires(Robot.armSubsystem);
    	setTimeout(1.0);
    	
    }

    /**
     * This starts the lowering of the arm.
     */
    protected void initialize() {
    	Robot.armSubsystem.lowerArm();
    	
    }

    /**
     *  Called repeatedly when this Command is scheduled to run
     */
    protected void execute() {
    }

    /**
     * This returns when the time is finished.
     */
    protected boolean isFinished() {
        return isTimedOut();
    }

    /**
     * This stops the arm from lowering when this command ends.
     */
    protected void end() {
    	Robot.armSubsystem.stopArmAngleChange();
    	
    }

    /**
     * This stops the arm from lowering when the code is interrupted.
     */
    protected void interrupted() {
    	Robot.armSubsystem.stopArmAngleChange();
    	
    }
}
