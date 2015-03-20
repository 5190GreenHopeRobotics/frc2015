package org.usfirst.frc.team5190.robot.commands.joystick;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmJoystickCommand extends Command {

	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();

	public ArmJoystickCommand() {
		super("ArmTeleopCommand");
		requires(armSubsystem);
	}

	protected void initialize() {
	}

	protected void execute() {
		double power = Robot.oi.getArmAxis();

		armSubsystem.moveArm(power);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
