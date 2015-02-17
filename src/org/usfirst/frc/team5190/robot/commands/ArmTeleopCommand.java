package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmTeleopCommand extends Command {

	public ArmTeleopCommand() {
		requires(Robot.armSubsystem);
	}

	protected void initialize() {
	}

	protected void execute() {
		Joystick joystick = Robot.oi.getDriveStick();
		// reverse the joystick value
		double power = -joystick.getRawAxis(5);
		// square the inputs (while preserving the sign) to increase fine
		// control while permitting full power
		if (power >= 0.0) {
			power = (power * power);
		} else {
			power = -(power * power);
		}
		Robot.armSubsystem.moveArm(power);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
