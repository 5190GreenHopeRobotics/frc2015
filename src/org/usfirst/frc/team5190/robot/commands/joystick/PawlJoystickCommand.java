package org.usfirst.frc.team5190.robot.commands.joystick;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.PawlSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PawlJoystickCommand extends Command {

	private PawlSubsystem pawlSubsystem = PawlSubsystem.getInstance();

	private static final double MAX_POWER_DELTA = 0.05;

	private double lastPowerValue = 0.0;

	public PawlJoystickCommand() {
		super("PawlJoystickCommand");
		requires(pawlSubsystem);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {

		if (!pawlSubsystem.clutchEngaged()) {
			return;
		}
		if (pawlSubsystem.isLocked()) {
			return;
		}
		// reverse the joystick value
		double power = Robot.oi.getPawlAxis();
		// The idea here to cap the rate of power change when moving away from 0
		// but not when moving close to it

		// if the power value changes sign from the last one then reset the last
		// value to 0.
		// if (power > 0 && lastPowerValue < 0 || power < 0 && lastPowerValue >
		// 0) {
		// lastPowerValue = 0.0;
		// }
		// if (power > 0 && (power - lastPowerValue) > MAX_POWER_DELTA) {
		// power = lastPowerValue + MAX_POWER_DELTA;
		// } else if (power < 0 && (lastPowerValue - power) > MAX_POWER_DELTA) {
		// power = lastPowerValue - MAX_POWER_DELTA;
		// }
		SmartDashboard.putNumber("pawl power", power);

		pawlSubsystem.goToAngle(power * 20);
		lastPowerValue = power;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		pawlSubsystem.stopPawl();
	}

	@Override
	protected void interrupted() {
		// end();
	}

}
