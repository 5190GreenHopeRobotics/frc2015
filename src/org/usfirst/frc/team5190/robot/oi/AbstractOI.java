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

		getIntakeDirectionINSwitch().whenPressed(new IntakeCommand(.5));
		getIntakeDirectionOUTSwitch().whenPressed(new IntakeCommand(-.5));
		// getIntakeDirectionBOTHSwitches().whenPressed(new IntakeCommand(0));

		getPneumaticForwardButton().whenPressed(new PneumaticExtendCommand());
		getPneumaticForwardButton().whenReleased(new PneumaticStopCommand()); // added
																				// for
																				// third
																				// solenoid

		getPneumaticReverseButton().whenPressed(new PneumaticRetractCommand());
		getPneumaticReverseButton().whenReleased(new PneumaticStopCommand()); // added
																				// for
																				// third
																				// solenoid

		getWidenIntakeButton().whenPressed(new WidenIntakeWheelCommand());

		getNarrowIntakeButton().whenPressed(new NarrowIntakeWheelCommand());
		getNarrowIntakeButton().whenReleased(new WidenIntakeWheelCommand()); // allows
																				// one
																				// button
																				// squeeze/release
		getStopIntakeButton().whenPressed(new IntakeCommand(0));

		// *************************************************************************
		// *****************TEMPORARY FOR PRACTICE
		// ONLY!!!!!!***********************
		// *************************************************************************
		getIntakeDirectionINSwitch2().whenPressed(new IntakeCommand(1.0)); // full
																			// power!
		getIntakeDirectionOUTSwitch2().whenPressed(new IntakeCommand(-1.0)); // full
																				// power!
		// getIntakeDirectionBOTHSwitches().whenPressed(new IntakeCommand(0));

		getPneumaticForwardButton2().whenPressed(new PneumaticExtendCommand());
		getPneumaticForwardButton2().whenReleased(new PneumaticStopCommand()); // added
																				// for
																				// third
																				// solenoid

		getPneumaticReverseButton2().whenPressed(new PneumaticRetractCommand());
		getPneumaticReverseButton2().whenReleased(new PneumaticStopCommand()); // added
																				// for
																				// third
																				// solenoid

		getWidenIntakeButton2().whenPressed(new WidenIntakeWheelCommand());

		getNarrowIntakeButton2().whenPressed(new NarrowIntakeWheelCommand());
		getNarrowIntakeButton2().whenReleased(new WidenIntakeWheelCommand()); // allows
																				// one
																				// button
																				// squeeze/release
		// *************************************************************************
		// *************************************************************************
		// *************************************************************************

	}

	protected abstract Button getPneumaticForwardButton();

	protected abstract Button getPneumaticReverseButton();

	// protected abstract Button getZeroPawlButton();

	protected abstract Button getKillButton();

	protected abstract Button getMoarPowahButton();

	protected abstract Button getIntakeDirectionINSwitch();

	protected abstract Button getIntakeDirectionOUTSwitch();

	// protected abstract Button getIntakeDirectionBOTHSwitches();

	protected abstract Button getWidenIntakeButton();

	protected abstract Button getNarrowIntakeButton();

	protected abstract Button getStopIntakeButton();

	// *************************************************************************
	// *****************TEMPORARY FOR PRACTICE ONLY!!!!!!***********************
	// *************************************************************************
	protected abstract Button getPneumaticForwardButton2();

	protected abstract Button getPneumaticReverseButton2();

	protected abstract Button getIntakeDirectionINSwitch2();

	protected abstract Button getIntakeDirectionOUTSwitch2();

	// protected abstract Button getIntakeDirectionBOTHSwitches();
	protected abstract Button getWidenIntakeButton2();

	protected abstract Button getNarrowIntakeButton2();
	// *************************************************************************
	// *************************************************************************
	// *************************************************************************

}
