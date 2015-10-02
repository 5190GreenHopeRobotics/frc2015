package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.commands.GoToLevelCommand;
import org.usfirst.frc.team5190.robot.commands.KillCommand;
import org.usfirst.frc.team5190.robot.commands.MoarPowahCommand;
import org.usfirst.frc.team5190.robot.commands.ZeroPawlCommand;
import org.usfirst.frc.team5190.robot.joystick.LogitechExtreme3D;
import org.usfirst.frc.team5190.robot.commands.IntakeMotorRunStopCommand;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class AbstractOI implements OI {

	public AbstractOI() {
	}

	protected void initializeButtons() {
		getLevelUpButton().whenPressed(new GoToLevelCommand(true));
		getLevelDownButton().whenPressed(new GoToLevelCommand(false));
		getZeroPawlButton().whenPressed(new ZeroPawlCommand());
		getKillButton().whenPressed(new KillCommand());

		Button moarPowahButton = getMoarPowahButton();
		moarPowahButton.whenPressed(new MoarPowahCommand(true));
		moarPowahButton.whenReleased(new MoarPowahCommand(false));
		
		Button intakeDirectionINSwitch = getIntakeDirectionINSwitch();
		intakeDirectionINSwitch.whenReleased(new IntakeMotorRunStopCommand(0.0));
		intakeDirectionINSwitch.whileHeld(new IntakeMotorRunStopCommand(-0.5f));

		Button intakeDirectionOUTSwitch = getIntakeDirectionOUTSwitch();
		intakeDirectionOUTSwitch.whenReleased(new IntakeMotorRunStopCommand(0.0));
		intakeDirectionOUTSwitch.whileHeld(new IntakeMotorRunStopCommand(0.5f));
		
	}

	protected abstract Button getLevelUpButton();

	protected abstract Button getLevelDownButton();

	protected abstract Button getZeroPawlButton();

	protected abstract Button getKillButton();

	protected abstract Button getMoarPowahButton();

	protected abstract Button getIntakeDirectionINSwitch();
	
	protected abstract Button getIntakeDirectionOUTSwitch();
	
}
