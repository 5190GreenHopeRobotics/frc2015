package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmTeleopCommand extends Command {

	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();

	private static final double MAX_POWER_DELTA = 0.05;

	private double lastPowerValue = 0.0;

	public ArmTeleopCommand() {
		requires(armSubsystem);
	}

	protected void initialize() {
	}

	protected void execute() {
		Joystick joystick = Robot.oi.getDriveStick();
		// reverse the joystick value
		double power = -joystick.getRawAxis(5);

		// The idea here to cap the rate of power change when moving away from 0
		// but not when moving close to it

		// if the power value changes sign from the last one then reset the last
		// value to 0.
		if (power > 0 && lastPowerValue < 0 || power < 0 && lastPowerValue > 0) {
			lastPowerValue = 0.0;
		}
		if (power > 0 && (power - lastPowerValue) > MAX_POWER_DELTA) {
			power = lastPowerValue + MAX_POWER_DELTA;
		} else if (power < 0 && (lastPowerValue - power) > MAX_POWER_DELTA) {
			power = lastPowerValue - MAX_POWER_DELTA;
		}

		armSubsystem.moveArm(power);
		lastPowerValue = power;
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		armSubsystem.stopArm();
	}

	protected void interrupted() {
		end();
	}
}
