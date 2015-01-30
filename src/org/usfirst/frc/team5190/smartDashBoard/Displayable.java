package org.usfirst.frc.team5190.smartDashBoard;

import java.util.Collection;

/**
 * a collection of values that can be displayed on smart dashboard
 * 
 * @author Shilong Dai
 *
 */
public interface Displayable {

	/**
	 * get a list of all boolean value to be output to the smartDashBoard
	 * 
	 * @return the list
	 */
	public Collection<Pair<String, Boolean>> getBooleanValue();

	/**
	 * get a list of all double value to be output to the SmartDashBoard
	 * 
	 * @return the list
	 */
	public Collection<Pair<String, Double>> getDecimalValues();
}
