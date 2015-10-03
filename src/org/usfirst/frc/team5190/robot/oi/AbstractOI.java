package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.commands.GoToLevelCommand;
import org.usfirst.frc.team5190.robot.commands.KillCommand;
import org.usfirst.frc.team5190.robot.commands.MoarPowahCommand;
import org.usfirst.frc.team5190.robot.commands.IntakeCommand;
import edu.wpi.first.wpilibj.buttons.Button;
//import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class AbstractOI implements OI {

	public AbstractOI() {
	}

	protected void initializeButtons() {
<<<<<<< Updated upstream
		getLevelUpButton().whenPressed(new GoToLevelCommand(true));
		getLevelDownButton().whenPressed(new GoToLevelCommand(false));
=======
//		getLevelUpButton().whenPressed(new GoToLevelCommand(true));
//		getLevelDownButton().whenPressed(new GoToLevelCommand(false));
//		getZeroPawlButton().whenPressed(new ZeroPawlCommand());
>>>>>>> Stashed changes
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
		
		getBackstopUpSwitch().whenPressed(new GoToLevelCommand(true));		//raise bar
		getBackstopDownSwitch().whenPressed(new GoToLevelCommand(true));	//lower bar

		
	}

//	protected abstract Button getLevelUpButton();

//	protected abstract Button getLevelDownButton();

//	protected abstract Button getZeroPawlButton();

	protected abstract Button getKillButton();

	protected abstract Button getMoarPowahButton();

	protected abstract Button getIntakeDirectionINSwitch();
	
	protected abstract Button getIntakeDirectionOUTSwitch();

	protected abstract Button getBackstopUpSwitch();

	protected abstract Button getBackstopDownSwitch();

}
