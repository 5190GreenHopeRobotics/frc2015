package org.usfirst.frc.team5190.smartDashBoard;

import java.util.Collection;

/**
 * a collection of values that can be displayed on smart dashboard
 * 
 * @author sdai
 *
 */
public interface Displayable {

	public Collection<Pair<String, Boolean>> getBooleanValue();

	public Collection<Pair<String, Double>> getDecimalValues();
}
