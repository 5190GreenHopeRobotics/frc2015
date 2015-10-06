package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.commands.IntakeCommand;
import org.usfirst.frc.team5190.robot.commands.KillCommand;
import org.usfirst.frc.team5190.robot.commands.MoarPowahCommand;
import org.usfirst.frc.team5190.robot.commands.NarrowIntakeWheelCommand;
import org.usfirst.frc.team5190.robot.commands.PneumaticExtendCommand;
import org.usfirst.frc.team5190.robot.commands.PneumaticRetractCommand;
import org.usfirst.frc.team5190.robot.commands.PneumaticStopCommand;
import org.usfirst.frc.team5190.robot.commands.WidenIntakeWheelCommand;

import edu.wpi.first.wpilibj.buttons.Button;

//import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class AbstractOI implements OI {

	public AbstractOI() {
	}

	protected void initializeButtons() {

		// getLevelUpButton().whenPressed(new GoToLevelCommand(true));
		// getLevelDownButton().whenPressed(new GoToLevelCommand(false));
		// getZeroPawlButton().whenPressed(new ZeroPawlCommand());

		getKillButton().whenPressed(new KillCommand());

		Button moarPowahButton = getMoarPowahButton();
		moarPowahButton.whenPressed(new MoarPowahCommand(true));
		moarPowahButton.whenReleased(new MoarPowahCommand(false));

		Button intakeDirectionINSwitch = getIntakeDirectionINSwitch();
		intakeDirectionINSwitch.whenReleased(new IntakeCommand(0));
		intakeDirectionINSwitch.whileHeld(new IntakeCommand(-0.5));

		Button intakeDirectionOUTSwitch = getIntakeDirectionOUTSwitch();
		intakeDirectionOUTSwitch.whenReleased(new IntakeCommand(0.0));
		intakeDirectionOUTSwitch.whileHeld(new IntakeCommand(0.5));

		getPneumaticForwardButton().whenPressed(new PneumaticExtendCommand());
		getPneumaticForwardButton().whenReleased(new PneumaticStopCommand());		//added for third solenoid
		
		getPneumaticReverseButton().whenPressed(new PneumaticRetractCommand());
		getPneumaticReverseButton().whenReleased(new PneumaticStopCommand());		//added for third solenoid

		getWidenIntakeButton().whenPressed(new WidenIntakeWheelCommand());
		getNarrowIntakeButton().whenPressed(new NarrowIntakeWheelCommand());
		// getBackstopUpSwitch().whenPressed(new GoToLevelCommand(true)); //
		// raise
		// // bar
		// getBackstopDownSwitch().whenPressed(new GoToLevelCommand(true)); //
		// lower
		// // bar

	}

	protected abstract Button getPneumaticForwardButton();

	protected abstract Button getPneumaticReverseButton();

	// protected abstract Button getZeroPawlButton();

	protected abstract Button getKillButton();

	protected abstract Button getMoarPowahButton();

	protected abstract Button getIntakeDirectionINSwitch();

	protected abstract Button getIntakeDirectionOUTSwitch();

	protected abstract Button getWidenIntakeButton();

	protected abstract Button getNarrowIntakeButton();
	// protected abstract Button getBackstopUpSwitch();
	//
	// protected abstract Button getBackstopDownSwitch();

}
